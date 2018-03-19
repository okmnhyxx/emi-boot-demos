/**
 * Created by Administrator on 2016/12/12.
 */
requirejs(['common'], function () {
    requirejs(['jquery', 'bootstrap', 'template', 'Qiniu', 'base'], function ($, bootstrap, template, Qiniu, base) {
        base.showLoading('');
        var loc = location.href;
        var n1 = loc.length;//地址的总长度
        var n2 = loc.indexOf("=");//取得=号的位置
        var id = decodeURI(loc.substr(n2 + 1, n1 - n2));//从=号后面的内容
        var state = '', locateProvince = $('#locateProvince'), locateCity = $('#locateCity'), locateDistrict = $('#locateDistrict');
        // 上传图片对象
        var uplShopObj, unlLicObj;
        $(document).ready(function () {
            // var qiToken = base.getQiniuToken();
            base.sendReq({
                url: 'api/qiniu/token',
                method: 'GET',
                getResult: function (data) {
                    data = JSON.parse(data);
                    if (data.success) {
                        $('#upToken').val(data.token);
                        $('input[name="token"]').val(data.token);
                        uplShopObj = base.uploadQiniuPic('uploadShopPic', 'uploader-shop-container', 'shopPicList', data.token);
                        unlLicObj = base.uploadQiniuPic('uploadLicensePic', 'uploader-lice-container', 'licensePicList', data.token);
                    } else {
                        base.showAlertDialog('');
                    }
                }
            });
            
            // 加载商家详情数据
            base.sendReq({
                url: 'merchant/storeDetail/' + id,
                method: 'GET',
                getResult: function (data) {
                    base.hideLoading();
                    if (data.success) {
                        var sellerInfo = data.storeDetailVo;
                        $('#sellerName').val(sellerInfo.storeName);
                        $('#contact').val(sellerInfo.concat);
                        $('#phoneNum').val(sellerInfo.phone);
                        $('#detailAddress').val(sellerInfo.address);
                        $('#shopArea').val(sellerInfo.storeArea);
                        $('#employeeNum').val(sellerInfo.clerkQuantity);
                        $('#previousYears').val(sellerInfo.enterYear);
                        $('#hasCompute').val(sellerInfo.hadComputer.toString());
                        $('#isSkillful').val(sellerInfo.familiarComputer.toString());
                        $('#phoneSkill').val(sellerInfo.familiarSmartphone.toString());
                        $('#hasLicence').val(sellerInfo.hadBusinessLicense.toString());
                        $('#other').val(sellerInfo.introduce);
                        if (!base.isStringNull(sellerInfo.storeImg)) {
                            var shopImgHtml = '<img src="'+sellerInfo.storeImg+'" class="pic-thumbnail">';
                            $('#shopPicList').html(shopImgHtml);
                        }

                        if (sellerInfo.hadBusinessLicense.toString() == 'false') {
                            $('#licenceNum').attr('disabled', 'disabled');
                            $('#licensePicList').addClass('hide');
                            $('#uploadLicensePic').css('display', 'none');
                            $('#unUploadLicensePic').css('display', 'inline-block');
                        } else {
                            $('#licenceNum').val(sellerInfo.businessLicenseNo);
                            $('#uploadLicensePic').css('display', 'inline-block');
                            $('#unUploadLicensePic').css('display', 'none');
                            if (!base.isStringNull(sellerInfo.businessLicenseImage)) {
                                var liceImgHtml = '<img src="'+sellerInfo.businessLicenseImage+'" class="pic-thumbnail">';
                                $('#licensePicList').html(liceImgHtml);
                            }
                        }
                        // 商户状态：0 待审核  1 可用  2 不通过  3 禁用
                        state = parseInt(sellerInfo.state);
                        var stateStr = '', startBtn = $('#startBtn'), stopBtn = $('#stopBtn'), validBtn = $('#validBtn');
                        switch (state) {
                            case 0:
                                stateStr = '待审核';
                                startBtn.attr('disabled', 'disabled').addClass('gray-btn');
                                stopBtn.attr('disabled', 'disabled').addClass('gray-btn');
                                break;
                            case 1:
                                stateStr = '可用';
                                startBtn.attr('disabled', 'disabled').addClass('gray-btn');
                                validBtn.attr('disabled', 'disabled').addClass('gray-btn');
                                break;
                            case 2:
                                stateStr = '不通过';
                                startBtn.attr('disabled', 'disabled').addClass('gray-btn');
                                stopBtn.attr('disabled', 'disabled').addClass('gray-btn');
                                break;
                            case 3:
                                stateStr = '停用';
                                stopBtn.attr('disabled', 'disabled').addClass('gray-btn');
                                validBtn.attr('disabled', 'disabled').addClass('gray-btn');
                                break;
                        }

                        $('#sellerStatus').text(stateStr);
                        selectPCD(sellerInfo.provinceId, sellerInfo.cityId, sellerInfo.districtId);
                        var feedBack = parseFloat(sellerInfo.feedBackRate);
                        feedBack = feedBack * 100 + '%';
                        $('#feedBackRate').text(feedBack);
                        var totalStarLevel = sellerInfo.totalStarLevel;
                        var starCount = parseInt(totalStarLevel);
                        for (var s = 0; s < starCount; s++) {
                            $('#star' + s).css('width', '100%');
                        }
                        var over = totalStarLevel - starCount, empty = starCount + 1;
                        over = over * 100;
                        $('#star' + empty).css('width', over + '%');
                        if (empty < 5) {
                            for (var e = empty + 1; e < 6; e++) {
                                $('#star' + e).css('width', 0 + '%');
                            }
                        }
                        if (!base.isStringNull(sellerInfo.storeType)) {
                            var storeType = sellerInfo.storeType.split(',');
                            for (var i = 0; i < storeType.length; i++) {
                                if (storeType[i] == '洗车') {
                                    $('#carWashType').prop("checked", true);
                                } else if (storeType[i] == '美容') {
                                    $('#carBeautyType').prop("checked", true);
                                } else if (storeType[i] == '保养') {
                                    $('#carMaintenanceType').prop("checked", true);
                                } else if (storeType[i] == '维修') {
                                    $('#carRepairType').prop("checked", true);
                                }
                            }

                        }
                        if (!base.isStringNull(sellerInfo.cooperation)) {
                            var cooperation = sellerInfo.cooperation.split(',');
                            for (var j = 0; j < cooperation.length; j++) {
                                if (cooperation[j] == '洗车') {
                                    $('#carWash').prop("checked", true);
                                } else if (cooperation[j] == '美容') {
                                    $('#carBeauty').prop("checked", true);
                                } else if (cooperation[j] == '保养') {
                                    $('#carMaintenance').prop("checked", true);
                                } else if (cooperation[j] == '维修') {
                                    $('#carRepair').prop("checked", true);
                                }
                            }
                        }

                    } else {
                        base.showAlertDialog(data.errorMessage);
                    }
                }
            });
        });

        $('#hasLicence').on('change', function () {
            var hasLic = $(this).val(), licence = $('#licenceNum'), licencePic = $('#licensePicList');
            if (hasLic == 'true') {
                licence.removeAttr('disabled');
                licencePic.removeClass('hide');
                $('#uploadLicensePic').css('display', 'inline-block');
                $('#unUploadLicensePic').css('display', 'none');
            } else {
                licence.attr('disabled', 'disabled').val('');
                licencePic.addClass('hide');
                $('#uploadLicensePic').css('display', 'none');
                $('#unUploadLicensePic').css('display', 'inline-block');
            }
        });

        $('.t-required').on('focus', function () {
            $(this).siblings('.dark-tooltip').css('display', 'none');
        });
        $('input[type=checkbox]').on('focus', function () {
            $(this).siblings('.dark-tooltip').css('display', 'none');
        });

        $('#modalAccount').on('blur', function () {
            if (!base.isStringNull($(this).val())) {
                var isPass = $('#passRadio').prop('checked'), pwd = $('#modalPwd');
                if (isPass && base.isStringNull(pwd.val())) {
                    pwd.val($(this).val()).removeClass('error-border').siblings('div').addClass('hide');
                }
            }
        });

        // 保存
        $('#saveBtn').on('click', function () {
            var isRight = true;
            $('.t-required').each(function () {
                var val = $(this).val(), tip = $(this).attr('data-tip');
                tip = tip.split('||');
                if (base.isStringNull(val)) {
                    $(this).siblings('.dark-tooltip').css('display', 'block');
                    $(this).siblings('.dark-tooltip').children('.tooltip-info').text(tip[0]);
                    isRight = false;
                    return false;
                } else {
                    var id = $(this).attr('id');
                    if (id == 'phoneNum') {
                        var isPhone1 = /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/;
                        var isPhone2 = /^((0\d{2,3}\d{7,8})|(1[3584]\d{9}))$/;
                        var isPhone3 = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
                        var isMob = /^((\+?86)|(\(\+86\)))?(^1[3|4|5|7|8]\d{9})$/;
                        if (!(isMob.test(val) || isPhone1.test(val) || isPhone2.test(val) || isPhone3.test(val))) {
                            $(this).siblings('.dark-tooltip').css('display', 'block');
                            $(this).siblings('.dark-tooltip').children('.tooltip-info').text(tip[1]);
                            isRight = false;
                            return false;
                        }
                    }
                }
            });

            if (isRight) {
                var carWashType = $('#carWashType').prop('checked'), carBeautyType = $('#carBeautyType').prop('checked'), carMaintenanceType = $('#carMaintenanceType').prop('checked'),
                    carRepairType = $('#carRepairType').prop('checked'), carWash = $('#carWash').prop('checked'), carBeauty = $('#carBeauty').prop('checked'),
                    carMaintenance = $('#carMaintenance').prop('checked'), carRepair = $('#carRepair').prop('checked');
                if (!carWashType && !carBeautyType && !carMaintenanceType && !carRepairType) {
                    $('#typeTooltip').css('display', 'block');
                    return;
                }
                if (!carWash && !carBeauty && !carMaintenance && !carRepair) {
                    $('#cooperTooltip').css('display', 'block');
                    return;
                }
                base.showLoading('正在保存，请等待...');

                var storeName = $('#sellerName').val(), contact = $('#contact').val(), phone = $('#phoneNum').val(),
                    provinceId = $('#locateProvince').val(), cityId = $('#locateCity').val(), districtId = $('#locateDistrict').val(),
                    province = $('#locateProvince').find("option:selected").text(), city = $('#locateCity').find("option:selected").text(),
                    district = $('#locateDistrict').find("option:selected").text(), address = $('#detailAddress').val(),
                    shopArea = $('#shopArea').val(), employeeNum = $('#employeeNum').val(), previousYears = $('#previousYears').val(),
                    hasCompute = $('#hasCompute').val(), isSkillful = $('#isSkillful').val(), phoneSkill = $('#phoneSkill').val(),
                    hasLicence = $('#hasLicence').val(), licenceNum = $('#licenceNum').val(), other = $('#other').val(),
                    storeImg = $('#shopPicList').children('img').attr('src'), liceImg = $('#licensePicList').children('img').attr('src');

                if (base.isStringNull(storeImg)) {
                    storeImg = '';
                }

                if (base.isStringNull(liceImg) || base.isStringNull(licenceNum)) {
                    liceImg = '';
                }

                var storeType = '', cooperationStr = '', typeList = [], cooperList = [];
                typeList.push(carWashType);
                typeList.push(carBeautyType);
                typeList.push(carMaintenanceType);
                typeList.push(carRepairType);
                cooperList.push(carWash);
                cooperList.push(carBeauty);
                cooperList.push(carMaintenance);
                cooperList.push(carRepair);

                storeType = joinStr(typeList);
                cooperationStr = joinStr(cooperList);

                var reqData = {
                    id: id,
                    storeName: storeName,
                    concat: contact,
                    storeImg: storeImg,
                    phone: phone,
                    storeType: storeType,
                    address: address,
                    introduce: other,
                    provinceId: provinceId,
                    cityId: cityId,
                    districtId: districtId,
                    province: province,
                    city: city,
                    district: district,
                    cooperation: cooperationStr,
                    hadComputer: hasCompute,
                    storeArea: shopArea,
                    hadBusinessLicense: hasLicence,
                    businessLicenseNo: licenceNum,
                    businessLicenseImage: liceImg,
                    enterYear: previousYears,
                    clerkQuantity: employeeNum,
                    familiarComputer: isSkillful,
                    familiarSmartphone: phoneSkill

                };

                base.sendReq({
                    url: 'merchant/updateStore',
                    method: 'POST',
                    data: reqData,
                    getResult: function (data) {
                        base.hideLoading();
                        if (data.success) {
                            base.showAlertDialog('保存成功');
                        } else {
                            base.showAlertDialog(data.errorMessage);
                        }
                    }
                });
            }
        });

        // 启用  禁用
        $('body').on('click', '.active-btn', function () {
            var btnId = $(this).attr('id'), changeState = '', changeStateStr = '', dialogStr = '';
            if (btnId == 'startBtn') {
                changeState = 1;
                changeStateStr = '启用成功';
                dialogStr = '是否启用该商家？';
            } else if (btnId == 'stopBtn') {
                changeState = 3;
                changeStateStr = '停用成功';
                dialogStr = '是否停用该商家？';
            }

            $('#sellerEditDialogBox').dialogBox({
                type: 'correct',  //three type:'normal'(default),'correct','error',
                width: 300,
                hasMask: true,
                hasClose: true,
                hasBtn: true,
                cancelValue: '取消',
                confirmValue: '确定',
                confirm: function () {
                    base.sendReq({
                        url: 'merchant/changeState',
                        method: 'POST',
                        data: {
                            id: id,
                            state: changeState
                        },
                        getResult: function (data) {
                            if (data.success) {
                                var startBtn = $('#startBtn'), stopBtn = $('#stopBtn'), status = $('#sellerStatus');
                                if (changeState == 1) {
                                    stopBtn.removeAttr('disabled').removeClass('gray-btn');
                                    startBtn.attr('disabled', 'disabled').addClass('gray-btn');
                                    status.text('可用');
                                } else {
                                    startBtn.removeAttr('disabled').removeClass('gray-btn');
                                    stopBtn.attr('disabled', 'disabled').addClass('gray-btn');
                                    status.text('停用');
                                }
                                base.showAlertDialog(changeStateStr);
                            } else {
                                base.showAlertDialog(data.errorMessage);
                            }
                        }
                    });
                },
                effect: 'sign',
                title: '提示',
                content: dialogStr
            });


        });

        $('.required').on('blur', function () {
            var item = $(this);
            base.checkNotEmpty(item);
        });

        $('.required').on('focus', function () {
            $(this).siblings('div').addClass('hide');
            $(this).removeClass('error-border');
        });

        // 审核
        $('#validBtn').on('click', function () {
            $('#modalAccount').val('').removeClass('error-border').siblings('div').addClass('hide');
            $('#modalPwd').val('').removeClass('error-border').siblings('div').addClass('hide');
            $('#rejectReason').val('');

            $('#validModal').modal('show');
        });

        $('#sendValidBtn').on('click', function () {
            var pass = $('#passRadio').prop('checked'), validType = 0, reqData = {}, isSend = false, responseStr = '', dialogStr = '';
            if (pass) {
                validType = 1;
                var account = $('#modalAccount'), pwd = $('#modalPwd'),
                    hasAccount = base.checkNotEmpty(account), hasPwd = base.checkNotEmpty(pwd);
                if (hasAccount && hasPwd) {
                    isSend = true;
                    reqData = {
                        id: id,
                        state: 1,
                        userName: account.val(),
                        password: pwd.val()
                    };
                    responseStr = '商家审核通过';
                    dialogStr = '是否通过该商家的审核？';
                }
            } else {
                validType = 2;
                var reason = $('#rejectReason'), hasReason = base.checkNotEmpty(reason);
                if (hasReason) {
                    isSend = true;
                    reqData = {
                        id: id,
                        state: 2,
                        reason: reason.val()
                    }
                    responseStr = '商家审核不通过';
                    dialogStr = '是否拒绝该商家的审核？';
                }
            }
            if (isSend) {
                $('#sellerEditDialogBox').dialogBox({
                    type: 'correct',  //three type:'normal'(default),'correct','error',
                    width: 300,
                    hasMask: true,
                    hasClose: true,
                    hasBtn: true,
                    cancelValue: '取消',
                    confirmValue: '确定',
                    confirm: function () {
                        base.sendReq({
                            url: 'merchant/audit',
                            method: 'POST',
                            data: reqData,
                            getResult: function (data) {
                                $('#validModal').modal('hide');
                                if (data.success) {
                                    var validBtn = $('#validBtn'), status = $('#sellerStatus'), startBtn = $('#startBtn');
                                    if (validType == 1) {
                                        status.text('可用');
                                        validBtn.attr('disabled', 'disabled').addClass('gray-btn');
                                        startBtn.removeAttr('disabled').removeClass('gray-btn');
                                    } else {
                                        status.text('不通过');
                                    }
                                    base.showAlertDialog(responseStr);
                                } else {
                                    base.showAlertDialog(data.errorMessage);
                                }
                            }
                        });
                    },
                    effect: 'sign',
                    title: '提示',
                    content: dialogStr
                });

            }

        });

        // 重置密码
        $('#resetPwdBtn').on('click', function () {
            $('#sellerEditDialogBox').dialogBox({
                type: 'correct',  //three type:'normal'(default),'correct','error',
                width: 300,
                hasMask: true,
                hasClose: true,
                hasBtn: true,
                cancelValue: '取消',
                confirmValue: '确定',
                confirm: function () {
                    var reqData = {
                        id: id
                    };
                    base.sendReq({
                        url: 'merchant/resetPassword/' + id,
                        method: 'GET',
                        data: reqData,
                        getResult: function (data) {
                            if (data.success) {
                                var html = '<div>重置密码成功</div><div>密码为' + data.newPassword + '</div>';
                                base.showAlertDialog(html);
                            } else {
                                base.showAlertDialog(data.errorMessage);
                            }
                        }
                    });
                },
                effect: 'sign',
                title: '提示',
                content: '是否重置该商户密码？'
            });
        });

        $('input[name=validRadio]').on('click', function () {
            var pass = $('#passRadio').prop('checked'), reject = $('#rejectRadio').prop('checked'),
                passForm = $('#passForm'), rejectForm = $('#rejectForm');
            if (!pass) {
                passForm.addClass('hide');
                rejectForm.removeClass('hide');
            } else if (!reject) {
                rejectForm.addClass('hide');
                passForm.removeClass('hide');
            }

        });

        // 页面初始化，加载省市区
        function selectPCD(provinceId, cityId, districtId) {
            // 加载省
            base.sendReq({
                url: 'districtSelect/province',
                method: 'POST',
                data: '',
                getResult: function (data) {
                    if (data.success) {
                        var provinceHtml = template('provinceData', data);
                        locateProvince.html(provinceHtml).val(provinceId);
                    }
                }
            });
            if (!base.isStringNull(provinceId)) {
                base.sendReq({
                    url: 'districtSelect/city',
                    method: 'POST',
                    data: {
                        provinceId: provinceId
                    },
                    getResult: function (data) {
                        if (data.success) {
                            var cityHtml = template('cityData', data);
                            locateCity.html(cityHtml).val(cityId);
                        }
                    }
                });
            }
            if (!base.isStringNull(cityId)) {
                base.sendReq({
                    url: 'districtSelect/district',
                    method: 'POST',
                    data: {
                        cityId: cityId
                    },
                    getResult: function (data) {
                        if (data.success) {
                            var districtHtml = template('districtData', data);
                            locateDistrict.html(districtHtml).val(districtId);
                        }
                    }
                });
            }
        }

        // 加载市
        locateProvince.on('change', function () {
            var provinceId = $(this).val();
            if (base.isStringNull(provinceId)) {
                locateCity.html('<option value="">全部</option>').val('');
                locateDistrict.html('<option value="">全部</option>').val('');
            } else {
                base.sendReq({
                    url: 'districtSelect/city',
                    method: 'POST',
                    data: {
                        provinceId: provinceId
                    },
                    getResult: function (data) {
                        if (data.success) {
                            var cityHtml = template('cityData', data);
                            locateCity.html(cityHtml).val('');
                            locateDistrict.html('<option value="">全部</option>').val('');
                        }
                    }
                });
            }
        });

        // 加载区
        locateCity.on('change', function () {
            var cityId = $(this).val();
            if (base.isStringNull(cityId)) {
                locateDistrict.html('<option value="">全部</option>').val('');
            } else {
                base.sendReq({
                    url: 'districtSelect/district',
                    method: 'POST',
                    data: {
                        cityId: cityId
                    },
                    getResult: function (data) {
                        if (data.success) {
                            var districtHtml = template('districtData', data);
                            locateDistrict.html(districtHtml).val('');
                        }
                    }
                });
            }
        });

        // 店铺类型、合作项目字符串拼接
        function joinStr(val) {
            var tempStr = '', str = '';
            for (var i = 0; i < val.length; i++) {
                switch (i) {
                    case 0:
                        tempStr = '洗车';
                        break;
                    case 1:
                        tempStr = '美容';
                        break;
                    case 2:
                        tempStr = '保养';
                        break;
                    case 3:
                        tempStr = '维修';
                        break;
                }
                if (val[i] == 'true' || val[i] == true) {
                    if (str == '') {
                        str = tempStr;
                    } else {
                        str += ',' + tempStr;
                    }
                }
            }
            return str;
        }
    });
});