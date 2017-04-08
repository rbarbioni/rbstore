'use strict';

var app = angular.module('rbstore');

app.controller('CartController', function($scope, $window) {

    $scope.carts = JSON.parse($window.sessionStorage.getItem("cart"));

    $scope.remCart=function(id){

        for (var i=0; i < $scope.carts.length; i++){
            var p = $scope.carts[i];
            if(id == p.id){
                $scope.carts.splice(i, 1);
            }
        }

        $window.sessionStorage.setItem("cart", JSON.stringify($scope.carts));
    }
});

