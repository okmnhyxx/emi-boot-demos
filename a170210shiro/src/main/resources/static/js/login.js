/**
 * Created by Administrator on 2016/12/6.
 */
requirejs(['common'], function () {
    requirejs(['jquery', 'base'], function ($, base) {
        var $account = $('#account'), $pwd = $('#pwd'), $login = $('#loginBtn');

        $login.on('click', function () {
            if (base.isStringNull($account.val())) {
                base.showAlertDialog('用户名不能为空');
                return false;
            } else if (base.isStringNull($pwd.val())) {
                base.showAlertDialog('密码不能为空');
                return false;
            } else {
                base.showLoading('正在登录，请等待...');
                var reqData = {
                    userName: $account.val(),
                    password: $pwd.val()
                };
                base.sendReq({
                    url: 'admin/login',
                    data: reqData,
                    method: 'POST',
                    getResult: function (data) {
                        base.hideLoading();
                        if (data.success) {
                            sessionStorage.setItem('adminUser', JSON.stringify(data.adminUser));
                            window.location.href = 'index.html';
                        } else {
                            base.showAlertDialog(data.errorMessage);
                        }
                    }
                });
            }
        });

        // 重置密码
        $('#resetPwd').on('click', function () {
            $('#resetPwdDialog').dialogBox({
                type: 'correct',  //three type:'normal'(default),'correct','error',
                width: 300,
                hasMask: true,
                hasClose: true,
                hasBtn: true,
                cancelValue: '取消',
                confirmValue: '确定',
                confirm: function () {
                    // base.sendReq({
                    //    url: '',
                    //    method: 'POST',
                    //    data: '',
                    //     getResult: function (data) {
                    //         if (data.success) {
                    //             base.showAlertDialog('重置密码请求发送成功');
                    //         } else {
                    //             base.showAlertDialog(data.errorMessage);
                    //         }
                    //     }
                    // });
                },
                effect: 'sign',
                title: '提示',
                content: '是否发送重置密码请求？'});
        });

    });
});