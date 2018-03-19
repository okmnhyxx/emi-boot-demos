/**
 * Created by Administrator on 2016/12/8.
 */
requirejs(['common'], function () {
    requirejs(['jquery', 'bootstrap', 'template', 'base', 'paging'], function ($, bootstrap, template, base, Paging) {
        var pageSize = 10, pageTotal = 10;
        // 加载列表
        function getUserList(pageNum, pageSize) {
			base.showLoading("");
            var Province = $('#Province').val(),status = $('#status').val();
            if (status == 0) {
                status = '';
            } else if (status == 1) {
                status = true;
            } else {
                status = false;
            }
            var reqData = {
                name: Province,  
                enable: status,
                pageNum: pageNum,
                pageSize: pageSize
            };
            base.sendReq({
				
                url: 'district/searchProvince',
                method: 'GET',
                data: reqData,
                getResult: function (data) {
					base.hideLoading();
                    if (data.success) {
                        var userList = data.page;
                        var userHtml = template('userDatas', userList);
                        $('#provinceTableBody').html(userHtml);
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
                            $('#provinceTableBody').html('<tr><td colspan="15">没有符合条件的查询记录</td></tr>');
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
		//更改状态
		$('body').on('click', '.edit-btn', function () {
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

        });
		//提交
		$('#sendAddBtn').on('click', function () {
				base.showLoading("");
					
					if ($('#modalStatus').val() == 1) {
                        modalStatus = false;
                    }else{
						modalStatus = true;
					}
                    var reqData = { 
						
                        enable: modalStatus
                    };
					 if ($('#userModalTitle').text() == '更改状态') {                 
                        url = 'district/changeProvinceState';
                        reqData.id = $('#modalUserId').val();
                    }
                    base.sendReq({
                        url: url,
                        method: 'POST',
                        data: reqData,
                        getResult: function (data) {
							base.hideLoading();
                            if (data.success) {
                                $('#userManageModal').modal('hide');
                                base.showAlertDialog('用户信息保存成功');
                                getUserList(1, pageSize);
                            } else {
                                base.showAlertDialog(data.errorMessage);
                            }
                        }
                    });
                
            });



       

		
		

		// 搜索
        $('#searchBtn').on('click', function () {
            getUserList(1, pageSize);
        });
		// 全选或取消全选
        base.selectAll('selectAll');
		
		
		
    });
});