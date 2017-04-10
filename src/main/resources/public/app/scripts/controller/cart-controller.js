'use strict';

var app = angular.module('rbstore');

app.controller('CartController', function ($scope, $window, OrderCalcFactory, OrderFactory, CustomerFactory) {


    $scope.carts = JSON.parse($window.sessionStorage.getItem("cart"));

    $scope.order = {};
    $scope.order.items = new Array();
    $scope.order.items = $scope.carts;
    $scope.order.fundingInstrument = []
    $scope.order.fundingInstrument.method = 'method';
    $scope.order.customer = CustomerFactory.find({email: 'joaosilva@email.com'})

    calc();
    $scope.remCart = function (id) {

        for (var i = 0; i < $scope.carts.length; i++) {
            var p = $scope.carts[i];
            if (id == p.id) {
                $scope.carts.splice(i, 1);
            }
        }

        $window.sessionStorage.setItem("cart", JSON.stringify($scope.carts));
        
        $scope.order.products = $scope.carts;
        calc();
    }

    $scope.calc = function () {
        calc();
    }

    $scope.requestOrder = function () {

        var order ={}
        order.ownId = $scope.order.ownId;
        order.items = $scope.order.items;
        order.customer = $scope.order.customer;

        OrderFactory.order({}, order, function (result) {

            console.error(result);

        }, function (error) {
            console.log(error);
        })
    }

    function calc() {
        OrderCalcFactory.calc({}, $scope.order, function (result) {
            $window.sessionStorage.setItem("cart", JSON.stringify($scope.carts));
            $scope.order.amount = result.amount
            $scope.order.discount = result.discount;
            $scope.order.ownId = result.ownId;
        });

    }

});

