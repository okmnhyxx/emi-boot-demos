/**
 * Created by Administrator on 2016/12/2.
 */
define(['jquery', 'webuploader', 'plupload', 'Qiniu'], function ($, WebUploader, plupload, Qiniu) {
    var base = {}, baseUrl = '/car-life-manager/';
    // 通用的发送请求
    base.sendReq = function (options) {
        $.ajax({
            url: baseUrl + options.url,
            type:options.type,
            dataType:options.dataType,
            method: options.method,
            data:options.data,
            headers: options.headers,
            error: options.error,
            success:options.getResult
        });
    };

    // 验证邮箱是否非空，格式是否正确
    base.checkMail = function (item, isNoEmpty, type) {
        var reg =  /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
        $(item).on('blur', function () {
            var isRight = true, mail = $(this).val();
            if (mail.length === 0 && isNoEmpty) {
                isRight = false;
            } else {
                if (!reg.test(mail)) {
                    isRight = false;
                }
            }
            if (!isRight) {
                if (type === 0) {
                    if (mail.length === 0 && isNoEmpty) {
                        $(this).siblings('div').text('邮箱不能为空');
                    } else {
                        $(this).siblings('div').text('邮箱格式不正确');
                    }
                    $(this).siblings('div').removeClass('hide');
                    $(this).addClass('error-border');
                } else {
                    $(this).css('color', '#f00');
                }
            }
        });

        $(item).on('focus', function () {
            if (!$(this).siblings('div').hasClass('hide')) {
                if (type === 0) {
                    $(this).siblings('div').addClass('hide');
                    $(this).removeClass('error-border');
                } else {
                    $(this).css('color', '#333');
                }

            }
        });

    };

    // 验证手机号是否非空，格式是否正确
    base.checkTel = function (item, isNoEmpty, type) {
        var isPhone1 = /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/;
        var isPhone2 = /^((0\d{2,3}\d{7,8})|(1[3584]\d{9}))$/;
        var isPhone3 = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
        var isMob=/^((\+?86)|(\(\+86\)))?(^1[3|4|5|7|8]\d{9})$/;
        $(item).on('blur', function () {
            var phone = $.trim($(this).val()), isRight = true;
            if (phone.length === 0 && isNoEmpty) {
                isRight = false;
            } else {
                if (!isMob.test(phone)) {
                    isRight = false;
                }
            }
            if (!isRight) {
                if (type === 0) {
                    if (phone.length === 0 && isNoEmpty) {
                        $(this).siblings('div').text('手机不能为空');
                    } else {
                        $(this).siblings('div').text('手机格式不正确');
                    }
                    $(this).siblings('div').removeClass('hide');
                    $(this).addClass('error-border');
                } else {
                    $(this).css('color', '#f00');
                }
            }
        });

        $(item).on('focus', function () {
            if (!$(this).siblings('div').hasClass('hide')) {
                if (type === 0) {
                    $(this).siblings('div').addClass('hide');
                    $(this).removeClass('error-border');
                } else {
                    $(this).css('color', '#333');
                }
            }
        });
    };

    // 验证非空
    base.checkNotEmpty = function (item) {
        var val = item.val();
        if (base.isStringNull(val)) {
            var errorHint = item.attr('data-tip');
            item.siblings('div').text(errorHint);
            item.siblings('div').removeClass('hide');
            item.addClass('error-border');
            return false;
        } else {
            item.siblings('div').addClass('hide');
            item.removeClass('error-border');
            return true;
        }
    };

    // 判断字符串是否为空
    base.isStringNull = function (str) {
        if (str == "" || str == "null" || str == null||str == undefined || str=='undefined') {
            return true;
        } else {
            return false;
        }
    };
    
    // 当字符串为null时，置为“”
    base.setStringNull = function (str) {
        if (str == null || str == 'null') {
            str = '';
        }
        return str;
    };

    // 表格全选或取消全选
    base.selectAll = function (id) {
      $('#'+id).on('click', function () {
          var temp = $(this).prop('checked');
          if (temp) {
              $(this).parent().parent().parent().siblings().find('.list-check').prop("checked", true);
          } else {
              $(this).parent().parent().parent().siblings().find('.list-check').prop("checked", false);
          }
      });
    };

    // 通用提示框
    base.showAlertDialog = function (content) {
        if ($('#alertDialog').length < 1) {
            $('body').append('<div id="alertDialog"></div>');
        }
        $('#alertDialog').dialogBox({
            type: 'correct',  //three type:'normal'(default),'correct','error',
            width: 300,
            hasMask: true,
            hasClose: true,
            hasBtn: true,
            confirmValue: '确定',
            confirm: function () {
            },
            effect: 'sign',
            title: '提示',
            content: content});
    };

    // 显示loading层
    base.showLoading = function (hint) {
        if (hint == '') {
            hint = '正在加载，请等待...';
        }
        var loadHtml = '<div id="mask" class="mask-layer"><div class="mask-content"></div><div class="mask-hint-cont"><div class="mask-hint"><div class="mask-img"></div><div class="mask-font">'+hint+'</div></div></div></div>';
        $('body').append(loadHtml);
    };

    // 隐藏loading层
    base.hideLoading = function () {
        $('#mask').remove();
    }

    // 图片上传——七牛SDK
    base.uploadQiniuPic = function (id, container, listCont, qiToken) {
        //引入Plupload 、qiniu.js后
        var uploader = Qiniu.uploader({
            runtimes: 'html5,flash,html4',
            browse_button: id,
            container: container,
            max_file_size: '10mb',
            get_new_uptoken: true,
            flash_swf_url: '../vendor/js/plupload/Moxie.swf',
            dragdrop: false,
            auto_start: true,
            multi_selection: false,
            //限制图片类型
            uptoken:qiToken,
            uptoken_url:'api/qiniu/token',
            domain: 'http://oiw94mloy.bkt.clouddn.com/',
            resize:true,
            filters : {
                mime_types: [
                    {title : "Image files", extensions : "jpg,gif,png"}
                ]
            },
            init: {
                'FilesAdded': function(up, files) {
                   // plupload.each(files, function(file) {
                        // 文件添加进队列后,处理相关的事情
                        // 上传前预览
                        // var image = new Image();
                        // var preloader = new mOxie.Image();
                        // preloader.onload = function() {
                        //     preloader.downsize( 100, 100 );
                        //     image.setAttribute( "src", preloader.getAsDataURL());
                        //     image.setAttribute( "class", "pic-thumbnail");
                        //     $('#'+listCont).append(image);
                        // };
                        // preloader.load( file.getSource());
                   // });
                },
                'BeforeUpload': function(up, file) {
                    // 每个文件上传前,处理相关的事情
                },
                'UploadProgress': function(up, file) {
                    // 每个文件上传时,处理相关的事情
                },
                'FileUploaded': function(up, file, info) {
                    var domain = up.getOption('domain');
                    var res = $.parseJSON(info);
                    var sourceLink = domain + res.key;
                    var imgHtml = '<img src="'+sourceLink+'" class="pic-thumbnail">';
                    $('#'+listCont).html(imgHtml);
                    // 每个文件上传成功后,处理相关的事情
                    // 其中 info 是文件上传成功后，服务端返回的json，形式如
                    // {
                    //    "hash": "Fh8xVqod2MQ1mocfI4S4KpRL6D98",
                    //    "key": "gogopher.jpg"
                    //  }
                    // 参考http://developer.qiniu.com/docs/v6/api/overview/up/response/simple-response.html

                    // var domain = up.getOption('domain');
                    // var res = parseJSON(info);
                    // var sourceLink = domain + res.key; 获取上传成功后的文件的Url
                },
                'Error': function(up, file, err, errTip) {
                    //上传出错时,处理相关的事情
                    if (file.code=='-600'){
                        base.showAlertDialog('上传头像的大小不能超过10mb！')
                    }
                    else if (file.code=='-601'){
                        base.showAlertDialog('上传头像的格式有误！')
                    }
                    else {
                        //showAlertDialog(1, 'qiniu Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
                        base.showAlertDialog(err);
                    }
                },
                'UploadComplete': function() {
                    //队列文件处理完毕后,处理相关的事情
                },
                'Key': function(up, file) {
                    // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
                    // 该配置必须要在 unique_names: false , save_key: false 时才生效
                    var timestamp = Date.parse(new Date());
                    var key = "data/car/web/uuid/"+file.name+'/'+timestamp;
                    // do something with key
                    return key;
                }
            }
        });
        return uploader;
    };

    // 图片上传
    base.uploadPic = function (id, container, qiToken) {
// 初始化Web Uploader
        var uploader = WebUploader.create({

            // 选完文件后，是否自动上传。
            auto: true,

            // swf文件路径
            swf: '../vendor/js/Uploader.swf',

            // 文件接收服务端。
            server: 'http://upload.qiniu.com/putb64/-1',
            formData: {
                token: qiToken
            },

            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: id,
            multiple: false,

            // 只允许选择图片文件。
            accept: {
                title: 'Images',
                extensions: 'jpg,jpeg,png',
                mimeTypes: 'image/jpg,image/jpeg,image/png'
            }
        });
// 当有文件添加进来的时候
        uploader.on( 'fileQueued', function( file ) {
            var $list = $(container);
            var $li = $(
                    '<div id="' + file.id + '" class="file-item thumbnail">' +
                    '<img>' +
                    '<div class="info">' + file.name + '</div>' +
                    '</div>'
                ),
                $img = $li.find('img');


            // $list为容器jQuery实例
            $list.append( $li );

            // 创建缩略图
            // 如果为非图片文件，可以不用调用此方法。
            // thumbnailWidth x thumbnailHeight 为 100 x 100
            uploader.makeThumb( file, function( error, src ) {
                if ( error ) {
                    $img.replaceWith('<span>不能预览</span>');
                    return;
                }

                $img.attr( 'src', src );
            }, 100, 100 );
            // 文件上传过程中创建进度条实时显示。
            uploader.on( 'uploadProgress', function( file, percentage ) {
                var $li = $( '#'+file.id ),
                    $percent = $li.find('.progress span');

                // 避免重复创建
                if ( !$percent.length ) {
                    $percent = $('<p class="progress"><span></span></p>')
                        .appendTo( $li )
                        .find('span');
                }

                $percent.css( 'width', percentage * 100 + '%' );
            });

// 文件上传成功，给item添加成功class, 用样式标记上传成功。
            uploader.on( 'uploadSuccess', function( file ) {
                $( '#'+file.id ).addClass('upload-state-done');
            });

// 文件上传失败，显示上传出错。
            uploader.on( 'uploadError', function( file ) {
                var $li = $( '#'+file.id ),
                    $error = $li.find('div.error');

                // 避免重复创建
                if ( !$error.length ) {
                    $error = $('<div class="error"></div>').appendTo( $li );
                }

                $error.text('上传失败');
            });

// 完成上传完了，成功或者失败，先删除进度条。
            uploader.on( 'uploadComplete', function( file ) {
                $( '#'+file.id ).find('.progress').remove();
            });
        });
        return uploader;
    };

    return base;

});