'use strict';

var app = angular.module('rbstore');

app.controller('PaymentStatusController', function ($scope, $window, $routeParams, $cookieStore, PaymentFactory, SocketFactory) {

    $scope.inAnalysis = false;
    $scope.preAuthorized = false;
    $scope.authorized = false;
    $scope.noAuthorized = false;

    SocketFactory.socket( '/topic/app/payment/' + $routeParams.id +'/updates', function( message ){
        console.log(message);
        processMessage(message);
    } );

    function processMessage(result) {

        $scope.inAnalysis = false;
        $scope.preAuthorized = false;
        $scope.authorized = false;
        $scope.noAuthorized = false;

        var data = JSON.parse(result.body);

        for (var i=0; i < data.resource.payment.events.length; i++) {
            var e = data.resource.payment.events[i];
            processEvent(e.type);
        }

        $scope.$apply()
    }

    function processEvent(type) {

        if(type == 'PAYMENT.CREATED' || type == 'PAYMENT.IN_ANALYSIS'){
            $scope.inAnalysis = true;
        }

        if(type == 'PAYMENT.PRE_AUTHORIZED'){
            $scope.preAuthorized = true;
        }

        if(type == 'PAYMENT.AUTHORIZED'){
            $scope.authorized = true;
        }

        if(type == 'PAYMENT.CANCELLED' || type == 'PAYMENT.REFUNDED' || type == 'PAYMENT.REVERSED' || type == 'PAYMENT.SETTLED'){
            $scope.noAuthorized = true;
        }
    }

    PaymentFactory.find({id: $routeParams.id},
        function (result) {

            $scope.inAnalysis = false;
            $scope.preAuthorized = false;
            $scope.authorized = false;
            $scope.noAuthorized = false;
            
            for (var i=0; i < result.events.length; i++) {
                var e = result.events[i];
                processEvent(e.type);
            }
        },
        function (error) {
            console.log(error);
        }
    );
});