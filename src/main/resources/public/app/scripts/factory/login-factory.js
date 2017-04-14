'use strict';

var app = angular.module('rbstore');

app.factory('LoginFactory', function ($resource) {

    return $resource(endpoint + '/api/login',
        {},

        {
            login: {
                method: 'POST'
            }
        }
    );
});