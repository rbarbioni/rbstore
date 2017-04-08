'use strict';

var app = angular.module('rbstore');

app.factory('ProductFactory', function ($resource) {

    return $resource(endpoint + '/api/product/:id',
        {
            id: '@id'
        },

        {
            findAll: {method: 'GET', isArray: true},
            find: {method: 'GET', params: { id: '@id' }}
        }
    );
});