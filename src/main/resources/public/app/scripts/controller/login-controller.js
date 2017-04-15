'use strict';

var app = angular.module('rbstore');

app.controller('LoginController', function($scope, $window, $cookieStore, LoginFactory) {

    $scope.user = new Object();
    
    $scope.login=function(){
        
        LoginFactory.login({}, $scope.user,
            function (result) {
                console.log(result)
                $window.sessionStorage.customer = JSON.stringify(result.customer);
                $cookieStore.put('token',result.token);
                $window.location.assign('#/order');                
            },
            function (error) {
                console.log(error)
                $scope.errors = error;
            }
        );
    };
});