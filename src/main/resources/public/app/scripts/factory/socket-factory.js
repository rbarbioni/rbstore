
'use strict';

var app = angular.module('rbstore');

app.factory('SocketFactory', function($rootScope) {
    return {
        socket : function( subscribePath, onMessage ){
            
            var socket = new SockJS('/updates', null, {debug: true});
            var stompClient = Stomp.over(socket);
            stompClient.connect({}, function() {
                stompClient.subscribe( subscribePath , function( message ){
                    onMessage(message);
                });
            });

            $rootScope.stompClient = stompClient;

            return;
        }
    }
});