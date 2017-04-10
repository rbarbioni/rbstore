'use strict';

var app = angular.module('rbstore');

app.factory('CustomerFactory', function ($resource) {

    return $resource(endpoint + '/api/customer/:email',
        {
            email: '@email'
        },

        {
            find: {method: 'GET', params: { email: '@email' }}
        }
    );
});