'use strict';

var app = angular.module('rbstore');

app.controller('ProductController', function($scope, $routeParams, $window, $location, ProductFactory) {

    $scope.product = ProductFactory.find({id: $routeParams.id});

    $scope.added = false;

    $scope.addCart=function(p){

        var _cart = $window.sessionStorage.getItem("cart");
        
        if(_cart == null){
            var _cart = new Array();
            p.quantity = 1
            _cart.push(p);
            $window.sessionStorage.setItem("cart", JSON.stringify(_cart));
        }else{
            _cart = JSON.parse(_cart);

            var contains = false;
            for (var i=0; i < _cart.length; i++){
                var p1 = _cart[i];
                if($scope.product.id == p1.id){
                    p1.quantity = p1.quantity+1
                    contains = true;
                }
            }

            if(!contains){
                p.quantity = 1;
                _cart.push(p);
            }

            $window.sessionStorage.setItem("cart", JSON.stringify(_cart));
        }

        $scope.added = true;
    }

    $scope.goToHome=function(){
        $window.location.assign('/');
    }

});

