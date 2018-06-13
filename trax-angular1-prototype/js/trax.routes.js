traxApp
    .config(function ($routeProvider) {
        $routeProvider
            .when("/", {
                templateUrl: "views/overview.html",
                controller: "TraxOverviewController"
            })
            .when("/addBooking/:id/:date", {
                templateUrl: "views/addBooking.html",
                controller: "TraxAddBookingController"
            })
            .when("/addActivity", {
                templateUrl: "views/addActivity.html",
                controller: "TraxAddActivityController"
            })
            .otherwise({
                redirectTo: "/"
            })
    });