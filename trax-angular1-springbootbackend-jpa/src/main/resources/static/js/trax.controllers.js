traxApp
    .controller("TraxOverviewController", ["$scope", "TraxService", function ($scope, traxService) {

        $scope.errorHandler = function () {
            console.log("An error has happened");
        };

        $scope.data = {};
        $scope.setDate = function (date) {
            $scope.data.date = date;
            $scope.updateBookings();
            traxService.setLastUsedDate(date);
        };
        $scope.navigateToYesterday = function () {
            var newDate = moment($scope.data.date).subtract(1, "days").toDate();
            $scope.setDate(newDate);
        };
        $scope.navigateToTomorrow = function () {
            var newDate = moment($scope.data.date).add(1, "days").toDate();
            $scope.setDate(newDate);
        };
        $scope.navigateToToday = function () {
            $scope.setDate(new Date());
        };
        $scope.removeBooking = function (item) {
            traxService
                .removeBooking(item)
                .then(
                    function () {
                        $scope.updateBookings();
                    },
                    $scope.errorHandler
                );
        };
        $scope.updateBookings = function () {
            traxService.getAllBookingsForDate($scope.data.date)
                .then(
                    function (response) {
                        $scope.bookings = response.data.bookings;
                        $scope.workingHours = response.data.hours;

                        var weekDay = moment($scope.data.date).isoWeekday();
                        $scope.workDay =
                            weekDay !== 6 && // Saturday
                            weekDay !== 7; // Sunday
                    },
                    $scope.errorHandler
                );
        };
        $scope.createBreak = function (minutes) {
            traxService.addPauseBooking({
                day: moment($scope.data.date).format("YYYY-MM-DD"),
                minutes: minutes
            }).then(
                function () {
                    $scope.updateBookings();
                },
                $scope.errorHandler
            );
        };

        $scope.setDate(traxService.getLastUsedDate());

        traxService.getAllActivities().then(
            function (response) {
                $scope.activities = response.data;
            },
            $scope.errorHandler
        );
    }]);

traxApp
    .controller("TraxAddBookingController", ["$scope", "$routeParams", "$location", "TraxService",
        function ($scope, $routeParams, $location, traxService) {

            $scope.errorHandler = function () {
                console.log("An error has happened");
            };

            function initBooking(activity) {
                var date = moment($routeParams.date, "YYYY-MM-DD").toDate();

                $scope.activity = activity;
                $scope.from = "";
                $scope.to = "";
                $scope.date = date;

                traxService
                    .getLatestBookingTimeForDate(date)
                    .then(
                        function (response) {
                            if (response.data.time) {
                                $scope.from = response.data.time;
                            }
                        },
                        $scope.errorHandler
                    );

                $scope.addMinutes = function (minutes) {
                    var referenceDate = $scope.from;
                    if ($scope.to !== "") {
                        referenceDate = $scope.to;
                    }
                    $scope.to = moment(referenceDate, "HH:mm")
                        .add(minutes, "minutes")
                        .format("HH:mm");
                };

                $scope.clearTo = function () {
                    $scope.to = "";
                };

                $scope.addBooking = function () {
                    traxService.addBooking({
                        day: moment($scope.date).format("YYYY-MM-DD"),
                        fromTime: $scope.from,
                        toTime: $scope.to,
                        activityId: activity.id
                    }).then(
                        function () {
                            $location.path("/");
                        },
                        $scope.errorHandler
                    );
                };
            }

            traxService.getActivityById(parseInt($routeParams.id, 10))
                .then(
                    function (response) {
                        initBooking(response.data);
                    },
                    $scope.errorHandler
                );
        }]);

traxApp
    .controller("TraxAddActivityController", ["$scope", "$location", "TraxService",
        function ($scope, $location, traxService) {

            $scope.errorHandler = function () {
                console.log("An error has happened");
            };

            $scope.addActivity = function () {
                traxService.addActivity({
                    name: $scope.name,
                    description: $scope.description
                }).then(
                    function () {
                        $location.path("/");
                    },
                    $scope.errorHandler
                );
            }
        }
    ]);