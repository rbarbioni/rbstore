/*global $ */
var app = angular.module('rbstore',
    [
        'ngRoute',
        'ngResource',
        'ngCookies',
        'ui.bootstrap',
        'angular-md5',
        'angular-loading-bar'
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
        .when('/order', {
            templateUrl : 'app/views/order.html'
        })
        .when('/status/:id', {
            templateUrl : 'app/views/status.html'
        })
        .when('/login', {
            templateUrl : 'app/views/login.html'
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
