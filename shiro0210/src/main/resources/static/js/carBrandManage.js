/**
 * Created by Administrator on 2016/12/8.
 */
requirejs(['common'], function () {
    requirejs(['jquery', 'bootstrap', 'template', 'Qiniu', 'base', 'paging'], function ($, bootstrap, template,  Qiniu,base, Paging) {
	var	uplLogoObj;
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

                     uplLogoObj = base.uploadQiniuPic('uploadLogoPic', 'uploader-logo-container', 'logoPicList', data.token);
				
                     } else {	
                         base.showAlertDialog('');
                     }
                 }
             });
		   });
	
	
		var pageSize = 10, pageTotal = 10;
		
        // 加载列表
        function getUserList(pageNum, pageSize) {
			base.showLoading('');
            var brand = $('#brand').val(),code = $('#code').val(),status = $('#status').val();
            if (status == 0) {
                status = '';
            } else if (status == 1) {
                status = true;
            } else {
                status = false;
            }
            var reqData = {
				name: brand,
                code: code,  
                enable: status,
                pageNum: pageNum,
                pageSize: pageSize
            };
            base.sendReq({
                url: 'carBasic/searchCarBrand',
                method: 'GET',
                data: reqData,
                getResult: function (data) {
					base.hideLoading();
                    if (data.success) {
                        var userList = data.pageInfo;
                        var userHtml = template('userDatas', userList);
                        $('#carBrandTableBody').html(userHtml);
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
                            $('#carBrandTableBody').html('<tr><td colspan="15">没有符合条件的查询记录</td></tr>');
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
            $('#status').val(0);
            getuserList(1, pageSize);
        });


        // 新增品牌
        $('#addUserBtn').on('click', function () {
            $('#userModalTitle').text('新增品牌');
            $('#modalUserId').val('');
            $('#modalCode').val('').removeClass('error-border').siblings('div').addClass('hide');
            $('#modalName').val('').removeClass('error-border').siblings('div').addClass('hide');
            $('#modalCountry').val('').removeClass('error-border').siblings('div').addClass('hide');
            $('#modalHot').val(0);
			$('#uploadLogoPic').val('').removeClass('error-border').siblings('div').addClass('hide');
			$('#modalStatus').val(0);
            $('#userManageModal').modal('show');
        });

        // 编辑品牌
        $('body').on('click', '.edit-btn', function () {
            $('#userModalTitle').text('编辑品牌');
            var data = [];
            $(this).parents('tr').children('td').each(function (i) {
                data[i] = $(this).text();
            });
            var userId = data[2], Code = data[3], Name = data[4], Country = data[5], Hot = data[6], Pic = data[7], status = data[8];
            $('#modalUserId').val(userId);
            $('#modalCode').val(Code).removeClass('error-border').siblings('div').addClass('hide');
            $('#modalName').val(Name).removeClass('error-border').siblings('div').addClass('hide');
            $('#modalCountry').val(Country).removeClass('error-border').siblings('div').addClass('hide');
			$('#uploadLogoPic').val(Pic).removeClass('error-border').siblings('div').addClass('hide');
			if (Hot == '是') {
                $('#modalHot').val(0);
            } else {
                $('#modalHot').val(1);
            }
            
            if (status == '可用') {
                $('#modalStatus').val(0);
            } else {
                $('#modalStatus').val(1);
            }
            $('#userManageModal').modal('show');

        });

		// 删除品牌
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
                        url: 'carBasic/carBrandDelete',
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
                content: '是否删除选中品牌？'});

        });
      
       
        // 全选或取消全选
        base.selectAll('selectAll');
		
		
		
        // 提交新增或编辑用户请求
        $('#sendAddBtn').on('click', function () {
			
            var  modalCode = $('#modalCode'), modalName = $('#modalName'), modalCountry = $('#modalCountry'), modalPic = $('#logoPicList');
			var hasCode = base.checkNotEmpty(modalCode),hasName = base.checkNotEmpty(modalName);
					if (hasCode && hasName) {
					if ($('#modalHot').val() == 1) {
						modalHot = false;
					} else {
						modalHot = true;
					}
					if ($('#modalStatus').val() == 1) {
                        modalStatus = false;
                    }else{
						modalStatus = true;
					}

                    var reqData = {
                       
						code: modalCode.val(),
                        name: modalName.val(),
                        countryCode: modalCountry.val(),
                        hot: modalHot,
						logo: modalPic.children('img').attr('src'),
                        enable: modalStatus
                    };
                    if ($('#userModalTitle').text() == '编辑品牌') {
                        url = 'carBasic/CarBrandUpdateOrInsert';
                        reqData.id = $('#modalUserId').val();
                    }else{
						url = 'carBasic/CarBrandUpdateOrInsert';
						
					}
                    base.sendReq({
                        url: url,
                        method: 'POST',
                        data: reqData,
                        getResult: function (data) {
                            if (data.success) {
                                $('#userManageModal').modal('hide');
                                base.showAlertDialog('品牌信息保存成功');
                                getUserList(1, pageSize);
                            } else {
                                base.showAlertDialog(data.errorMessage);
                            }
                        }
                    });
					}
				});
          

		
			


	});		
});


   
      




