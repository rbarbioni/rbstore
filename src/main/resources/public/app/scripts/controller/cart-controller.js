'use strict';

var app = angular.module('rbstore');

app.controller('CartController', function ($scope, $window, $routeParams, $cookieStore, OrderCalculatorFactory, OrderFactory, LoginFactory, CartService) {

    $scope.cart = CartService.getCart();
    $scope.order = {};
    $scope.order.items = new Array();
    $scope.order.items = $scope.cart;
    $scope.order.installmentCount=1;
    $scope.order.fundingInstrument = []
    $scope.order.fundingInstrument.method = 'method';

    $scope.actionBlock = true;
    calc();

    $scope.remCart = function (id) {
        $scope.cart = CartService.rem(id);
        if($scope.cart.length == 0){
            $scope.order.amount = 0;
            $scope.actionBlock = true;
        }
    }

    function calc() {
        CartService.update($scope.cart);
        $scope.order.amount =  CartService.calc();
        if($scope.order.amount > 0){
            $scope.actionBlock = false;
        }
    }

    $scope.calc = function () {
        calc();
    }

    $scope.requestOrder = function () {

        var customer = $window.sessionStorage.customer;

        if($cookieStore.get('token') == null || customer == null || customer == undefined){
            $window.location.assign('#/login');
            return;
        }

        $window.location.assign('#/order');
    }
});

