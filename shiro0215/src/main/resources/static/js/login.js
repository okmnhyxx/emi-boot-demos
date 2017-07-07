/**
 * Created by emi on 2017/2/15.
 */
myManagerApp.controller('loginCtrl', ['$scope', '$state', '$http', '$cookieStore', 'howMenuGoSvc', function($scope, $state, $http, $cookieStore, howMenuGoSvc) {

    $scope.username = "admin";
    $scope.password = "123456";
    $scope.submit = function() {
        $http({
            url: "/login",
            method: "POST",
            params: {
                username: $scope.username,
                password:$scope.password
            }
        }).success(function(data) {
            if (typeof data == "string") {
                data = $.parseJSON(data);
            }
            if(data.success){
                var menuList = data.menuVoList;

                $scope.userVo = data.userVo;
                $cookieStore.put("username", data.userVo.username);
                sessionStorage.setItem("menuList", JSON.stringify(menuList));
                sessionStorage.setItem("permissionList", JSON.stringify(data.permissionList));

                howMenuGoSvc.howMenuGo(menuList, $state);

            } else {
                console.log("error data: " + JSON.stringify(data));
            }
        }).error (function (){
            $scope.alert("粗问题了,你看着办")
        });
    }


}]);


