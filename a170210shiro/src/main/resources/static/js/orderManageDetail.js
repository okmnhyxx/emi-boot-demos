/**
 * Created by Administrator on 2016/12/12.
 */
requirejs(['common'], function () {
    requirejs(['jquery', 'bootstrap', 'template', 'swipebox', 'base'], function ($, bootstrap, template, swipebox, base) {
        base.showLoading('');
        var loc = location.href;
        var locArr = loc.split('=');
        var idArr = locArr[1].split('&');
        var id = 0, orderType = 0, carPrj1 = $('.project-td1'), carPrj2 = $('.project-td2');
        if (!base.isStringNull(idArr[0]) && !base.isStringNull(locArr[2])) {
            id = idArr[0];
            orderType = parseInt(locArr[2]);
            var secondTitle = $('.detail-second-title'), maintFast = $('.maint-fast'), troubleAccident = $('.trouble-accident'),
                trouble = $('.trouble'), accident = $('.accident');
            switch (orderType) {
                case 1:
                    carPrj1.text('洗车项目');
                    break;
                case 2:
                    carPrj1.text('美容项目');
                    break;
                case 3:
                case 4:
                    carPrj1.attr('colspan', 2).text('');
                    carPrj2.addClass('hide');
                    secondTitle.removeClass('hide').text('商品列表');
                    maintFast.removeClass('hide');
                    break;
                case 5:
                    carPrj1.attr('colspan', 2).text('');
                    carPrj2.addClass('hide');
                    secondTitle.removeClass('hide').text('故障相关信息');
                    troubleAccident.removeClass('hide');
                    trouble.removeClass('hide');
                    break;
                case 6:
                    carPrj1.attr('colspan', 2).text('');
                    carPrj2.addClass('hide');
                    secondTitle.removeClass('hide').text('事故相关信息');
                    troubleAccident.removeClass('hide');
                    accident.removeClass('hide');
                    break;

            }
        } else {
            base.showAlertDialog('查看订单详情发生错误');
        }

        $(document).ready(function () {
            // 加载订单详情数据
            base.sendReq({
                url: 'order/details/'+id,
                method: 'GET',
                data: {
                    id: id
                },
                getResult: function (data) {
                    base.hideLoading();
                    if (data.success) {
                        var orderInfo = data.orderDetailsVo;
                        var clientPhone = base.setStringNull(orderInfo.customerPhone), labourFee = base.setStringNull(orderInfo.manualPriceStr),
                            carFee = base.setStringNull(orderInfo.doorPriceStr), totalPrice = base.setStringNull(orderInfo.allProductPriceStr),
                            coupon = base.setStringNull(orderInfo.couponAmountStr), payAmount = base.setStringNull(orderInfo.allRealPriceStr);
                        $('#orderNum').text(orderInfo.orderNo);
                        $('#clientName').text(orderInfo.customer);
                        $('#orderTime').text(orderInfo.orderTime);
                        $('#clientPhone').text(clientPhone);
                        $('#orderStatus').text(orderInfo.stateStr);
                        $('#sellerName').text(orderInfo.storeName);
                        $('#carBrand').text(orderInfo.carBrand);
                        $('#carFee').text(carFee);
                        $('#carType').text(orderInfo.carSeries);
                        $('#labourFee').text(labourFee);
                        $('#carModel').text(orderInfo.carModel);
                        $('#totalPrice').text(totalPrice);
                        $('#serviceTime').text(orderInfo.appointTime);
                        $('#coupon').text(coupon);
                        $('#payAmount').text(payAmount);
                        $('#payWay').text(orderInfo.payTypeStr);
                        if (orderType == 1 || orderType == 2) {
                            $('#carPrj').text(base.setStringNull(orderInfo.serviceName));
                        }

                        if (orderInfo.doorService == false || orderInfo.doorService == 'false') {
                            $('#isGetCar').text('否');
                        } else if (orderInfo.doorService == true || orderInfo.doorService == 'true') {
                            $('#isGetCar').text('是');
                        } else {
                            $('#isGetCar').text(orderInfo.doorService);
                        }

                        if (orderInfo.itemVoList.length > 0) {
                            var orderGoodsHtml = template('goodsDatas', orderInfo);
                            $('#orderGoodsTableBody').html(orderGoodsHtml);
                        }
                        $('.swipebox').swipebox();
                    } else {
                        base.showAlertDialog(data.errorMessage);
                    }
                }
            });
        });

    });
});