'use strict';

var app = angular.module('rbstore');

app.controller('OrderController', function ($scope, $window, $location, OrderCalculatorFactory, OrderFactory, OrderPaymentFactory, ConfigurationFactory, CartService) {

    var publicKey = null;
    $scope.cart = CartService.getCart();
    $scope.order = new Order();
    $scope.order.items = $scope.cart;
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
        $scope.cart = CartService.rem(id);
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

    function sintetizeItens() {

        var items = new Array();

        for (var i = 0; i < $scope.cart.length; i++) {
            var cart = angular.copy($scope.cart[i]);
            cart.price = Number((cart.price).toString().split(".").join(""))
            items.push(cart)
        }

        return items;
    }

    $scope.validateOrder = function () {

        if(publicKey == null){
            ConfigurationFactory.findAll(
                function (result) {
                    publicKey = result.publicKey;
                    var cc = validateCreditCard(publicKey);
                    if(cc != null){
                        processOrder(cc);
                    }else{
                        $scope.creditCardError = 'Cartão de crédito inválido';
                    }
                }
            );
        }else{
            var cc = validateCreditCard(publicKey);
            if(cc != null){
                processOrder(cc);
            }else{
                $scope.creditCardError = 'Cartão de crédito inválido';
            }
        }
    }

    function processOrder(cc) {
        $scope.creditCardError = null;
        $scope.payment.fundingInstrument.creditCard.hash = cc.hash();
        $scope.payment.fundingInstrument.creditCard.holder.fullname = $scope.order.customer.fullname;
        $scope.payment.fundingInstrument.creditCard.holder.birthdate = $scope.order.customer.birthDate;
        $scope.payment.fundingInstrument.creditCard.holder.taxDocument = $scope.order.customer.taxDocument;
        $scope.payment.fundingInstrument.creditCard.holder.phone = $scope.order.customer.phone;

        $scope.order.items = sintetizeItens();

        OrderFactory.order({}, $scope.order, function (result) {
            requestPayment(result.id);

        }, function (error) {
            console.log(error);
        })
    }

    function validateCreditCard(publicKey) {
        var cc = new Moip.CreditCard({
            number  : $scope.payment.creditCard.number,
            cvc     : $scope.payment.creditCard.cvc,
            expMonth: $scope.payment.creditCard.expirationMonth,
            expYear : $scope.payment.creditCard.expirationYear,
            pubKey  : publicKey
        });

        return cc.isValid() ? cc : null;
    }

    function requestPayment(orderId) {
        OrderPaymentFactory.payment({id : orderId}, $scope.payment,
            function (result) {
                console.log(result);
                CartService.clear();
                $window.location.assign('#/status/' + result.id);
            },
            function (error) {
                console.log(error);
            }
        );
    }
});