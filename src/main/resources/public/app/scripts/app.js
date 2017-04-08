/*global $ */
var app = angular.module('rbstore',
    [
        'ngRoute',
        'ngResource',
        'ngCookies',
        'ui.bootstrap',
        'angular-md5'
    ]);


var endpoint = '';

app.config(function($routeProvider, $locationProvider) {
    $routeProvider
        
        .when('/', {
            templateUrl : 'app/views/home.html'
        })
        .when('/product/:id', {
            templateUrl : 'app/views/product.html'
        })
        .when('/cart', {
            templateUrl : 'app/views/cart.html'
        })
        .otherwise({
            redirectTo: '/'
        });

    $locationProvider.html5Mode(false);
    $locationProvider.hashPrefix('');
});

app.run(['$location',
    function ($location) {
        endpoint = $location.protocol() + '://' + $location.host() + ':' + $location.port();
    }
]);