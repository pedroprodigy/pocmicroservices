(function() {
    'use strict';

    angular
        .module('pocgatewayApp')
        .controller('DepartmentDMController', DepartmentDMController);

    DepartmentDMController.$inject = ['$scope', '$state', 'DepartmentDM'];

    function DepartmentDMController ($scope, $state, DepartmentDM) {
        var vm = this;
        vm.departmentDMS = [];
        vm.loadAll = function() {
            DepartmentDM.query(function(result) {
                vm.departmentDMS = result;
            });
        };

        vm.loadAll();
        
    }
})();
