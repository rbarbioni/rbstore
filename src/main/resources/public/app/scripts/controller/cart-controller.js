'use strict';

var app = angular.module('rbstore');

app.controller('CartController', function ($scope, $window, $location, $cookieStore, OrderCalculatorFactory, OrderFactory, LoginFactory, CartService) {

    $scope.carts = CartService.carts();
    $scope.order = {};
    $scope.order.items = new Array();
    $scope.order.items = $scope.carts;
    $scope.order.installmentCount=1;
    $scope.order.fundingInstrument = []
    $scope.order.fundingInstrument.method = 'method';

    $scope.actionBlock = true;
    calc();

    $scope.remCart = function (id) {
        $scope.carts = CartService.rem(id);
        if($scope.carts.length == 0){
            $scope.order.amount = 0;
            $scope.actionBlock = true;
        }
    }

    function calc() {
        CartService.update($scope.carts);
        $scope.order.amount =  CartService.calc();
        if($scope.order.amount > 0){
            $scope.actionBlock = false;
        }
    }

    $scope.calc = function () {
        calc();
    }

    $scope.login = function () {

        LoginFactory.login({}, {email: "joaosilva@email.com", password: "testemoip"},

            function (result) {
                console.log(result);
                $scope.customer = result.customer;
                $window.sessionStorage.customer = JSON.stringify(result.customer);
                $cookieStore.put('token',result.token);
                $window.location.assign('#/order');
            },

            function (error) {
                console.log(error);
                $scope.errors = error;
            }
        );

    }

    $scope.requestOrder = function () {

        var order ={}
        order.ownId = $scope.order.ownId;
        order.items = $scope.order.items;
        $scope.order.customer = $scope.customer;

        OrderFactory.order({}, order, function (result) {

            console.error(result);

        }, function (error) {
            console.log(error);
        })
    }
});

