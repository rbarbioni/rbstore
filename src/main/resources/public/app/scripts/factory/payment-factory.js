'use strict';

var app = angular.module('rbstore');


app.factory('PaymentFactory', function ($resource, $window) {

    return $resource(endpoint + '/secure/api/payment/:id',
        {},

        {
            find: {
                method: 'GET',
                headers: {
                    'Authorization':  $window.sessionStorage.token
                }
            }
        }
    );
});