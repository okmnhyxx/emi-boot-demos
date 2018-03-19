/**
 * Created by emi on 2017/2/24.
 */

myManagerApp.controller("roleCreateModifyCtrl", ['$scope', '$http', '$state', '$stateParams', '$cookieStore', 'checkAuthSvc', function($scope, $http, $state, $stateParams, $cookieStore, checkAuthSvc) {

    $scope.roleId = $stateParams.id;

    $scope.createAuth = checkAuthSvc.hasAuth("role:create");
    $scope.updateAuth = checkAuthSvc.hasAuth("role:update");

    var zNodes = null;
    var holdResources = function() {
        $http({
            url: "/api/role/" + $scope.roleId + "/detail",
            method: "GET",
            headers:{"username": $cookieStore.get("username")}
        }).success(function(data) {
            if (data.success) {
                zNodes = data.resourceVoList;
                $.fn.zTree.init($("#treeAuth"), setting, zNodes);
                $scope.roleName = data.roleVo.roleName;
                $scope.roleDesc = data.roleVo.roleDesc;
                onResourceCheck();

            } else {
                console.log("error data: " + JSON.stringify(data));
                $scope.errorMsg = "初始资源失败， for：" + data.errorMsg;
            }
        }).error(function() {
            $scope.errorMsg = "粗问题了,你看着办";
        });
    };

    var onResourceCheck = function(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeAuth"),
            nodes = zTree.getCheckedNodes(true),
            id = "",
            name = "";
        nodes.sort(function compare(a,b){return a.id-b.id;});
        for (var i=0, l=nodes.length; i<l; i++) {
            id += nodes[i].id + ";";
            name += nodes[i].name + ";  ";
        }
        if (id.length > 0 ) id = id.substring(0, id.length-1);
        if (name.length > 0 ) name = name.substring(0, name.length-1);
        $("#resourceIds").val(id);
        $("#resourceNames").val(name);
    };
    var setting = {
        check: {
            enable: true ,
            chkboxType: { "Y": "", "N": "" }
        },
        view: {
            dblClickExpand: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onCheck: onResourceCheck
        }
    };

    holdResources();


    function hideMenu() {
        $("#resourceContentId").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }
    function onBodyDown(event) {
        if (!(event.target.id == "showResourceId" || event.target.id == "resourceContentId" || $(event.target).parents("#resourceContentId").length>0)) {
            hideMenu();
        }
    }


    $scope.showResource = function() {
        var resourceNamesInput = $("#resourceNames");
        var resourceNamesInputOffset = resourceNamesInput.offset();
        $("#resourceContentId").css({left:resourceNamesInputOffset.left + "px", top:resourceNamesInputOffset.top + resourceNamesInput.outerHeight + "px"}).slideDown("fast");

        $("body").bind("mousedown", onBodyDown);
    };

    $scope.submitRole = function() {
        console.log(" ----------- " + $("#resourceIds").val());
        $http({
            url:"/api/role/" + this.roleId + "/modify",
            method:"POST",
            headers: {"username": $cookieStore.get("username")},
            params:{
                roleName: $scope.roleName,
                roleDesc: $scope.roleDesc,
                resourceIds: $("#resourceIds").val()
            }
        }).success(function(data) {
            if(data.success) {
                $state.go("mainManager.role",{successMsg:"修改成功"});
            } else {
                $scope.errorMsg = "修改失败， for：" + data.errorMsg;
            }
        }).error(function() {
            $scope.errorMsg = "粗问题了,你看着办";
        });
    }

}]);