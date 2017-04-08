'use strict';

var app = angular.module('rbstore');

app.controller('ProductController', function($scope, $routeParams, $window, ProductFactory) {

    $scope.product = ProductFactory.find({id: $routeParams.id});

    $scope.addCart=function(p){

        var _cart = $window.sessionStorage.getItem("cart");
        
        if(_cart == null){
            var _cart = new Array();
            p.qtd = 1
            _cart.push(p);
            $window.sessionStorage.setItem("cart", JSON.stringify(_cart));
        }else{
            _cart = JSON.parse(_cart);

            var contains = false;
            for (var i=0; i < _cart.length; i++){
                var p1 = _cart[i];
                if($scope.product.id == p1.id){
                    p1.qtd = p1.qtd+1
                    contains = true;
                }
            }

            if(!contains){
                p.qtd = 1;
                _cart.push(p);
            }

            $window.sessionStorage.setItem("cart", JSON.stringify(_cart));
        }
    }

    $scope.remCart=function(){

    }

});

