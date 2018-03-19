/**
 * Created by Administrator on 2016/12/8.
 */
requirejs(['common'], function () {
    requirejs(['jquery', 'bootstrap', 'template', 'base', 'paging'], function ($, bootstrap, template, base, Paging) {
  

        // 上传图片
       	var	uplTypeObj;
		$(document).ready(function () {
        // 上传图片
		
      base.sendReq({
		   url: 'api/qiniu/token',
		   method: 'GET',
		   getResult:function(data){
			   data = JSON.parse(data);
			    if (data.success) {
                         $('#upToken').val(data.token);
                         $('input[name="token"]').val(data.token);

                     uplTypeObj = base.uploadQiniuPic('uploadTypePic', 'uploader-type-container', 'logoPicList', data.token);
				
                     } else {	
                         base.showAlertDialog('');
                     }
                 }
             });
		   });
		
		
		
		
		var pageSize = 10, pageTotal = 10/* ,brand = $('#brand'); */
		//所有品牌
		
		/* function getUserList(pageNum, pageSize) {
           
            base.sendReq({
                url: 'carBasic/searchCarSeries',
                method: 'GET',
                data: reqData,
                getResult: function (data) {
                    if (data.success) {
                        var userList = data.pageInfo;
                        var userHtml = template('selectData', userList);
                        $('#barnd').html(userHtml);                                                                     
                    } 
                }

            });
        }

       
        getUserList(1, 10); */
		
        // 加载列表
        function getUserList(pageNum, pageSize) {
			base.showLoading("");
            var brand = $('#brand').val(),code = $('#code').val(),name = $('#carName').val(),status = $('#status').val();
            if (status == 0) {
                status = '';
            } else if (status == 1) {
                status = true;
            } else {
                status = false;
            }
            var reqData = {
				
				brand: brand,
                code: code, 
				name: name,
                enable: status,
                pageNum: pageNum,
                pageSize: pageSize
            };
            base.sendReq({
                url: 'carBasic/searchCarSeries',
                method: 'GET',
                data: reqData,
                getResult: function (data) {
					base.hideLoading();
                    if (data.success) {
                        var userList = data.pageInfo;
                        var userHtml = template('userDatas', userList);
                        $('#carTypeTableBody').html(userHtml);
                        pageTotal = userList.total;
                        var pageNum = userList.total/userList.size;
                        if (userList.total%userList.size != 0) {
                            pageNum += 1;
                        }
                        p.render({
                            count: userList.total,
                            pagecount: pageNum,
                            current: userList.pageNum,
                            pagesize: userList.pageSize
                        });
                        if ($('#pageTool').hasClass('hide')) {
                            $('#pageTool').removeClass('hide');
                        }
                    } else {
                        if (data.errorCode === 200004) {
                            $('#carTypeTableBody').html('<tr><td colspan="15">没有符合条件的查询记录</td></tr>');
                            $('#pageTool').addClass('hide');
                        } else {
                            base.showAlertDialog(data.errorMessage);
                        }
                    }
                }

            });
        }

        var p = new Paging();
        p.init({target:'#pageTool',pagesize:pageSize,count:pageTotal,toolbar:true, callback: function (page, size ,count) {
            pageSize = size;
            getUserList(page, pageSize);
        }});
        getUserList(1, 10);
		
		
      
        // 搜索
        $('#searchBtn').on('click', function () {
            getUserList(1, pageSize);
        });

        // 重置
        $('#resetBtn').on('click', function () {
            $('#brand').val('');
            $('#code').val('');
            $('#carName').val('');
            $('#status').val(0);
            getuserList(1, pageSize);
        });


        // 新增用户
        $('#addUserBtn').on('click', function () {
            $('#userModalTitle').text('新增用户');
            $('#modalUserId').val('');			
			$('#modalCode').val('').removeClass('error-border').siblings('div').addClass('hide');
            $('#modalName').val('').removeClass('error-border').siblings('div').addClass('hide');
            $('#modalBrand').val('').removeClass('error-border').siblings('div').addClass('hide');
            $('#modalStatus').val(0);
			$('#uploadLogoPic').val('').removeClass('error-border').siblings('div').addClass('hide');
            $('#userManageModal').modal('show');
        });

        // 编辑
        $('body').on('click', '.edit-btn', function () {
            $('#userModalTitle').text('编辑用户');
            var data = [];
            $(this).parents('tr').children('td').each(function (i) {
                data[i] = $(this).text();
            });
            var userId = data[2], Code = data[3], Name = data[4], Brand = data[5], Pic = data[6], status = data[7];
            $('#modalUserId').val(userId);
            $('#modalCode').val(Code).removeClass('error-border').siblings('div').addClass('hide');
            $('#modalName').val(Name).removeClass('error-border').siblings('div').addClass('hide');
            $('#modalBrand').val(Brand).removeClass('error-border').siblings('div').addClass('hide');
            $('#uploadTypePic').val(Pic).removeClass('error-border').siblings('div').addClass('hide');
            if (status == '可用') {
                $('#modalStatus').val(0);
            } else {
                $('#modalStatus').val(1);
            }
			base.sendReq({
                url: 'carBasicDataSelect/carBrand',
                method: 'POST',
                data: '',
                getResult: function (data) {
                   if (data.success) {
                        var carBrandHtml = template('modalBrandData', data);
                        $('#modalBrand').html(carBrandHtml);
                    }
                }
            });  
            $('#userManageModal').modal('show');

        });


        // 删除
        $('#delUserBtn').on('click', function () {
            var user = $("input[name=user]:checked"), userArr = [];
            if (user.size() == 0) {
                alert('请先选中要删除的行');
                return;
            }
            user.each(function () {
                var delId = $(this).parent().siblings('.user-id').text();
                userArr.push(delId);
            });

            userArr = JSON.stringify(userArr);
			//alert (userArr);exit;
            $('#userHintDialog').dialogBox({
                type: 'correct',  //three type:'normal'(default),'correct','error',
                width: 300,
                hasMask: true,
                hasClose: true,
                hasBtn: true,
                cancelValue: '取消',
                confirmValue: '确定',
                confirm: function () {
                    base.sendReq({
                        url: 'carBasic/carSeriesDelete',
                        data: userArr,
                        headers: {
                            'Content-Type': 'application/json;charset=UTF-8'
                        },
                        method: 'DELETE',
                        getResult: function (data) {
                            if (data.success) {
                                base.showAlertDialog('删除成功');
                                getUserList(1, pageSize);
                            } else {
                                base.showAlertDialog(data.errorMessage);
                            }
                        }
                    });
                },
                effect: 'sign',
                title: '提示',
                content: '是否删除选中用户？'});

        });
		// 提交新增或编辑用户请求
        $('#sendAddBtn').on('click', function () {
			base.showLoading("");
            var modalCode = $('#modalCode'), modalName = $('#modalName'), modalBrand = $('#modalBrand'), modalPic = $('#logoPicList');
			var hasCode = base.checkNotEmpty(modalCode),hasName = base.checkNotEmpty(modalName);
					if (hasCode && hasName) {		
					if ($('#modalStatus').val() == 1) {
                        modalStatus = false;
                    }else{
						modalStatus = true;
					}

                    var reqData = {
                       
						code: modalCode.val(),
                        name: modalName.val(),
						brandId: modalBrand.val(),
						pictureUrl: modalPic.children('img').attr('src'),
                        enable: modalStatus
                    };
                    if ($('#userModalTitle').text() == '编辑用户') {
                        url = 'carBasic/carSeriesUpdateOrInsert';
                        reqData.id = $('#modalUserId').val();
                    }else{
						url = 'carBasic/carSeriesUpdateOrInsert';
						
					}
                    base.sendReq({
                        url: url,
                        method: 'POST',
                        data: reqData,
                        getResult: function (data) {
							base.hideLoading();
                            if (data.success) {
                                $('#userManageModal').modal('hide');
                                base.showAlertDialog('信息保存成功');
                                getUserList(1, pageSize);
                            } else {
                                base.showAlertDialog(data.errorMessage);
                            }
                        }
                    });
					}
				});
		

       

        // 全选或取消全选
        base.selectAll('selectAll');

		//加载所属品牌
		$('#addUserBtn').on('click', function () {
		
        base.sendReq({
                url: 'carBasicDataSelect/carBrand',
                method: 'POST',
                data: '',
                getResult: function (data) {
                   if (data.success) {
                        var carBrandHtml = template('modalBrandData', data);
                        $('#modalBrand').html(carBrandHtml);
                    }
                }
            });    
		});	
		//加载所属品牌
	/* 	$('#brand').on('click', function () {
		
        base.sendReq({
                url: 'carBasicDataSelect/carBrand',
                method: 'POST',
                data: '',
                getResult: function (data) {
                   if (data.success) {
                        var carBrandHtml = template('modalBrandData', data);
                        $('#selectData').html(carBrandHtml);
                    }
                }
            });    
		});	 */
		
//模糊

/* var data = [
"MINI",
"奔腾"

];

$(document).ready(function() {
$(document).keydown(function(e) {
e = e || window.event;
var keycode = e.which ? e.which : e.keyCode;
if (keycode == 38) {
if (jQuery.trim($("#append").html()) == "") {
return;
}
movePrev();
} else if (keycode == 40) {
if (jQuery.trim($("#append").html()) == "") {
return;
}
$("#brand").blur();
if ($(".item").hasClass("addbg")) {
moveNext();
} else {
$(".item").removeClass('addbg').eq(0).addClass('addbg');
}
} else if (keycode == 13) {
dojob();
}
});
var movePrev = function() {
$("#brand").blur();
var index = $(".addbg").prevAll().length;
if (index == 0) {
$(".item").removeClass('addbg').eq($(".item").length - 1).addClass('addbg');
} else {
$(".item").removeClass('addbg').eq(index - 1).addClass('addbg');
}
}
var moveNext = function() {
var index = $(".addbg").prevAll().length;
if (index == $(".item").length - 1) {
$(".item").removeClass('addbg').eq(0).addClass('addbg');
} else {
$(".item").removeClass('addbg').eq(index + 1).addClass('addbg');
}
}
var dojob = function() {
$("#brand").blur();
var value = $(".addbg").text();
$("#brand").val(value);
$("#append").hide().html("");
}
});
function getContent(obj) {
var brand = jQuery.trim($(obj).val());
if (brand == "") {
$("#append").hide().html("");
return false;
}
var html = "";
for (var i = 0; i < data.length; i++) {
if (data[i].indexOf(brand) >= 0) {
html = html + "<div class='item' onmouseenter='getFocus(this)' onClick='getCon(this);'>" + data[i] + "</div>"
}
}
if (html != "") {
$("#append").show().html(html);
} else {
$("#append").hide().html("");
}
}
function getFocus(obj) {
$(".item").removeClass("addbg");
$(obj).addClass("addbg");
}
function getCon(obj) {
var value = $(obj).text();
$("#brand").val(value);
$("#append").hide().html("");
}
	 */
   


    });
});
