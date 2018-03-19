/**
 * Created by Administrator on 2016/12/27.
 */
/**
 * Created by Administrator on 2016/12/12.
 */
requirejs(['common'], function () {
    requirejs(['jquery', 'bootstrap', 'template', 'base', 'paging'], function ($, bootstrap, template, base, Paging) {
        var pageSize = 10, pageTotal = 10;

        // 加载列表
        function getProductList() {
            var productName = $('#productName'), brand = $('#brand'), maintType = $('#maintType');
            base.sendReq({
                url: '',
                method: 'GET',
                data: {},
                getResult: function (data) {
                    if (data.success) {

                    } else {
                        if (data.errorCode === 200004) {
                            $('#orderTableBody').html('<tr><td colspan="11">没有符合条件的查询记录</td></tr>');
                            $('#pageTool').addClass('hide');
                        } else {
                            base.showAlertDialog(data.errorMessage);
                        }
                    }
                }
            });
        }

         $('#selectMaintType').on('change', function () {
             var searchContent = $('#searchContent');
             if (searchContent.hasClass('hide')) {
                 searchContent.removeClass('hide');
             }
             getProductList();
         });

        // 重置
        $('#resetBtn').on('click', function () {
            $('#productName').val('');
            $('#brand').val('');
            $('#maintType').val('');
            getProductList(1, pageSize);
        });

        var p = new Paging();
        p.init({target:'#pageTool',pagesize:pageSize,count:pageTotal,toolbar:true, callback: function (page, size ,count) {
            pageSize = size;
            getProductList(page, pageSize);
        }});
    });
});