requirejs(['common'], function () {
    requirejs(['jquery', 'bootstrap', 'template', 'base'], function ($, bootstrap, template, base) {
        var loc = location.href;
        var n1 = loc.length;//地址的总长度
        var n2 = loc.indexOf("=");//取得=号的位置
        var id = decodeURI(loc.substr(n2+1, n1-n2));//从=号后面的内容
        var state = '';
		$(document).ready(function(){
			//上传图片	
			base.uploadPic('#uploadHeadPic','#headPicList');
		
		
		
		});
		 

		
		
		
		
				//更改状态
		$('body').on('click', '.sendBtn', function () {
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
                
            });
		
    });
    });