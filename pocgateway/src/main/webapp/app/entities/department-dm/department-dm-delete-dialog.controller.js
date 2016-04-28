(function() {
    'use strict';

    angular
        .module('pocgatewayApp')
        .controller('DepartmentDMDeleteController',DepartmentDMDeleteController);

    DepartmentDMDeleteController.$inject = ['$uibModalInstance', 'entity', 'DepartmentDM'];

    function DepartmentDMDeleteController($uibModalInstance, entity, DepartmentDM) {
        var vm = this;
        vm.departmentDM = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            DepartmentDM.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
