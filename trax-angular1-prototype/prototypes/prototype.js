var app = angular.module("trax_prototype", ["ngRoute"]);

app.config(function ($routeProvider) {
    $routeProvider
        .when("/", {
            templateUrl: "views/main.html"
        })
        .when("/addBooking", {
            templateUrl: "views/addBooking.html"
        })
        .when("/addActivity", {
            templateUrl: "views/addActivity.html"
        })
        .otherwise({
            redirectTo: "/"
        })
});