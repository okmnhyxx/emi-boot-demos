/**
 * Created by Administrator on 2016/12/8.
 */
requirejs(['common'], function () {
    requirejs(['jquery', 'bootstrap', 'template', 'base', 'paging'], function ($, bootstrap, template, base, Paging) {
        var pageSize = 10, pageTotal = 10;
        // 加载列表
        function getUserList(pageNum, pageSize) {
			base.showLoading("");
            var tureName = $('#name').val(), phone = $('#phone').val(),sexType = $('#sexType').val();
			 if (sexType == 2) {
                sexType = '';
            } else if (sexType == 0) {
                sexType = 0;
            } else {
                sexType = 1;
            }
            var reqData = {
                name: tureName,
                phone: phone,
                sexType:sexType,
                pageNum: pageNum,
                pageSize: pageSize
            };
            base.sendReq({
                url: 'carUser/search',
                method: 'Get',
                data: reqData,
                getResult: function (data) {
					base.hideLoading();
                    if (data.success) {
                        var userList = data.pageInfo;
                        var userHtml = template('userDatas', userList);
                        $('#carOwnerTableBody').html(userHtml);
                        //console.log(userList);
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
                    } else {
                        base.showAlertDialog(data.errorMessage);
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
        //搜索
	    $('#carOwnerSearch').on('click',function(){
		
			getUserList(1,pageSize);
		});
	  	//重置
		 $('#resetBtn').on('click', function () {
            $('#name').val('');
            $('#phone').val('');
            $('#sexType').val(2);
            getUserList(1, pageSize);
        });
		//编辑
		 $('body').on('click','.edit-btn', function () {
            var id = $(this).parent().siblings('.user-id').text();
            var parentMenu = sessionStorage.getItem('menu');
            $('#addTabMenu', parent.document).attr('data-addtab', parentMenu+'-edit').attr('url', 'views/carOwnerDetail.html?id='+id).attr('title', '车主详情');
            window.parent.document.getElementById('addTabMenu').click();
        }); 
         
		//更改状态
	/* 	$('body').on('click', '.edit-btn', function () {
            $('#userModalTitle').text('更改状态');
            var data = [];
            $(this).parents('tr').children('td').each(function (i) {
                data[i] = $(this).text();
            });
            var userId = data[2],status = data[4];
			$('#modalUserId').val(userId);
            if (status == '可用') {
                $('#modalStatus').val(0);
            } else {
                $('#modalStatus').val(1);
            }
            $('#userManageModal').modal('show');

        }); */

     //提交
		/* $('#sendAddBtn').on('click', function () {
				
					
					if ($('#modalStatus').val() == 1) {
                        modalStatus = false;
                    }else{
						modalStatus = true;
					}
                    var reqData = { 
						
                        enable: modalStatus
                    };
					 if ($('#userModalTitle').text() == '更改状态') {                 
                        url = 'carUser/changeCarUserEnable';
                        reqData.id = $('#modalUserId').val();
                    }
                    base.sendReq({
                        url: url,
                        method: 'POST',
                        data: reqData,
                        getResult: function (data) {
                            if (data.success) {
                                $('#userManageModal').modal('hide');
                                base.showAlertDialog('用户信息保存成功');
                                getUserList(1, pageSize);
                            } else {
                                base.showAlertDialog(data.errorMessage);
                            }
                        }
                    });
                
            }); */


        // 全选或取消
        base.selectAll('selectAll');


        // 验证
        base.checkTel('#modalPhone', true, 0);



    });


        


       


});