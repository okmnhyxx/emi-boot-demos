/**
 * Created by emi on 2017/2/15.
 */
angular.module('manager.main.service', [])
    .controller('managerHeadCtrl', ['$scope', '$cookieStore', function ($scope, $cookieStore) {

    $scope.username = $cookieStore.get('username');
    //todo: $scope.$on('ngRepeatFinished'




}]).controller('menuCtrl', ['$scope', function($scope) {

    $scope.menuList = $.parseJSON(sessionStorage.getItem('menuList'));
    $scope.menuList2 = sessionStorage.getItem('menuList');


}]).controller("managerContentCtrl", ['$scope', function($scope) {
    //配置分页基本参数
    $scope.paginationMsgConf = {
        currentPage: 1,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 50, 100]
    };
}]);

