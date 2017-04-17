'use strict';

var app = angular.module('rbstore');

app.factory('ConfigurationFactory', function ($resource, $cookieStore) {

    return $resource(endpoint + '/secure/api/configuration',
        {},
        {
            findAll: {
                method: 'GET',
                headers: {
                    'Authorization':  $cookieStore.get('token')
                }
            }
        }
    );
});