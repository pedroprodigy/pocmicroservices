(function() {
    'use strict';

    angular
        .module('pocgatewayApp')
        .controller('DepartmentDMDetailController', DepartmentDMDetailController);

    DepartmentDMDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'DepartmentDM'];

    function DepartmentDMDetailController($scope, $rootScope, $stateParams, entity, DepartmentDM) {
        var vm = this;
        vm.departmentDM = entity;
        
        var unsubscribe = $rootScope.$on('pocgatewayApp:departmentDMUpdate', function(event, result) {
            vm.departmentDM = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
