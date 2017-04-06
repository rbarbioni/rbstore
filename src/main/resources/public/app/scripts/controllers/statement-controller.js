'use strict';

var app = angular.module('blueBank');

app.controller('StatementController', function($scope, StatementFactory, AccountFactory, $window, $http) {

    $scope.account = JSON.parse($window.sessionStorage.getItem('account'));
    $scope.startDate = null;
    $scope.endDate = null;

    $http.defaults.headers.common.Authorization = $window.sessionStorage.getItem('token');


    AccountFactory.find(
        {
            cpf: $scope.account.cpf,
            agencia: $scope.account.agencia,
            numero: $scope.account.numero

        }, {}, function (response) {
            $scope.account = response;
        }, function (error) {
            $scope.errorMessage = error.data.message;
        });

    StatementFactory.find(
        {
            cpf: $scope.account.cpf,
            agencia: $scope.account.agencia,
            numero: $scope.account.numero,
            data_inicial: $scope.startDate,
            data_final: $scope.endDate
        },
        {}, function (response) {
            $scope.statement = response;
        }, function (error) {
            $scope.errorMessage = error.data.message;
        });
});

app.factory('StatementFactory', function ($resource) {

    return $resource(endpoint + '/api/account/statement?cpf=:cpf&agencia=:agencia&numero=:numero&data_inicial=:data_inicial&data_final=:data_final',
        {
            cpf: '@cpf',
            agencia: '@agencia',
            numero: '@numero',
            data_inicial: '@data_inicial',
            data_final: '@data_final'
        }, {
            find:  { method: 'GET', isArray: true }
        });
});