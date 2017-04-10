'use strict';

var app = angular.module('rbstore');

app.controller('HomeController', function($scope, $window, $routeParams, $location, ProductFactory) {

    $scope.products = ProductFactory.findAll();

    $scope.getTimes=function(n){
        return new Array(n);
    };

    $scope.goToCarts = function () {
        $location.path('/cart');
    };

    $scope.getCarts=function(n){
        var _cart = $window.sessionStorage.getItem("cart");
        if(_cart == null){
            _cart = new Array();
            return _cart;
        }else{
            _cart = JSON.parse(_cart)
            return _cart;
        }
    };

    $scope.goToDetail=function(p){
        $location.path = "/product/" + p.id;
    }

});

