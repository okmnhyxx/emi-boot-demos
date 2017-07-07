myManagerApp.controller("resourceSubCtrl", ['$scope', '$http', '$state', '$stateParams', '$cookieStore', 'checkAuthSvc', function($scope, $http, $state, $stateParams, $cookieStore, checkAuthSvc) {

    $scope.createAuth = checkAuthSvc.hasAuth("resource:create");

    $scope.parentId = $stateParams.parentId;
    $scope.parentViewType = $stateParams.parentViewType;
    $scope.parentDesc = $stateParams.parentDesc;
    $scope.parentStateUrl = $stateParams.parentStateUrl;
    $scope.parentName = $stateParams.parentName;

    if($scope.parentViewType == -1) {//说明没获取到，
        $scope.errorMsg = "请从资源管理重新进入";
        $scope.createAuth = false;
    }


    if ($scope.parentViewType == 1) {
        $scope.viewTypeData = [{key:1,value:"空菜单"},{key:2,value:"菜单"}];//{key:-1,value:"-----请选择-----"},
        $scope.notBtnNode = true;
    } else if ($scope.parentViewType == 1) {
        $scope.viewTypeData = [{key:2,value:"菜单"}];
        $scope.notBtnNode = false;
    } else if ($scope.parentViewType == 2) {
        $scope.viewTypeData = [{key:3,value:"按钮"}];
        $scope.notBtnNode = false;
    }
    $scope.viewType = $scope.viewTypeData[0];
    console.log(JSON.stringify($scope.viewTypeData));


    $scope.addSubNode = function() {
        $http({
            url:"/api/resource/" + $scope.parentId + "/sub",
            method:"POST",
            headers:{"username": $cookieStore.get("username")},
            params:{
                viewType: $scope.viewType,
                stateUrl: $scope.stateUrl,
                templateUrl: $scope.templateUrl,
                permissionCode: $scope.permissionCode,
                permissionName: $scope.permissionName
            }
        }).success(function(data) {
            if (data.success) {
                $state.go("mainManager.resource",{successMsg:"添加成功"});
            } else {
                console.log("error data: " + JSON.stringify(data));
                $scope.errorMsg = "添加失败， for：" + data.errorMsg;
            }
        }).error(function() {
            $scope.errorMsg = "粗问题了,你看着办";
        });
    };

    $scope.viewTypeChange = function() {
        //$scope.notBtnNode = $scope.viewType != 3;
    };

}]);