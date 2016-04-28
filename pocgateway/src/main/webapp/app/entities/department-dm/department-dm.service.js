(function() {
    'use strict';
    angular
        .module('pocgatewayApp')
        .factory('DepartmentDM', DepartmentDM);

    DepartmentDM.$inject = ['$resource'];

    function DepartmentDM ($resource) {
        var resourceUrl =  'pocmicro/' + 'api/department-dms/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
