/**
 * Created by emi on 2017/2/15.
 */
var myManagerApp = angular.module('myManagerApp',[
    'ui.tree',
    'ui.router',
    'ngResource',
    'ngCookies',
    'tm.pagination',
    'manager.main.service',
    'common.svc'
]);

myManagerApp.config(['$stateProvider', '$locationProvider', '$urlRouterProvider', '$httpProvider', 'howMenuGoSvcProvider',
    function ($stateProvider, $locationProvider, $urlRouterProvider, $httpProvider, howMenuGoSvcProvider) {

        // Set the following to true to enable the HTML5 Mode
        // You may have to set <base> tag in index and a routing configuration in your server
        $locationProvider.html5Mode(false);
        if (!$httpProvider.defaults.headers.get) {
            $httpProvider.defaults.headers.get = {};
        }

        $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
        $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
        //$httpProvider.interceptors.push('timeoutHttpIntercept');


        var menuList = $.parseJSON(sessionStorage.getItem('menuList'));
        $urlRouterProvider.otherwise(howMenuGoSvcProvider.$get().menuUrl_(menuList));
        console.log(howMenuGoSvcProvider.$get().testIt + " --------------------- ");

        //var otherwiseUrl = '/login';
        //$urlRouterProvider.otherwise(otherwiseUrl);

        $stateProvider
        .state('doLogin', {
            url: '/login',
            templateUrl: 'alogin.html'
        })
        .state('mainManager', {
            url: '/manager',
            templateUrl: 'manager-main.html'
        })
        .state('mainManager.default', {
            url: '/manager/default',
            templateUrl: '/view/default.html'
        })
        .state('mainManager.resource', {
            url: '/resource',
            templateUrl: '/view/resource.html',
            params:{successMsg: null},
            controller:'resourceContentCtrl'
        })
        .state('mainManager.resourceModify', {
            url:'/resource/:id',
            templateUrl: '/view/resource-modify.html'
            //params:{"resourceId": null},
            //controller:"resourceDetailCtrl"
        })
        .state('mainManager.resourceAddSub', {
            url:'/resource/:parentId/add/node',
            templateUrl: '/view/resource-add-sub.html',
            params:{parentViewType: -1, parentDesc: null, parentStateUrl:null, parentName:null}
        })
        .state('mainManager.role', {
            url: '/role',
            templateUrl: '/view/role.html',
            params:{successMsg:null}
        })
        .state('mainManager.roleCreate', {
            url: '/role/create',
            templateUrl: '/view/role-create-modify.html'
        })
        .state('mainManager.roleModify', {
            url: '/role/:id/modify',
            templateUrl: '/view/role-create-modify.html'
        })
        .state('mainManager.user', {
            url: '/user',
            templateUrl: '/view/user.html',
            params:{successMsg:null}
        })
        .state('mainManager.userModify', {
            url: '/user/:id/modify',
            templateUrl: '/view/user-create-modify.html'
        })
        .state('mainManager.organization', {
            url: '/organization',
            templateUrl: '/view/organization.html'
        })
        ;


        //angular.module('myManagerApp').factory('timeoutHttpIntercept', function ($rootScope, $cookieStore, $q) {
        //    return {
        //        'request': function (config) {
        //            // config.timeout = 3000;
        //            config.headers.username = $cookieStore.get("username");
        //            return config;
        //        },
        //        responseError: function (err) {
        //            $('#mask').css('display', 'none');
        //            if (-1 === err.status) {
        //                $rootScope.alert("远程服务器无响应");
        //                // 远程服务器无响应
        //            } else if (500 === err.status) {
        //                $rootScope.alert("服务器错误");
        //                // 处理各类自定义错误
        //            } else if (501 === err.status) {
        //                // ...
        //            } else if (400 === err.status) {
        //                $rootScope.alert("请求参数异常");
        //                // ...
        //            }
        //            /* else {
        //             $('#mask').css('display', 'none');
        //             // alert('请求超时');
        //             }*/
        //            return $q.reject(err);
        //        }
        //    }
        //});

    }]);

