/**
 * Created by emi on 2017/2/24.
 */

myManagerApp.controller("resourceDetailCtrl", ['$scope', '$http', '$state', '$stateParams', '$cookieStore', 'checkAuthSvc', function($scope, $http, $state, $stateParams, $cookieStore, checkAuthSvc) {

    $scope.id = $stateParams.id;

    var resourceDetailInfo = function() {
        $http({
            url: "/api/resource/" + $scope.id + "/detail",
            method: "GET",
            headers:{"username": $cookieStore.get("username")}
        }).success(function(data) {

            if(data.success){
                $scope.resource = data.resourceVo;
            } else {
                console.log("error data: " + JSON.stringify(data));
                $scope.errorMsg = "获取失败， for：" + data.errorMsg;
            }
        }).error(function() {
            $scope.errorMsg = "粗问题了,你看着办";
        });
    };

    $scope.viewTypeData = [
        {key:1,value:"空菜单"},
        {key:2,value:"菜单"},
        {key:3,value:"按钮"}
    ];

    $scope.updateAuth = checkAuthSvc.hasAuth("resource:update");
    resourceDetailInfo();

    $scope.updateResource = function() {
        if ($scope.resource.viewType <= 0) {
            $scope.errorMsg = "类型不能为空";
            return;
        }

        $http({
            url: "/api/resource/" + $scope.id + "/modify",
            method: "POST",
            params:{
                viewType: $scope.resource.viewType,
                stateUrl: $scope.resource.stateUrl,
                templateUrl: $scope.resource.templateUrl,
                permissionCode: $scope.resource.permissionCode,
                permissionName: $scope.resource.permissionName
            },
            headers:{"username": $cookieStore.get("username")}
        }).success(function(data) {
            if (data.success) {

                $state.go("mainManager.resource",{successMsg:"修改成功"});

            } else {
                console.log("error data: " + JSON.stringify(data));
                $scope.errorMsg = "修改失败， for：" + data.errorMsg;
            }
        }).error(function() {
            $scope.errorMsg = "粗问题了,你看着办";
        });

    };

}]);