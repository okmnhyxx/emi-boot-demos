/**
 * Created by emi on 2017/3/6.
 */
myManagerApp.controller("userContentCtrl", ['$scope', '$http', '$state', '$stateParams', '$cookieStore', 'checkAuthSvc', function($scope, $http, $state, $stateParams, $cookieStore, checkAuthSvc) {

    $scope.successMsg = $stateParams.successMsg;

    $scope.createAuth = checkAuthSvc.hasAuth("user:create");
    $scope.retrieveAuth = checkAuthSvc.hasAuth("user:retrieve");
    $scope.updateAuth = checkAuthSvc.hasAuth("user:update");
    $scope.deleteAuth = checkAuthSvc.hasAuth("user:delete");


    var fetchUserList = function() {
        $http({
            url: "/api/user/list",
            method: "GET",
            headers: {"username": $cookieStore.get("username")},
            params: {
                username: $scope.username,
                locked: $scope.locked,
                pageSize: $scope.paginationMsgConf.itemsPerPage,
                currentPage: $scope.paginationMsgConf.currentPage - 1
            }
        }).success(function(data) {
            if(data.success) {
                $scope.userList = data.userVoList;
                $scope.paginationMsgConf.totalItems = data.pageVo.totalRows;
                console.log(" ------------- " + $scope.userList[0].username);
            } else {
                $scope.errorMsg = "error data: " + JSON.stringify(data.errorMsg);
            }
        }).error(function() {
            $scope.errorMsg = "粗问题了,你看着办";
        });
    };

    //fetchUserList();

    $scope.changeUserState = function(id, locked) {
        $http({
            url: "/api/user/" + id + "/lock",
            method: "POST",
            headers: {"username": $cookieStore.get("username")},
            params: {
                locked: locked
            }
        }).success(function(data) {
            if(data.success) {
                $state.go("mainManager.user",{successMsg:"修改成功"});
            } else {
                $scope.errorMsg = "error data: " + JSON.stringify(data.errorMsg);
            }
        }).error(function() {
            $scope.errorMsg = "粗问题了,你看着办";
        });

    };



    /***************************************************************
     当页码和页面记录数发生变化时监控后台查询
     如果把currentPage和itemsPerPage分开监控的话则会触发两次后台事件。
     ***************************************************************/
    $scope.$watch('paginationMsgConf.currentPage + paginationMsgConf.itemsPerPage', fetchUserList);



}]);






