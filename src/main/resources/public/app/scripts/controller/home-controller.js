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

    $scope.getCart=function(){
        return CartService.getCart();
    };

    $scope.goToDetail=function(p){
        $location.path = "/product/" + p.id;
    }

    $scope.getName = function () {
        var customer = $window.sessionStorage.customer;
        if(customer != null && customer != undefined){
            customer = JSON.parse(customer);
            return 'Olá ' + customer.fullname;
        }
        return '';
    };

});

