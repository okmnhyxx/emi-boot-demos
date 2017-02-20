requirejs(['common'], function () {
    requirejs(['jquery', 'bootstrap', 'template', 'base'], function ($, bootstrap, template, base) {
        var loc = location.href;
        var n1 = loc.length;//地址的总长度
        var n2 = loc.indexOf("=");//取得=号的位置
        var id = decodeURI(loc.substr(n2+1, n1-n2));//从=号后面的内容
        var state = '';
    });
});