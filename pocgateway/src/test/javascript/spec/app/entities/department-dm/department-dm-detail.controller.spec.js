'use strict';

describe('Controller Tests', function() {

    describe('DepartmentDM Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDepartmentDM;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDepartmentDM = jasmine.createSpy('MockDepartmentDM');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'DepartmentDM': MockDepartmentDM
            };
            createController = function() {
                $injector.get('$controller')("DepartmentDMDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pocgatewayApp:departmentDMUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
