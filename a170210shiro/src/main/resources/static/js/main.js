/**
 * Created by Administrator on 2016/12/5.
 */
requirejs(['common'], function () {
    requirejs(['jquery', 'bootstrap', 'template', 'base', './accordion.menu', 'addtabs'], function ($, bootstrap, template, base, accordion) {
        var adminUser = sessionStorage.getItem('adminUser');
        adminUser = $.parseJSON(adminUser);
        $('.user-name').text(adminUser.userName);
        var menuData = {
            menuList:[
                {title: '权限管理', icon: 'icon-cl-authority',
                    subList: [{subTitle: '系统用户管理', subPage: 'views/userManage.html'},
                        {subTitle: '角色管理', subPage: 'views/roleManage.html'},
						{subTitle: '权限管理', subPage: 'views/roleAuthority.html'},
                        {subTitle: '组织管理', subPage: 'views/organizeManage.html'},
                        {subTitle: '系统参数', subPage: 'views/systemParams.html'}]},
                {title: '基础数据', icon: 'icon-cl-base',
                    subList: [{subTitle: '广告管理', subPage: 'views/AdvertisingManage.html'},
						{subTitle: '消息管理', subPage: 'views/messageManage.html'},
                        {subTitle: '数据字典', subPage: 'views/dataDictionary.html'},
                        {subTitle: '车辆品牌管理', subPage: 'views/carBrandManage.html'},
                        {subTitle: '车辆类型管理', subPage: 'views/carTypeManage.html'},
                        {subTitle: '车辆型号管理', subPage: 'views/carModelManage.html'},
                        {subTitle: '省份管理', subPage: 'views/provinceManage.html'},
                        {subTitle: '城市管理', subPage: 'views/cityManage.html'},
                        {subTitle: '地区管理', subPage: 'views/districtManage.html'}]},
                {title: '客户管理', icon: 'icon-cl-client',
                    subList: [{subTitle: '车主管理', subPage: 'views/carOwnerList.html'},
                        {subTitle: '商家管理', subPage: 'views/sellerManageList.html'},
                        {subTitle: '代理商管理', subPage: 'views/agencyManage.html'}]},
                {title: '产品管理', icon: 'icon-cl-goods',
                    subList: [{subTitle: '保养产品管理', subPage: 'views/maintProductList.html'},
                        {subTitle: '美容产品管理', subPage: 'views/beautyProductList.html'},
                        {subTitle: '维修产品管理', subPage: 'views/repairProductList.html'}]},
                {title: '订单管理', icon: 'icon-cl-order',
                    subList: [{subTitle: '洗车订单管理', subPage: 'views/orderManageList.html?orderType=1'},
                        {subTitle: '美容订单管理', subPage: 'views/orderManageList.html?orderType=2'},
                        {subTitle: '保养订单管理', subPage: 'views/orderManageList.html?orderType=3'},
                        {subTitle: '快修订单管理', subPage: 'views/orderManageList.html?orderType=4'},
                        {subTitle: '故障订单管理', subPage: 'views/orderManageList.html?orderType=5'},
                        {subTitle: '事故订单管理', subPage: 'views/orderManageList.html?orderType=6'},
                        {subTitle: '支付管理', subPage: 'views/paymentManage.html'},
                        {subTitle: '退款管理', subPage: 'views/refundManage.html'},
                        {subTitle: '评价管理', subPage: 'views/evaluateManage.html'}]},
                {title: '优惠券管理', icon: 'icon-cl-coupon',
                    subList: [{subTitle: '优惠券', subPage: 'views/couponManage.html'}]},
                {title: '财务管理', icon: 'icon-cl-money',
                    subList: [{subTitle: '应收管理', subPage: 'views/receivableManage.html'},
                        {subTitle: '应付管理', subPage: 'views/payableManage.html'},
                        {subTitle: '结算管理', subPage: 'views/balanceManage.html'},
                        {subTitle: '收入管理', subPage: 'views/incomeManage.html'},
                        {subTitle: '支出管理', subPage: 'views/costManage.html'},
                        {subTitle: '报表统计', subPage: 'views/reportStatistics.html'}]},
                {title: '统计报表', icon: 'icon-cl-report',
                    subList: [{subTitle: '商家营业统计', subPage: 'views/default.html'},
                        {subTitle: '客户消费统计', subPage: 'views/default.html'},
                        {subTitle: '订单统计报表', subPage: 'views/default.html'},
                        {subTitle: '商家加盟统计', subPage: 'views/default.html'},
                        {subTitle: '客户月增加统计', subPage: 'views/default.html'},
                        {subTitle: '客户评价统计', subPage: 'views/default.html'}]}]};

        var menuHtml = template('sideMenu', menuData);
        $('#collapseOne').html(menuHtml);
		
        accordion.Accordion($('#accordion'), false);
		
        $('#tabs').addtabs({contextmenu:true});

        $('#changePwd').on('click', function () {
            var required = $('.required');
            required.val('');
            required.siblings('div').addClass('hide');
            required.removeClass('error-border');
            $('#changePwdModal').modal('show');
        });

        $('#changePwdBtn').on('click', function () {
            var oldPwd = $('#oldPwd'), newPwd = $('#newPwd'), confirmPwd = $('#confirmPwd');
            if (base.isStringNull(oldPwd.val()) || base.isStringNull(newPwd.val()) || base.isStringNull(confirmPwd.val())) {
                if (base.isStringNull(oldPwd.val()) && !oldPwd.hasClass('error-border')) {
                    oldPwd.siblings('div').text('原密码不能为空');
                    oldPwd.siblings('div').removeClass('hide');
                    oldPwd.addClass('error-border');
                }
                if (base.isStringNull(newPwd.val()) && !newPwd.hasClass('error-border')) {
                    newPwd.siblings('div').text('新密码不能为空');
                    newPwd.siblings('div').removeClass('hide');
                    newPwd.addClass('error-border');
                }
                if (base.isStringNull(confirmPwd.val()) && !confirmPwd.hasClass('error-border')) {
                    confirmPwd.siblings('div').text('确认密码不能为空');
                    confirmPwd.siblings('div').removeClass('hide');
                    confirmPwd.addClass('error-border');
                }
                return;
            } else if (newPwd.val() != confirmPwd.val()) {
                confirmPwd.siblings('div').text('两次输入的新密码不一致');
                confirmPwd.siblings('div').removeClass('hide');
                confirmPwd.addClass('error-border');
                return;
            } else {
                var reqData = {
                    id: adminUser.id,
                    oldPassword: oldPwd.val(),
                    newPassword: newPwd.val(),
                    confirmNewPassword: confirmPwd.val()
                }
                base.sendReq({
                    url: 'admin/changePassword',
                    data: reqData,
                    method: 'POST',
                    getResult: function (data) {
                        if (data.success) {
                            $('#changePwdModal').modal('hide');
                            base.showAlertDialog('密码修改成功');
                        } else {
                            base.showAlertDialog(data.errorMessage);
                        }
                    }
                });
            }

        });

        $('#loginOut').on('click', function () {
            $('#loginOutDialog').dialogBox({
                type: 'correct',  //three type:'normal'(default),'correct','error',
                width: 300,
                hasMask: true,
                hasClose: true,
                hasBtn: true,
                cancelValue: '取消',
                confirmValue: '确定',
                confirm: function () {
                    sessionStorage.setItem('adminUser', '');
                    window.location.href = '../alogin.html';
                },
                effect: 'sign',
                title: '提示',
                content: '是否退出登录？'});
        });

        $('.required').on('blur', function () {
            var val = $(this).val(), isConfirm = $(this).attr('id');
            if (base.isStringNull(val)) {
                var errorHint = $(this).attr('data-tip');
                $(this).siblings('div').text(errorHint);
                $(this).siblings('div').removeClass('hide');
                $(this).addClass('error-border');
            } else if ((isConfirm === 'confirmPwd') && ($(this).val() != $('#newPwd').val())) {
                $(this).siblings('div').text('两次输入的新密码不一致');
                $(this).siblings('div').removeClass('hide');
                $(this).addClass('error-border');
            } else {
                $(this).siblings('div').addClass('hide');
                $(this).removeClass('error-border');
            }
        });

        $('.required').on('focus', function () {
            $(this).siblings('div').addClass('hide');
            $(this).removeClass('error-border');
        });

    });
});
