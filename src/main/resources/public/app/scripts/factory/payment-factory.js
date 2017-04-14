'use strict';

var app = angular.module('rbstore');


app.factory('PaymentFactory', function ($resource, $cookieStore) {

    return $resource(endpoint + '/api/payment/:id',
        {},

        {
            find: {
                method: 'GET',
                headers: {
                    'Authorization':  $cookieStore.get('token')
                }
            }
        }
    );
});