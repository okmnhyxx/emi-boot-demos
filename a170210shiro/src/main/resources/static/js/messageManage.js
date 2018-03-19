requirejs(['common'], function () {
    requirejs(['jquery', 'bootstrap', 'template', 'base', 'paging', 'datetimepicker', 'moment'], function ($, bootstrap, template, base, Paging,datetimepicker,moment) {
    
        var userData = {
            userList: [{
                userId: 12,
                messageId: 'zhangsan',
                messageName: '张三',
                messageTitle: '13212312312',
                messageTime: '123123@qq.com',
            },
                {
                    userId: 22,
                    messageId: 'zhangsan',
					messageName: '张三',
					messageTitle: '13212312312',
					messageTime: '123123@qq.com',
                },
                {
                    userId: 32,
                    messageId: 'zhangsan',
					messageName: '张三',
					messageTitle: '13212312312',
					messageTime: '123123@qq.com',
                }]
        };

        var userHtml = template('userDatas', userData);
        $('#messageTableBody').html(userHtml);

        var p = new Paging();
        p.init({target:'#pageTool',pagesize:10,count:100,toolbar:true, callback: function (page, size ,count) {
            console.log(page);
        }});
     //编辑信息
     $('body').on('click', '.edit-btn', function () {
            var id = $(this).parent().siblings('.user-id').text();
            // window.location.href = 'sellerManageEdit.html?id='+id;
            var parentMenu = sessionStorage.getItem('menu');
            $('#addTabMenu', parent.document).attr('data-addtab', parentMenu+'-edit').attr('url', 'views/messageManageEdit.html?id='+id).attr('title', '消息维护');
            window.parent.document.getElementById('addTabMenu').click();
        });
     //时间格式
	$(function () {
         $('#starttime,#endtime').datetimepicker({
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

       /*  $("#starttime").on("dp.change",function (e) {

         $('#endtime').data("DateTimePicker").minDate(e.date);

            });

        $("#endtime").on("dp.change",function (e) {

        $('#starttime').data("DateTimePicker").maxDate(e.date);

                });*/

        });

    // 全选或取消全选
        base.selectAll('selectAll');




    });
});