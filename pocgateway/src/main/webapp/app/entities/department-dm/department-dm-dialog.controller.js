(function() {
    'use strict';

    angular
        .module('pocgatewayApp')
        .controller('DepartmentDMDialogController', DepartmentDMDialogController);

    DepartmentDMDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DepartmentDM'];

    function DepartmentDMDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DepartmentDM) {
        var vm = this;
        vm.departmentDM = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('pocgatewayApp:departmentDMUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.departmentDM.id !== null) {
                DepartmentDM.update(vm.departmentDM, onSaveSuccess, onSaveError);
            } else {
                DepartmentDM.save(vm.departmentDM, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
