/**
 * Created by Administrator on 2016/12/6.
 */
requirejs.config({
    paths: {
        jquery: '../vendor/js/jquery.min',
        bootstrap: '../vendor/js/bootstrap.min',
        addtabs: '../vendor/js/bootstrap.addtabs',
        template: '../vendor/js/template',
        paging: '../vendor/js/paging',
        query: '../vendor/js/query',
        dialogBox: '../vendor/js/jquery.dialogBox',
        webuploader: '../vendor/js/webuploader',
        datetimepicker: '../vendor/js/bootstrap-datetimepicker',
        swipebox: '../vendor/js/jquery.swipebox',
        treemenu: '../vendor/js/jquery.treemenu',
        plupload: '../vendor/js/plupload/plupload.dev',
        moxie: '../vendor/js/plupload/moxie',
        Qiniu: '../vendor/js/qiniu',
        base: 'base'
    },
    shim: {
        'jquery': {
            exports: 'jquery'
        },
        bootstrap: {
            deps: ['jquery']
        },
        'addtabs': {
            deps: ['jquery'],
            exports: 'jQuery.fn.addtabs'
        },
        'dialogBox': {
            deps: ['jquery'],
            exports: 'jQuery.fn.dialogBox'
        },
        'swipebox': {
            deps: ['jquery'],
            exports: 'jQuery.fn.swipebox'
        },
        'treemenu': {
            deps: ['jquery'],
            exports: 'jQuery.fn.treemenu'
        },
        'moxie': {
            deps: ['jquery']
        },
        'plupload': {
            deps: ['moxie'],
            exports: 'plupload'
        },
        'Qiniu': {
            deps: ['plupload', 'moxie'],
            exports: 'Qiniu'
        },
        'base': {
            deps: ['dialogBox']
        }
    }
});