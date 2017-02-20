/**
 * Created by Administrator on 2016/12/8.
 */
requirejs(['common'], function () {
    requirejs(['jquery', 'bootstrap', 'template', 'base', 'paging'], function ($, bootstrap, template, base, Paging) {
        base.showLoading('');
        var loc = location.href;
        var n1 = loc.length;//地址的总长度
        var n2 = loc.indexOf("=");//取得=号的位置
        var id = decodeURI(loc.substr(n2 + 1, n1 - n2));//从=号后面的内容
        var pageSize = 10, pageTotal = 10;

        var p = new Paging();
        p.init({target:'#pageTool',pagesize:pageSize,count:pageTotal,toolbar:true, callback: function (page, size ,count) {
            pageSize = size;
            getSellerAccountList(page, pageSize);
        }});
        getSellerAccountList(1, 10);

        // 搜索
        $('#searchBtn').on('click', function () {
            getSellerAccountList(1, pageSize);
        });

        // 重置
        $('#resetBtn').on('click', function () {
            $('#orderNum').val('');
            $('#incomeType').val('');
            getSellerAccountList(1, pageSize);
        });



        // 全选或取消全选
        base.selectAll('selectAll');

        // 加载列表
        function getSellerAccountList(page, size) {
            var orderNum = $('#orderNum').val(), incomeType = $('#incomeType').val();
            var reqData = {
                storeId: id,
                incomeType: incomeType,
                orderNo: orderNum,
                pageNum: page,
                pageSize: size
            };
            base.sendReq({
                url: 'merchant/storeIncomeDetail',
                method: 'POST',
                data: reqData,
                getResult: function (data) {
                    base.hideLoading();
                    if (data.success){
                        if (!base.isStringNull(data.totalIncome)) {
                            $('#totalIncome').text(data.totalIncome);
                        } else {
                            $('#totalIncome').text(0);
                        }
                        var userHtml = template('sellerAccountDatas', data.pageInfo);
                        $('#sellerAccountTableBody').html(userHtml);
                        $('#pageTool').removeClass('hide');
                    } else {
                        if (data.errorCode === 200004) {
                            $('#sellerAccountTableBody').html('<tr><td colspan="6">没有符合条件的查询记录</td></tr>');
                            $('#pageTool').addClass('hide');
                        } else {
                            base.showAlertDialog(data.errorMessage);
                        }
                    }
                }

            });
        }
    });
});