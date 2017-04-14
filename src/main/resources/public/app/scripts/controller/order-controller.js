'use strict';

var app = angular.module('rbstore');

app.controller('OrderController', function ($scope, $window, $location, OrderCalculatorFactory, OrderFactory, OrderPaymentFactory, LoginFactory, CartService) {

    var publicKey = 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoBttaXwRoI1Fbcond5mS7QOb7X2lykY5hvvDeLJelvFhpeLnS4YDwkrnziM3W00UNH1yiSDU+3JhfHu5G387O6uN9rIHXvL+TRzkVfa5iIjG+ap2N0/toPzy5ekpgxBicjtyPHEgoU6dRzdszEF4ItimGk5ACx/lMOvctncS5j3uWBaTPwyn0hshmtDwClf6dEZgQvm/dNaIkxHKV+9jMn3ZfK/liT8A3xwaVvRzzuxf09xJTXrAd9v5VQbeWGxwFcW05oJulSFjmJA9HcmbDYHJT+sG2mlZDEruCGAzCVubJwGY1aRlcs9AQc1jIm/l8JwH7le2kpk3QoX+gz0wWwIDAQAB';
    $scope.carts = CartService.carts();
    $scope.order = new Order();
    $scope.order.items = $scope.carts;
    $scope.order.customer = JSON.parse($window.sessionStorage.getItem("customer"));

    $scope.payment = new Payment();
    calc();

    $scope.remCart = function (id) {
        $scope.carts = CartService.rem(id);
    }

    $scope.calc = function () {
        calc();
    }

    function calc() {

        $scope.order.installmentCount = $scope.payment.installmentCount;
        OrderCalculatorFactory.calculator({}, $scope.order,
            function (result) {
                $scope.order.ownId = result.ownId;
                $scope.order.amount.subtotals.addition = result.addition;
                $scope.order.amount.subtotals.discount = result.discount;
                $scope.order.amount.subtotals.amount = result.amount;
            },
            function (error) {
                console.log(error)
            }
        );
    }

    $scope.login = function () {

        LoginFactory.login({}, {email: "joaosilva@email.com", password: "testemoip"},

            function (result) {
                console.log(result);
                $scope.customer = result.customer;
                $window.sessionStorage.token = result.token;
                $window.location.assign('#/order');
            },

            function (error) {
                console.log(error);
                $scope.errors = error;
            }
        );

    }

    function sintetizeItens() {

        var items = new Array();

        for (var i = 0; i < $scope.carts.length; i++) {
            var cart = angular.copy($scope.carts[i]);
            cart.price = Number((cart.price).toString().split(".").join(""))
            items.push(cart)
        }

        return items;
    }

    $scope.requestOrder = function () {

        $scope.order.items = sintetizeItens();

        OrderFactory.order({}, $scope.order, function (result) {
            console.log(result);

            requestPayment(result.id);

        }, function (error) {
            console.log(error);
        })
    }

    function requestPayment(orderId) {

        var cc = new Moip.CreditCard({
            number  : order.creditCard.number,
            cvc     : order.creditCard.cvc,
            expMonth: order.creditCard.expirationMonth,
            expYear : order.creditCard.expirationYear,
            pubKey  : publicKey
        });

        console.log(cc);
        if( cc.isValid()){

            var payment = new Payment();
            payment.installmentCount = $scope.order.installmentCount;
            payment.fundingInstrument.creditCard.holder = $scope.customer;
            payment.fundingInstrument.creditCard.hash = cc.hash();

            OrderPaymentFactory.payment({id : orderId}, payment,
                function (result) {
                    console.log(result);
                },
                function (error) {
                    console.log(error);
                }
            );

        }
        else{

        }


    }
});

