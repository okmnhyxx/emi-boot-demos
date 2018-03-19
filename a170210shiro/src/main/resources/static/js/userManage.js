/**
 * Created by Administrator on 2016/12/8.
 */
requirejs(['common'], function () {
    requirejs(['jquery', 'bootstrap', 'template', 'base', 'paging'], function ($, bootstrap, template, base, Paging) {
        var pageSize = 10, pageTotal = 10;
        // 加载列表
        function getUserList(pageNum, pageSize) {
            base.showLoading('');
            var userName = $('#userName').val(), trueName = $('#trueName').val(), phoneNum = $('#phoneNum').val(), status = $('#sysStatus').val();
            if (status == 0) {
                status = '';
            } else if (status == 1) {
                status = true;
            } else {
                status = false;
            }
            var reqData = {
                userName: userName,
                trueName: trueName,
                phone: phoneNum,
                enable: status,
                pageNum: pageNum,
                pageSize: pageSize
            };
            base.sendReq({
                url: 'admin/search',
                method: 'GET',
                data: reqData,
                getResult: function (data) {
                    base.hideLoading();
                    if (data.success) {
                        var userList = data.page;
                        var userHtml = template('userDatas', userList);
                        $('#userTableBody').html(userHtml);
                        pageTotal = userList.total;
                        var pageNum = userList.total/userList.size;
                        if (userList.total%userList.size != 0) {
                            pageNum += 1;
                        }
                        p.render({
                            count: userList.total,
                            pagecount: pageNum,
                            current: userList.pageNum,
                            pagesize: userList.pageSize
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
            getUserList(page, pageSize);
        }});
        getUserList(1, 10);

        // 搜索
        $('#searchBtn').on('click', function () {
            getUserList(1, pageSize);
        });

        // 重置
        $('#resetBtn').on('click', function () {
            $('#userName').val('');
            $('#trueName').val('');
            $('#phoneNum').val('');
            $('#sysStatus').val(0);
            getUserList(1, pageSize);
        });


        // 新增用户
        $('#addUserBtn').on('click', function () {
            $('#userModalTitle').text('新增用户');
            $('#modalUserId').val('');
            $('#modalUser').val('').removeClass('error-border').siblings('div').addClass('hide');
            $('#modalName').val('').removeClass('error-border').siblings('div').addClass('hide');
            $('#modalPhone').val('').removeClass('error-border').siblings('div').addClass('hide');
            $('#modalMail').val('').removeClass('error-border').siblings('div').addClass('hide');
            $('#modalRemark').val('');
            $('#modalStatus').val(1);
            $('#userManageModal').modal('show');
        });

        // 编辑用户
        $('body').on('click', '.edit-btn', function () {
            $('#userModalTitle').text('编辑用户');
            var data = [];
            $(this).parents('tr').children('td').each(function (i) {
                data[i] = $(this).text();
            });
            var userId = data[2], userName = data[3], trueName = data[4], phone = data[5], mail = data[6], remark = data[7], status = data[8];
            $('#modalUserId').val(userId);
            $('#modalUser').val(userName).removeClass('error-border').siblings('div').addClass('hide');
            $('#modalName').val(trueName).removeClass('error-border').siblings('div').addClass('hide');
            $('#modalPhone').val(phone).removeClass('error-border').siblings('div').addClass('hide');
            $('#modalMail').val(mail).removeClass('error-border').siblings('div').addClass('hide');
            $('#modalRemark').val(remark);
            if (status == '可用') {
                $('#modalStatus').val(0);
            } else {
                $('#modalStatus').val(1);
            }
            $('#userManageModal').modal('show');

        });


        // 删除用户
        $('#delUserBtn').on('click', function () {
            var user = $("input[name=user]:checked"), userArr = [];
            if (user.size() == 0) {
                alert('请先选中要删除的行');
                return;
            }
            user.each(function () {
                var delId = $(this).parent().siblings('.user-id').text();
                userArr.push(delId);
            });

            userArr = JSON.stringify(userArr);
		
            $('#userHintDialog').dialogBox({
                type: 'correct',  //three type:'normal'(default),'correct','error',
                width: 300,
                hasMask: true,
                hasClose: true,
                hasBtn: true,
                cancelValue: '取消',
                confirmValue: '确定',
                confirm: function () {
                    base.sendReq({
                        url: 'admin/delete',
                        data: userArr,
                        headers: {
                            'Content-Type': 'application/json;charset=UTF-8'
                        },
                        method: 'POST',
                        getResult: function (data) {
                            if (data.success) {
                                base.showAlertDialog('删除成功');
                                getUserList(1, pageSize);
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
        $('body').on('click', '.pwd-btn', function () {
            var id = $(this).parent().siblings('.user-id').text();
            var url = 'admin/resetPassword/' + id;
            base.showLoading('正在重置，请等待...');
            $('#userHintDialog').dialogBox({
                type: 'correct',  //three type:'normal'(default),'correct','error',
                width: 300,
                hasMask: true,
                hasClose: true,
                hasBtn: true,
                cancelValue: '取消',
                confirmValue: '确定',
                confirm: function () {
                    base.hideLoading();
                    base.sendReq({
                        url: url,
                        method: 'GET',
                        data: id,
                        getResult: function (data) {
                            if (data.success) {
                                base.showAlertDialog('重置密码成功');
                            } else {
                                base.showAlertDialog(data.errorMessage);
                            }
                        }
                    });
                },
                effect: 'sign',
                title: '提示',
                content: '是否重置该用户的密码？'});

        });

        // 全选或取消全选
        base.selectAll('selectAll');

        // 提交新增或编辑用户请求
        $('#sendAddBtn').on('click', function () {
            var modalUser = $('#modalUser'), modalName = $('#modalName'), modalPhone = $('#modalPhone'), modalMail = $('#modalMail'), isCorrect = true;
            var hasUser = base.checkNotEmpty(modalUser), hasName = base.checkNotEmpty(modalName),
                hasPhone = base.checkNotEmpty(modalPhone), hasMail = base.checkNotEmpty(modalMail);
            if (hasUser && hasName && hasPhone && hasMail) {
                var isMob=/^((\+?86)|(\(\+86\)))?(^1[3|4|5|7|8]\d{9})$/;
                var isEmail =  /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
                if (!isMob.test(modalPhone.val())) {
                    modalPhone.siblings('div').text('手机格式不正确');
                    modalPhone.siblings('div').removeClass('hide');
                    modalPhone.addClass('error-border');
                    isCorrect = false;
                }
                if (!isEmail.test(modalMail.val())) {
                    modalMail.siblings('div').text('邮箱格式不正确');
                    modalMail.siblings('div').removeClass('hide');
                    modalMail.addClass('error-border');
                    isCorrect = false;
                }
                if (isCorrect) {
                    var url = '', modalStatus = true;
                    if ($('#modalStatus').val() == 1) {
                        modalStatus = false;
                    }
                    var reqData = {
                        userName: modalUser.val(),
                        trueName: modalName.val(),
                        phone: modalPhone.val(),
                        email: modalMail.val(),
                        remark: $('#modalRemark').val(),
                        enable: modalStatus
                    };
                    if ($('#userModalTitle').text() == '新增用户') {
                        url = 'admin/add';
                    } else {
                        url = 'admin/update';
                        reqData.id = $('#modalUserId').val();
                    }
                    base.showLoading('正在保存，请等待...');
                    base.sendReq({
                        url: url,
                        method: 'POST',
                        data: reqData,
                        getResult: function (data) {
                            base.hideLoading();
                            if (data.success) {
                                $('#userManageModal').modal('hide');
                                base.showAlertDialog('用户信息保存成功');
                                getUserList(1, pageSize);
                            } else {
                                base.showAlertDialog(data.errorMessage);
                            }
                        }
                    });
                }
            }



        });

        // 验证
        base.checkTel('#modalPhone', true, 0);
        base.checkMail('#modalMail', true, 0);
        $('.required').on('blur', function () {
            var item = $(this);
            base.checkNotEmpty(item);
        });

        $('.required').on('focus', function () {
            $(this).siblings('div').addClass('hide');
            $(this).removeClass('error-border');
        });


    });
});