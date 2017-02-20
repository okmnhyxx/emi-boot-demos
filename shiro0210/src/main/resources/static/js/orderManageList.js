/**
 * Created by Administrator on 2016/12/22.
 */
requirejs(['common'], function () {
    requirejs(['jquery', 'bootstrap', 'template', 'base', 'paging', 'datetimepicker'], function ($, bootstrap, template, base, Paging, datetimepicker) {
        var loc = location.href;
        var n1 = loc.length;//地址的总长度
        var n2 = loc.indexOf("=");//取得=号的位置
        var orderType = decodeURI(loc.substr(n2+1, n1-n2));//从=号后面的内容
        var pageSize = 10, pageTotal = 10, pageTitle = '', prjTh = $('#projectTh'), serTh = $('#serviceTh'), editTitle = '';
        orderType = parseInt(orderType);

        switch (orderType) {
            case 1:
                pageTitle = '洗车订单列表';
                prjTh.text('洗车项目');
                $('.search-wash').removeClass('hide');
                prjTh.removeClass('hide');
                editTitle = '洗车订单详情';
                break;
            case 2:
                pageTitle = '美容订单列表';
                prjTh.removeClass('hide');
                prjTh.text('美容项目');
                editTitle = '美容订单详情';
                break;
            case 3:
                pageTitle = '保养订单列表';
                editTitle = '保养订单详情';
                break;
            case 4:
                pageTitle = '快速维修订单列表';
                editTitle = '快速维修订单详情';
                serTh.removeClass('hide');
                break;
            case 5:
                pageTitle = '故障维修订单列表';
                editTitle = '故障维修订单详情';
                serTh.removeClass('hide');
                break;
            case 6:
                pageTitle = '事故维修订单列表';
                editTitle = '事故维修订单详情';
                serTh.removeClass('hide');
                break;
        }

        $('#pageTitle').text(pageTitle);
        
        $('#searchTime1,#searchTime2').datetimepicker({
            language:  'zh',
            format: 'yyyy-mm-dd',
            weekStart: 1,
            todayBtn:  1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        });

        if (orderType == 1) {
            base.sendReq({
                url: 'select/carWashType',
                method: 'GET',
                getResult: function (data) {
                    if (data.success) {
                        var washHtml = template('washPrjDatas', data);
                        $('#washProject').html(washHtml);
                    } else {
                        base.showAlertDialog(data.errorMessage);
                    }
                }
            });
        }

        function getOrderList(pageNum, pageSize) {
            var sellerName = $('#sellerName').val(), clientName = $('#clientName').val(), clientPhone = $('#clientPhone').val(), orderStatus = $('#orderStatus').val(),
                searchTime1 = $('#searchTime1').val(), searchTime2 = $('#searchTime2').val(), washProject = $('#washProject').val();
            var searchTime11 = searchTime1.split('-'), searchTime12 = searchTime2.split('-');
            var isCorrectDate = true;
            for (var i = 0; i < searchTime11.length; i++) {
                if (searchTime11[i] > searchTime12[i]) {
                    isCorrectDate = false;
                }
            }
            if (isCorrectDate) {
                base.showLoading('');
                var serviceType = orderType;
                if (orderType == 5 || orderType == 6) {
                    serviceType = 4;
                }
                var reqData = {
                    serviceType: serviceType,
                    storeName: sellerName,
                    customer: clientName,
                    customerPhone: clientPhone,
                    state: orderStatus,
                    start: searchTime1,
                    end: searchTime2,
                    pageNum: pageNum,
                    pageSize: pageSize
                };
                if (orderType == 1) {
                    reqData.typeName = washProject;
                }
                base.sendReq({
                    url: 'order/search',
                    method: 'GET',
                    data: reqData,
                    getResult: function (data) {
                        base.hideLoading();
                        if (data.success) {
                            template.helper('isSelect', function (data) {
                                if (data == true || data == 'true') {
                                    return 'list-check';
                                } else {
                                    return '';
                                }
                            });
                            template.helper('isEnable', function (data) {
                                if (data == true || data == 'true') {
                                    return '';
                                } else {
                                    return 'disabled';
                                }
                            });
                            var orderList = data.pageInfo;
                            var orderHtml = template('sellerDatas', orderList);
                            $('#orderTableBody').html(orderHtml);
                            pageTotal = orderList.total;
                            var pageNum = orderList.total/orderList.size;
                            if (orderList.total%orderList.size != 0) {
                                pageNum += 1;
                            }
                            p.render({
                                count: orderList.total,
                                pagecount: pageNum,
                                current: orderList.pageNum,
                                pagesize: orderList.pageSize
                            });
                            if ($('#pageTool').hasClass('hide')) {
                                $('#pageTool').removeClass('hide');
                            }

                            var serviceItem = $('.service-item'), projectItem = $('.project-item');
                            switch (orderType) {
                                case 1:case 2:
                                projectItem.removeClass('hide');
                                break;
                                case 4:case 5:case 6:
                                serviceItem.removeClass('hide');
                                break;
                            }
                        } else {
                            if (data.errorCode === 200004) {
                                $('#orderTableBody').html('<tr><td colspan="18">没有符合条件的查询记录</td></tr>');
                                $('#pageTool').addClass('hide');
                            } else {
                                base.showAlertDialog(data.errorMessage);
                            }
                        }
                    }

                });
            }

        }

        var p = new Paging();
        p.init({target:'#pageTool',pagesize:pageSize,count:pageTotal,toolbar:true, callback: function (page, size ,count) {
            pageSize = size;
            getOrderList(page, pageSize);
        }});

        $(document).ready(function () {
            getOrderList(1, 10);
        });

        // 搜索
        $('#searchBtn').on('click', function () {
            getOrderList(1, pageSize);
        });

        // 重置
        $('#resetBtn').on('click', function () {
            $('#sellerName').val('');
            $('#clientName').val('');
            $('#clientPhone').val('');
            $('#orderStatus').val('');
            $('#searchTime1').val('');
            $('#searchTime2').val('');
            $('#washProject').val('');
            getOrderList(1, pageSize);
        });


        // 查看订单详情
        $('body').on('click', '.edit-btn', function () {
            var id = $(this).parent().siblings('.order-id').text();
            var parentMenu = sessionStorage.getItem('menu');
            $('#addTabMenu', parent.document).attr('data-addtab', parentMenu+'-detail').attr('url', 'views/orderManageDetail.html?id='+id+'&orderType='+orderType).attr('title', editTitle);
            
            window.parent.document.getElementById('addTabMenu').click();
        });

        // 删除订单
        $('#delOrderBtn').on('click', function () {
            var seller = $("input[name=order]:checked"), orderArr = [];
            if (seller.size() == 0) {
                base.showAlertDialog('请先选中要删除的行');
                return;
            }
            seller.each(function () {
                var delId = $(this).parent().siblings('.order-id').text();
                orderArr.push(delId);
            });

            orderArr = JSON.stringify(orderArr);
            $('#orderDialogBox').dialogBox({
                type: 'correct',  //three type:'normal'(default),'correct','error',
                width: 300,
                hasMask: true,
                hasClose: true,
                hasBtn: true,
                cancelValue: '取消',
                confirmValue: '确定',
                confirm: function () {
                    base.sendReq({
                        url: 'order/delete',
                        data: orderArr,
                        headers: {
                            'Content-Type': 'application/json;charset=UTF-8'
                        },
                        method: 'DELETE',
                        getResult: function (data) {
                            if (data.success) {
                                base.showAlertDialog('删除成功');
                                getOrderList(1, pageSize);
                            } else {
                                base.showAlertDialog(data.errorMessage);
                            }
                        }
                    });
                },
                effect: 'sign',
                title: '提示',
                content: '是否删除选中订单？'});
        });

        // 全选或取消全选
        base.selectAll('selectAll');
    });
});