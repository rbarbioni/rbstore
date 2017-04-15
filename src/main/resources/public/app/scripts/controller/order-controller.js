'use strict';

var app = angular.module('rbstore');

app.controller('OrderController', function ($scope, $window, $location, OrderCalculatorFactory, OrderFactory, OrderPaymentFactory, LoginFactory, CartService) {

    var publicKey = '-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoBttaXwRoI1Fbcond5mS7QOb7X2lykY5hvvDeLJelvFhpeLnS4YDwkrnziM3W00UNH1yiSDU+3JhfHu5G387O6uN9rIHXvL+TRzkVfa5iIjG+ap2N0/toPzy5ekpgxBicjtyPHEgoU6dRzdszEF4ItimGk5ACx/lMOvctncS5j3uWBaTPwyn0hshmtDwClf6dEZgQvm/dNaIkxHKV+9jMn3ZfK/liT8A3xwaVvRzzuxf09xJTXrAd9v5VQbeWGxwFcW05oJulSFjmJA9HcmbDYHJT+sG2mlZDEruCGAzCVubJwGY1aRlcs9AQc1jIm/l8JwH7le2kpk3QoX+gz0wWwIDAQAB-----END PUBLIC KEY-----';
    $scope.carts = CartService.carts();
    $scope.order = new Order();
    $scope.order.items = $scope.carts;
    $scope.order.customer = JSON.parse($window.sessionStorage.getItem("customer"));
    $scope.payment = new Payment();

    $scope.payment.creditCard = new Object();
    $scope.payment.installmentCount = 1;
    $scope.payment.creditCard.number = 5555666677778884;
    $scope.payment.creditCard.cvc = '123';
    $scope.payment.creditCard.expirationMonth = '12';
    $scope.payment.creditCard.expirationYear = '18';

    $scope.promoCode = null;

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

                if($scope.order.code != null && $scope.order.amount.subtotals.discount == 0){
                    $scope.promoCode = true
                }else{
                    $scope.promoCode = false;
                }

                if($scope.order.code == undefined || $scope.order.code == ''){
                    $scope.promoCode = null;
                }

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
            requestPayment(result.id);
            CartService.clear();
        }, function (error) {
            console.log(error);
        })
    }

    function requestPayment(orderId) {

        var cc = new Moip.CreditCard({
            number  : $scope.payment.creditCard.number,
            cvc     : $scope.payment.creditCard.cvc,
            expMonth: $scope.payment.creditCard.expirationMonth,
            expYear : $scope.payment.creditCard.expirationYear,
            pubKey  : publicKey
        });

        console.log(cc);
        if( cc.isValid()){

            $scope.payment.fundingInstrument.creditCard.hash = cc.hash();
            $scope.payment.fundingInstrument.creditCard.holder.fullname = $scope.order.customer.fullname;
            $scope.payment.fundingInstrument.creditCard.holder.birthdate = $scope.order.customer.birthDate;
            $scope.payment.fundingInstrument.creditCard.holder.taxDocument = $scope.order.customer.taxDocument;
            $scope.payment.fundingInstrument.creditCard.holder.phone = $scope.order.customer.phone;


            OrderPaymentFactory.payment({id : orderId}, $scope.payment,
                function (result) {
                    console.log(result);
                    $window.location.assign('#/status/' + result.id);
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

