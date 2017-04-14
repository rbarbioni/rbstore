'use strict';

var app = angular.module('rbstore');

app.service('CartService', function ($window) {

    this.add = function (p) {

        var _cart = getCarts();

        if(_cart == null){
            var _cart = new Array();
            p.quantity = 1
            _cart.push(p);
            $window.sessionStorage.setItem("cart", JSON.stringify(_cart));
        }else{

            var contains = false;
            for (var i=0; i < _cart.length; i++){
                var p1 = _cart[i];
                if(p.id == p1.id){
                    p1.quantity = p1.quantity+1
                    contains = true;
                }
            }

            if(!contains){
                p.quantity = 1;
                _cart.push(p);
            }

            $window.sessionStorage.setItem("cart", JSON.stringify(_cart));

            return _cart;
        }
    }

    this.update = function (cart) {
        this._cart = cart;
        $window.sessionStorage.setItem("cart", JSON.stringify(cart));
    }

    this.rem = function (id) {

        var _cart = getCarts();

        for (var i = 0; i < _cart.length; i++) {
            var p = _cart[i];
            if (id == p.id) {
                _cart.splice(i, 1);
            }
        }

        $window.sessionStorage.setItem("cart", JSON.stringify(_cart));
        return _cart;
    }

    function calc() {
        var amount = 0;
        var _cart = getCarts();
        for (var i = 0; i < _cart.length; i++) {
            var cart = _cart[i];
            amount +=  ((!isNaN(cart.price) ? cart.price : 0 ) * cart.quantity);
        }

        $window.sessionStorage.setItem("cart", JSON.stringify(_cart));
        return amount;
    }
    this.calc = function () {
        return calc();
    }

    this.carts = function () {
        return getCarts();
    }

    function getCarts() {
        return JSON.parse($window.sessionStorage.getItem("cart"));
    }
});