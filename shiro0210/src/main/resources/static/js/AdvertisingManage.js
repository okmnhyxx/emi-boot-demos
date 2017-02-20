requirejs(['common'], function () {
    requirejs(['jquery', 'bootstrap', 'template', 'base', 'paging', 'datetimepicker', 'moment'], function ($, bootstrap, template, base, Paging,datetimepicker,moment) {
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
     });
	 //图片上传
	  base.uploadPic('#uploadMessagePic', '#logoPicList');

	 //新增
	 $('#addUserBtn').on('click', function () {
            $('#userModalTitle').text('新增广告');
            $('#modalUserId').val('');
            $('#modalCode').val('').removeClass('error-border').siblings('div').addClass('hide');
            $('#modalName').val('').removeClass('error-border').siblings('div').addClass('hide');
            $('#modalBrand').val('').removeClass('error-border').siblings('div').addClass('hide');
            $('#modalStatus').val(1);
            $('#userManageModal').modal('show');
        });
	 
	 
	 
	 
  });
});

