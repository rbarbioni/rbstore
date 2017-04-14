'use strict';

var app = angular.module('rbstore');

app.controller('ProductController', function($scope, $routeParams, $window, $location, ProductFactory, CartService) {

    $scope.product = ProductFactory.find({id: $routeParams.id});

    $scope.added = false;

    $scope.addCart=function(p){
        CartService.add(p);
        $scope.added = true;
    }

    $scope.goToHome=function(){
        $window.location.assign('/');
    }

});

