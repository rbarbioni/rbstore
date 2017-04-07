/*global $ */
var app = angular.module('blueBank',
    [
        'ngRoute',
        'ngResource',
        'ngCookies',
        'ui.bootstrap',
        'angular-md5'
    ]);


var endpoint = '';
var account = null;

app.config(function($routeProvider, $locationProvider) {
    $routeProvider
        
        .when('/', {
            templateUrl : 'app/views/home.html'
        })
        
        .when('/transfer', {
            templateUrl : 'app/views/transfer.html'
        })
        
        .when('/extract', {
            templateUrl : 'app/views/statement.html'
        });

    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });
});

app.run(['$location',
    function ($location) {
        endpoint = $location.protocol() + '://' + $location.host() + ':' + $location.port();
    }
]);
