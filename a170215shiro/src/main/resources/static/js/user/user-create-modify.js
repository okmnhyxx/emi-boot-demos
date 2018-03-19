/**
 * Created by emi on 2017/3/7.
 */
myManagerApp.controller("userCreateModifyCtrl", ['$scope', '$http', '$state', '$stateParams', '$cookieStore', 'checkAuthSvc', function($scope, $http, $state, $stateParams, $cookieStore, checkAuthSvc) {

    $scope.userId = $stateParams.id;

    $scope.createAuth = checkAuthSvc.hasAuth("role:create");
    $scope.updateAuth = checkAuthSvc.hasAuth("role:update");

    var onOrganizationCheck = function(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeOrganization"),
            nodes = zTree.getSelectedNodes(),
            id = "",
            name = "";
        nodes.sort(function compare(a,b){return a.id-b.id;});
        for (var i=0, l=nodes.length; i<l; i++) {
            id += nodes[i].id + ",";
            name += nodes[i].name + ",";
        }
        if (id.length > 0 ) id = id.substring(0, id.length-1);
        if (name.length > 0 ) name = name.substring(0, name.length-1);
        $("#organizationIds").val(id);
        $("#organizationNames").val(name);
        hideMenu();
    };

    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onCheck: onOrganizationCheck()
        }
    };

    var zNodes = null;
    $http({
        url: "/api/user/" + $scope.userId + "/detail",
        method: "GET",
        headers:{"username": $cookieStore.get("username")}
    }).success(function(data) {
        if (data.success) {
            zNodes = data.organizationVoList;
            $.fn.zTree.init($("#treeOrganization"), setting, zNodes);
            $scope.username = data.userVo.roleName;
            $scope.roleList = data.roleVoList;
            onOrganizationCheck();

        } else {
            console.log("error data: " + JSON.stringify(data));
            $scope.errorMsg = "初始资源失败， for：" + data.errorMsg;
        }
    }).error(function() {
        $scope.errorMsg = "粗问题了,你看着办";
    });

    $scope.selectOrganization = function () {
        showMenu();
    }


    function showMenu() {
        var cityObj = $("#organizationNames");
        var cityOffset = $("#organizationNames").offset();
        $("#organizationContentId").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

        $("body").bind("mousedown", onBodyDown);
    }
    function hideMenu() {
        $("#organizationContentId").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }
    function onBodyDown(event) {
        if (!(event.target.id == "selectBtnId" || event.target.id == "organizationContentId" || $(event.target).parents("#organizationContentId").length>0)) {
            hideMenu();
        }
    }




}]);