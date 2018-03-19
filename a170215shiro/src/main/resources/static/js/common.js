/**
 * Created by emi on 2017/2/15.
 *
 *
 * .config(function($provide, $state) {

    $provide
 */

//myManagerApp
angular.module('common.svc', [])
    .provider('howMenuGoSvc', function() {

        this.$get = function() {
            return {
                howMenuGo : function(menuList, $state) {
                    if (menuList.length > 0) {
                        //var firstItemVoUrl;
                        //var itemVo = menuList[0].menuVoList;
                        //if (itemVo.length > 0) {
                        //    firstItemVoUrl = itemVo[0].stateUrl;
                            //itemVo = itemVo[0].menuVoList;
                            //if (itemVo.length > 0) {
                            //    itemVo = itemVo[0].menuVoList;
                            //    firstItemVoUrl = itemVo[0].stateUrl;
                            //} else {
                            //    firstItemVoUrl = menuList[0].stateUrl;
                            //}
                        //} else {
                        //    firstItemVoUrl = menuList[0].stateUrl;
                        //}
                        $state.go(menuList[0].stateUrl);//todo:
                    } else {
                        $state.go('mainManager.default');
                    }
                    console.log(" --------------------------- ");
                },
                menuUrl_ : function(menuList) {
                    //if (menuList.length > 0) {
                    //    var menuUrl;
                    //    var itemVo = menuList[0].itemVoList;
                    //    if (null == itemVo || 0 == itemVo.length) {
                    //        menuUrl = menuList[0].mainItemVo.menuUrl;
                    //    } else {
                    //        menuUrl = itemVo[0].menuUrl;
                    //    }
                    //    return menuUrl;
                    //}
                    return '/login';
                },
                testIt: " --------------------------------------- "

            };
        };
    })
    .provider('checkAuthSvc', function() {
        this.$get = function() {
            return {
                hasAuth : function(requireAuth) {
                    var permissionList = $.parseJSON(sessionStorage.getItem("permissionList"));
                    var allPermission = requireAuth.substring(0, requireAuth.indexOf(":") + 1) + "*";
                    for(var i = 0; i < permissionList.length; i ++) {
                        if(requireAuth == permissionList[i] || permissionList[i] == allPermission) {
                            console.log("true");
                            return true;
                        }
                    }
                    console.log("false");
                    return false;
                }
            }
        }
    });






