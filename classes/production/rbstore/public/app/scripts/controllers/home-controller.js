'use strict';

var app = angular.module('blueBank');

app.controller('HomeController', function($scope, $window, AccountFactory, $http) {


    $scope.account = JSON.parse($window.sessionStorage.getItem('account'));
    $http.defaults.headers.common.Authorization = $window.sessionStorage.getItem('token');
    AccountFactory.find(
        {
            cpf: $scope.account.cpf,
            agencia: $scope.account.agencia,
            numero: $scope.account.numero

        }, {}, function (response) {
            $scope.account = response;
            $http.defaults.headers.common.Authorization = response.token;
        }, function (error) {
            $scope.errorMessage = error.data.message;
    });


    $scope.logout = function () {
        $window.sessionStorage.removeItem('account');
        $window.location.reload();
    }


});

app.factory('AccountFactory', function ($resource) {

    return $resource(endpoint + '/api/account?cpf=:cpf&agencia=:agencia&numero=:numero',
        {
            cpf: '@cpf',
            agencia: '@agencia',
            numero: '@numero'
        }, {
        find:  { method: 'GET' }
    });
});