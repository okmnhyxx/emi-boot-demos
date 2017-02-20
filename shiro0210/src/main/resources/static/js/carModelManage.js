/**
 * Created by Administrator on 2016/12/8.
 */
requirejs(['common'], function () {
    requirejs(['jquery', 'bootstrap', 'template', 'base', 'paging'], function ($, bootstrap, template, base, Paging) {
         $(document).ready(function () {
        // 上传图片
        base.uploadPic('#uploadCarPic', '#logoPicList');
		
		
		var pageSize = 10, pageTotal = 10/* , brand = $('#brand'), modelCar = $('#modelCar') */;
		/* //所属品牌
		
		base.sendReq({
            url: 'carBasicDataSelect/carBrand',
            method: 'POST',
            data: '',
            getResult: function (data) {
                if (data.success) {
                    var brandHtml = template('brandData', data);
					$('#brand').html(brandHtml);
                  
                }
            }
        });
		//所属车型
		brand.on('change', function () {
            var brandId = $(this).val();
            if (base.isStringNull(brandId)) {
                modelCar.html('<option value="">全部</option>').val('');
               
            } else {
                base.sendReq({
                    url: 'carBasicDataSelect/carSeries',
                    method: 'POST',
                    data: {
                        brandId: brandId
                    },
                    getResult: function (data) {
                        if (data.success) {
                            var modelCarHtml = template('modelCarData', data);
                            modelCar.html(modelCarHtml).val('');
                          
                        }
                    }
                });
            }
        }); */
		
        // 加载列表
        function getUserList(pageNum, pageSize) {
			base.showLoading("");
            var brand = $('#brand').val(),series = $('#modelCar').val(),name = $('#modelName').val(),status = $('#status').val();
            if (status == 0) {
                status = '';
            } else if (status == 1) {
                status = true;
            } else {
                status = false;
            }
            var reqData = {
				brand: brand,
                series: series,
				name: name,
		
                enable: status,
                pageNum: pageNum,
                pageSize: pageSize
            };
            base.sendReq({
                url: 'carBasic/searchCarModel',
                method: 'GET',
                data: reqData,
                getResult: function (data) {
					base.hideLoading();
                    if (data.success) {
                        var userList = data.pageInfo;
                        var userHtml = template('userDatas', userList);
                        $('#carModelTableBody').html(userHtml);
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
                            $('#carModelTableBody').html('<tr><td colspan="15">没有符合条件的查询记录</td></tr>');
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
            $('#modelCar').val('');
            $('#modelName').val('');
            $('#status').val(0);
            getuserList(1, pageSize);
        });


        // 新增车型
        $('#addUserBtn').on('click', function () {
			 $('#userModalTitle').text('新增车型');
            $('#modalUserId').val('');
            $('#modalCode').val('').removeClass('error-border').siblings('div').addClass('hide');
            $('#modalName').val('').removeClass('error-border').siblings('div').addClass('hide');
            $('#modalBrand').val('').removeClass('error-border').siblings('div').addClass('hide');
			$('#modelCarName').val('').removeClass('error-border').siblings('div').addClass('hide');			
			$('#modalComment').val('').removeClass('error-border').siblings('div').addClass('hide');
            $('#modalStatus').val(1);
            $('#userManageModal').modal('show');
        });
		//所属品牌
		var modalBrand = $('#modalBrand'), modalName = $('#modalName');
		base.sendReq({
            url: 'carBasicDataSelect/carBrand',
            method: 'POST',
            data: '',
            getResult: function (data) {
                if (data.success) {
                    var modalBrandHtml = template('modalBrandData', data);
					$('#modalBrand').html(modalBrandHtml);
                  
                }
            }
        });
		//所属车型
		modalBrand.on('change', function () {
            var brandId = $(this).val();
            if (base.isStringNull(brandId)) {
                modelCar.html('<option value="">全部</option>').val('');
               
            } else {
                base.sendReq({
                    url: 'carBasicDataSelect/carSeries',
                    method: 'POST',
                    data: {
                        brandId: brandId
                    },
                    getResult: function (data) {
                        if (data.success) {
                            var modalNameHtml = template('modalNameData', data);
                            modalName.html(modalNameHtml).val('');
                          
                        }
                    }
                });
            }
        });

        // 编辑车型
		 $('body').on('click', '.edit-btn', function () {
            $('#userModalTitle').text('编辑车型');
            var data = [];
            $(this).parents('tr').children('td').each(function (i) {
                data[i] = $(this).text();
            });
            var userId = data[2], Code = data[3], Brand = data[4], Name = data[5], CarName = data[6],Comment = data[7], status = data[8];
            $('#modalUserId').val(userId);
            $('#modalCode').val(Code).removeClass('error-border').siblings('div').addClass('hide');
			$('#modalBrand').val(Brand).removeClass('error-border').siblings('div').addClass('hide');
            $('#modalName').val(Name).removeClass('error-border').siblings('div').addClass('hide');
			$('#modalCarName').val(CarName).removeClass('error-border').siblings('div').addClass('hide');
			$('#modalComment').val(Comment).removeClass('error-border').siblings('div').addClass('hide');
           
            
            if (status == '可用') {
                $('#modalStatus').val(0);
            } else {
                $('#modalStatus').val(1);
            }

		
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
                        url: 'carBasic/carModelDelete',
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
                content: '是否删除选中？'});

        });
      

        // 全选或取消全选
        base.selectAll('selectAll');

        // 提交新增或编辑用户请求
         $('#sendAddBtn').on('click', function () {
            var modalCode = $('#modalCode'), modalName = $('#modalName'), modalBrand = $('#modalBrand'), modelCarName = $('#modelCarName'), modalComment = $('#modalComment');
			var hasCode = base.checkNotEmpty(modalCode),hasCarName = base.checkNotEmpty(modelCarName);
					if (hasCode && hasCarName) {		
					if ($('#modalStatus').val() == 1) {
                        modalStatus = false;
                    }else{
						modalStatus = true;
					}

                    var reqData = {
                       
						code: modalCode.val(),
                        seriesId: modalName.val(),
						brandId: modalBrand.val(),
						name: modelCarName.val(),
						comment: modalComment.val(),
                        enable: modalStatus
                    };
                    if ($('#userModalTitle').text() == '编辑车型') {
                        url = 'carBasic/carModelUpdateOrInsert';
                        reqData.id = $('#modalUserId').val();
                    }else{
						url = 'carBasic/carModelUpdateOrInsert';
						
					}
                    base.sendReq({
                        url: url,
                        method: 'POST',
                        data: reqData,
                        getResult: function (data) {
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
		//
		
		
 

    });
});
});
