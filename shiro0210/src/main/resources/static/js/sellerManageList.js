/**
 * Created by Administrator on 2016/12/12.
 */
requirejs(['common'], function () {
    requirejs(['jquery', 'bootstrap', 'template', 'base', 'paging'], function ($, bootstrap, template, base, Paging) {
        var pageSize = 10, pageTotal = 10, locateProvince = $('#locateProvince'), locateCity = $('#locateCity'), locateDistrict = $('#locateDistrict');

        // 加载省
        base.sendReq({
            url: 'districtSelect/province',
            method: 'POST',
            data: '',
            getResult: function (data) {
                if (data.success) {
                    var provinceHtml = template('provinceData', data);
                    $('#locateProvince').html(provinceHtml);
                }
            }
        });

        // 加载市
        locateProvince.on('change', function () {
            var provinceId = $(this).val();
            if (base.isStringNull(provinceId)) {
                locateCity.html('<option value="">全部</option>').val('');
                locateDistrict.html('<option value="">全部</option>').val('');
            } else {
                base.sendReq({
                    url: 'districtSelect/city',
                    method: 'POST',
                    data: {
                        provinceId: provinceId
                    },
                    getResult: function (data) {
                        if (data.success) {
                            var cityHtml = template('cityData', data);
                            locateCity.html(cityHtml).val('');
                            locateDistrict.html('<option value="">全部</option>').val('');
                        }
                    }
                });
            }
        });

        // 加载区
        locateCity.on('change', function () {
            var cityId = $(this).val();
            if (base.isStringNull(cityId)) {
                locateDistrict.html('<option value="">全部</option>').val('');
            } else {
                base.sendReq({
                    url: 'districtSelect/district',
                    method: 'POST',
                    data: {
                        cityId: cityId
                    },
                    getResult: function (data) {
                        if (data.success) {
                            var districtHtml = template('districtData', data);
                            locateDistrict.html(districtHtml).val('');
                        }
                    }
                });
            }
        });
        
        function getSellerList(pageNum, pageSize) {
            var sellerName = $('#sellerName').val(), contact = $('#contact').val(), contactPhone = $('#contactPhone').val(), locateProvince = $('#locateProvince').val(),
                locateCity = $('#locateCity').val(), locateDistrict = $('#locateDistrict').val(), carWash = $('#carWash').prop('checked'),
                carBeauty = $('#carBeauty').prop('checked'), carMaintenance = $('#carMaintenance').prop('checked'), carRepair = $('#carRepair').prop('checked');
            var reqData = {
                storeName: sellerName,
                concat: contact,
                phone: contactPhone,
                provinceId: locateProvince,
                cityId: locateCity,
                districtId: locateDistrict,
                pageNum: pageNum,
                pageSize: pageSize
            };
            base.showLoading('');
            base.sendReq({
                url: 'merchant/search',
                method: 'GET',
                data: reqData,
                getResult: function (data) {
                    base.hideLoading();
                    if (data.success) {
                        var sellerList = data.page;
                        template.helper('stateName', function (data) {
                           if (data == 0) {
                               return '待审核';
                           } else if (data == 1) {
                               return '可用';
                           } else if (data == 2) {
                               return '不通过';
                           } else if (data == 3) {
                               return '停用';
                           }
                        });
                        var sellerHtml = template('sellerDatas', sellerList);
                        // console.log(data);
                        $('#sellerTableBody').html(sellerHtml);
                        pageTotal = sellerList.total;
                        var pageNum = sellerList.total/sellerList.size;
                        if (sellerList.total%sellerList.size != 0) {
                            pageNum += 1;
                        }
                        p.render({
                            count: sellerList.total,
                            pagecount: pageNum,
                            current: sellerList.pageNum,
                            pagesize: sellerList.pageSize
                        });
                        if ($('#pageTool').hasClass('hide')) {
                            $('#pageTool').removeClass('hide');
                        }
                    } else {
                        if (data.errorCode === 200004) {
                            $('#sellerTableBody').html('<tr><td colspan="15">没有符合条件的查询记录</td></tr>');
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
            getSellerList(page, pageSize);
        }});
        getSellerList(1, 10);

        // 搜索
        $('#searchBtn').on('click', function () {
            getSellerList(1, pageSize);
        });

        // 重置
        $('#resetBtn').on('click', function () {
            $('#sellerName').val('');
            $('#contact').val('');
            $('#contactPhone').val('');
            $('#locateProvince').val('');
            $('#locateCity').html('<option value="">全部</option>');
            $('#locateDistrict').html('<option value="">全部</option>');
            $('#carWash').prop('checked', false);
            $('#carBeauty').prop('checked', false);
            $('#carMaintenance').prop('checked', false);
            $('#carRepair').prop('checked', false);
            getSellerList(1, pageSize);
        });


        // 编辑商家
        $('body').on('click', '.edit-btn', function () {
            var id = $(this).parent().siblings('.seller-id').text();
            // window.location.href = 'sellerManageEdit.html?id='+id;
            var parentMenu = sessionStorage.getItem('menu');
            $('#addTabMenu', parent.document).attr('data-addtab', parentMenu+'-edit').attr('url', 'views/sellerManageEdit.html?id='+id).attr('title', '商家维护');
            window.parent.document.getElementById('addTabMenu').click();
        });

        // 查看商家账单
        $('body').on('click', '.account-btn', function () {
            var id = $(this).parent().siblings('.seller-id').text();
            // window.location.href = 'sellerManageEdit.html?id='+id;
            var parentMenu = sessionStorage.getItem('menu');
            $('#addTabMenu', parent.document).attr('data-addtab', parentMenu+'-account').attr('url', 'views/sellerAccountList.html?id='+id).attr('title', '商家账单');
            window.parent.document.getElementById('addTabMenu').click();
        });

        // 删除商家
        $('#delSellerBtn').on('click', function () {
            var seller = $("input[name=seller]:checked"), sellerArr = [];
            if (seller.size() == 0) {
                base.showAlertDialog('请先选中要删除的行');
                return;
            }
            seller.each(function () {
                var delId = $(this).parent().siblings('.seller-id').text();
                sellerArr.push(delId);
            });

            sellerArr = JSON.stringify(sellerArr);
            $('#sellerDialogBox').dialogBox({
                type: 'correct',  //three type:'normal'(default),'correct','error',
                width: 300,
                hasMask: true,
                hasClose: true,
                hasBtn: true,
                cancelValue: '取消',
                confirmValue: '确定',
                confirm: function () {
                    base.sendReq({
                        url: 'merchant/delete',
                        data: sellerArr,
                        headers: {
                            'Content-Type': 'application/json;charset=UTF-8'
                        },
                        method: 'POST',
                        getResult: function (data) {
                            if (data.success) {
                                base.showAlertDialog('删除成功');
                                getSellerList(1, pageSize);
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

        // 重置密码
        $('.pwd-btn').on('click', function () {
            $(this).parent().siblings('user-id').text();
            base.sendReq({
                url: ''
            });
        });

        // 全选或取消全选
        base.selectAll('selectAll');

        // 验证
        base.checkTel('#modalPhone', true, 0);
        base.checkMail('#modalMail', true, 0);


    });
});