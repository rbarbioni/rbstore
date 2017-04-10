'use strict';

var app = angular.module('rbstore');

app.factory('OrderCalcFactory', function ($resource) {

    return $resource(endpoint + '/api/order/calc',
        {},

        {
            calc: {method: 'POST'}
        }
    );
});

app.factory('OrderFactory', function ($resource) {

    return $resource(endpoint + '/api/order',
        {},

        {
            order: {method: 'POST'}
        }
    );
});

app.factory('OrderPaymentFactory', function ($resource) {

    return $resource(endpoint + '/api/order/:{id}/payment',
        {
            id: '@id'
        },

        {
            find: {method: 'GET', params: { id: '@id' }}
        }
    );
});