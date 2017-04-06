'use strict';

var app = angular.module('blueBank');

app.controller('TransferController', function($scope, TransferFactory, $window, $http) {

    $scope.account = JSON.parse($window.sessionStorage.getItem('account'));
    $scope.transfer = {};
    $scope.transfer.amount = 0;
    $scope.transfer.source = $scope.account;

    $http.defaults.headers.common.Authorization = $window.sessionStorage.getItem('token');
    $scope.doTransfer = function () {
        TransferFactory.transfer({}, $scope.transfer, function (response) {
            $scope.account = response;
            $scope.transfer.destination = {};
            $scope.transfer.amount = 0;
            $scope.success = true;
            $scope.errorMessage = null;
        }, function (error) {
            $scope.success = false;
            $scope.errorMessage = error.data.message;
        });
    }


});

app.factory('TransferFactory', function ($resource) {

    return $resource(endpoint + '/api/account/transfer', {}, {

        transfer:  { method: 'POST' }
    });
});