/**
 * Created by emi on 2017/2/24.
 */

myManagerApp.controller("resourceContentCtrl", ['$scope', '$http', '$state', '$stateParams', '$cookieStore', 'checkAuthSvc', function($scope, $http, $state, $stateParams, $cookieStore, checkAuthSvc) {

    //$("#tableList").treetable({ expandable: true }).treetable("expandNode", 0);
    var successMsg = '';

    if(null != $stateParams.successMsg && '' != $stateParams.successMsg) {
        successMsg = $stateParams.successMsg;
    }
    $scope.successMsg = successMsg;

    var fetchResourceList = function () {
        $http({
            url: "/api/resource/list",
            method: "GET",
            headers: {"username": $cookieStore.get("username")}
        }).success(function(data) {
            if (typeof data == "string") {
                data = $.parseJSON(data);
            }
            if(data.success){
                $scope.resourceList = data.resourceVoList;

                $scope.$broadcast('angular-ui-tree:collapse-all');
                $scope.collapseAll();
            } else {
                console.log("error data: " + JSON.stringify(data));

            }
        }).error (function (){
            $scope.errorMsg = "粗问题了,你看着办";
        });
    };
    fetchResourceList();

    $scope.treeOptions = {
        accept: function(sourceNodeScope, destNodesScope, destIndex) {
            return true;
        }
    };


    $scope.createAuth = checkAuthSvc.hasAuth("resource:create");
    $scope.retrieveAuth = checkAuthSvc.hasAuth("resource:retrieve");
    $scope.updateAuth = checkAuthSvc.hasAuth("resource:update");
    $scope.deleteAuth = checkAuthSvc.hasAuth("resource:delete");

    //$scope.createAuth = true;
    //$scope.retrieveAuth = true;
    //$scope.updateAuth = true;
    //$scope.deleteAuth = true;

    $scope.collapseAll = function () {
        $scope.$broadcast('angular-ui-tree:collapse-all');
    };

    $scope.expandAll = function () {
        $scope.$broadcast('angular-ui-tree:expand-all');
    };

    $scope.updateResource = function(id) {
        console.log(" updateResource " + id);
        $state.go('mainManager.resourceModify',{"id": id});
    };
    $scope.createResource = function(resource) {
        if (null == resource) {
            $state.go('mainManager.resourceAddSub',
                {parentId: 0, parentViewType: 0, parentDesc:"/", parentStateUrl:"/", parentName:"/"});
        }

        $state.go('mainManager.resourceAddSub',
            {parentId: resource.id, parentViewType: resource.viewType, parentDesc:resource.permissionName, parentStateUrl:resource.stateUrl, parentName:resource.permissionCode});
    };
    $scope.deleteResource = function(resourceId) {

    };




}]);