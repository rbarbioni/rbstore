'use strict';

var app = angular.module('rbstore');

app.controller('HomeController', function($scope, $window, $routeParams, $location, ProductFactory, CartService) {

    $scope.products = ProductFactory.findAll();

    $scope.getTimes=function(n){
        return new Array(n);
    };

    $scope.goToCarts = function () {
        $location.path('/cart');
    };

    $scope.getCarts=function(){
        return CartService.carts();
    };

    $scope.goToDetail=function(p){
        $location.path = "/product/" + p.id;
    }

});

