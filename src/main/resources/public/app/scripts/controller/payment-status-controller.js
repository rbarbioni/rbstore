'use strict';

var app = angular.module('rbstore');

app.controller('PaymentStatusController', function ($scope, $window, $routeParams, $cookieStore, PaymentFactory) {

    PaymentFactory.find({id: $routeParams.id},
        function (result) {

            $scope.inAnalysis = false;
            $scope.preAuthorized = false;
            $scope.authorized = false;
            $scope.noAuthorized = false;
            
            for (var i=0; i < result.events.length; i++) {
                var e = result.events[i];

                if(e.type == 'PAYMENT.CREATED' || e.type == 'PAYMENT.IN_ANALYSIS'){
                    $scope.inAnalysis = true;
                }

                if(e.type == 'PAYMENT.PRE_AUTHORIZED'){
                    $scope.preAuthorized = true;
                }

                if(e.type == 'PAYMENT.AUTHORIZED'){
                    $scope.authorized = true;
                }

                if(e.type == 'PAYMENT.CANCELLED' || e.type == 'PAYMENT.REFUNDED' || e.type == 'PAYMENT.REVERSED' || e.type == 'PAYMENT.SETTLED'){
                    $scope.noAuthorized = true;
                }
            }

        },
        function (error) {
            console.log(error);
        }
    );
});