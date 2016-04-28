(function() {
    'use strict';

    angular
        .module('pocgatewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('department-dm', {
            parent: 'entity',
            url: '/department-dm',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pocgatewayApp.departmentDM.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/department-dm/department-dms.html',
                    controller: 'DepartmentDMController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('departmentDM');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('department-dm-detail', {
            parent: 'entity',
            url: '/department-dm/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pocgatewayApp.departmentDM.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/department-dm/department-dm-detail.html',
                    controller: 'DepartmentDMDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('departmentDM');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DepartmentDM', function($stateParams, DepartmentDM) {
                    return DepartmentDM.get({id : $stateParams.id});
                }]
            }
        })
        .state('department-dm.new', {
            parent: 'department-dm',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/department-dm/department-dm-dialog.html',
                    controller: 'DepartmentDMDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                designation: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('department-dm', null, { reload: true });
                }, function() {
                    $state.go('department-dm');
                });
            }]
        })
        .state('department-dm.edit', {
            parent: 'department-dm',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/department-dm/department-dm-dialog.html',
                    controller: 'DepartmentDMDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DepartmentDM', function(DepartmentDM) {
                            return DepartmentDM.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('department-dm', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('department-dm.delete', {
            parent: 'department-dm',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/department-dm/department-dm-delete-dialog.html',
                    controller: 'DepartmentDMDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DepartmentDM', function(DepartmentDM) {
                            return DepartmentDM.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('department-dm', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
