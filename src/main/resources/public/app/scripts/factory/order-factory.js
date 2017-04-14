'use strict';

var app = angular.module('rbstore');


app.factory('OrderCalculatorFactory', function ($resource, $cookieStore) {

    return $resource(endpoint + '/api/order/calculator',
        {},

        {
            calculator: {
                method: 'POST',
                headers: {
                    'Authorization':  $cookieStore.get('token')
                }
            }
        }
    );
});

app.factory('OrderFactory', function ($resource, $cookieStore) {

    return $resource(endpoint + '/api/order',
        {},

        {
            order: {
                method: 'POST',
                headers: {
                    'authorization':  $cookieStore.get('token')
                }
            }
        }
    );
});

app.factory('OrderPaymentFactory', function ($resource, $cookieStore) {

    return $resource(endpoint + '/api/order/:id/payment',
        {
            id: '@id'
        },

        {
            payment: {
                method: 'POST',
                headers: {
                    'Authorization':  $cookieStore.get('token')
                },
                params: {
                    id: '@id',
                }
            }
        }
    );
});