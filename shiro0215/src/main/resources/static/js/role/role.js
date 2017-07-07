/**
 * Created by emi on 2017/3/2.
 */
myManagerApp.controller("roleContentCtrl", ['$scope', '$http', '$state', '$stateParams', '$cookieStore', 'checkAuthSvc', function($scope, $http, $state, $stateParams, $cookieStore, checkAuthSvc) {

    $scope.successMsg = $stateParams.successMsg;

    $scope.createAuth = checkAuthSvc.hasAuth("resource:create");
    $scope.retrieveAuth = checkAuthSvc.hasAuth("resource:retrieve");
    $scope.updateAuth = checkAuthSvc.hasAuth("resource:update");
    $scope.deleteAuth = checkAuthSvc.hasAuth("resource:delete");

    var fetchRoleList = function() {
        $http({
            url:"/api/role/list",
            method:"GET",
            headers: {"username": $cookieStore.get("username")}
        }).success(function(data) {
            if(data.success) {
                $scope.roleList = data.roleVoList;
            } else {
                $scope.errorMsg = "error data: " + JSON.stringify(data.errorMsg);
            }
        }).error(function() {
            $scope.errorMsg = "粗问题了,你看着办";
        });
    };

    fetchRoleList();


    $scope.disableRole = function() {
        $scope.alert("该功能暂不支持");
    };

}]);