traxApp
    .controller("TraxOverviewController", ["$scope", "TraxService", "TraxTestDataService", function ($scope, traxService, traxTestDataService) {
        traxTestDataService.init();

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
            traxService.removeBooking(item);
            $scope.updateBookings();
        };
        $scope.updateBookings = function () {
            $scope.bookings = traxService.getAllBookingsForDate($scope.data.date);

            $scope.workingHours = _.reduce($scope.bookings, function (sum, booking) {
                var hours = 0;
                if (!booking.pause) {
                    var from = moment(booking.from);
                    var to = moment(booking.to);
                    var diff = to.diff(from);
                    hours = moment.duration(diff).asHours();
                }
                return sum + hours;
            }, 0.0);

            var weekDay = moment($scope.data.date).isoWeekday();
            $scope.workDay =
                weekDay !== 6 && // Saturday
                weekDay !== 7; // Sunday
        };
        $scope.createBreak = function (minutes) {
            traxService.createPauseBooking($scope.data.date, minutes);
            $scope.updateBookings();
        };

        $scope.setDate(traxService.getLastUsedDate());
        $scope.activities = traxService.getAllActivities();
    }]);

traxApp
    .controller("TraxAddBookingController", ["$scope", "$routeParams", "$location", "TraxService",
        function ($scope, $routeParams, $location, traxService) {
            var activity = traxService.getActivityById(parseInt($routeParams.id, 10));

            var date = moment($routeParams.date, "YYYY-MM-DD").toDate();

            $scope.activity = activity;
            $scope.from = "";
            $scope.to = "";
            $scope.date = date;

            var latestToDate = traxService.getLatestBookingTimeForDate(date);
            if (latestToDate) {
                $scope.from = moment(latestToDate).format("HH:mm");
            }

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
                var fromTime = moment($scope.from, "HH:mm");
                var toTime = moment($scope.to, "HH:mm");

                var from = moment($scope.date)
                    .minutes(fromTime.minutes())
                    .hours(fromTime.hours())
                    .toDate();

                var to = moment($scope.date)
                    .minutes(toTime.minutes())
                    .hours(toTime.hours())
                    .toDate();

                traxService.addBooking({
                    from: from,
                    to: to,
                    activity: activity
                });

                $location.path("/");
            };
        }]);

traxApp
    .controller("TraxAddActivityController", ["$scope", "$location", "TraxService",
        function ($scope, $location, traxService) {
            $scope.addActivity = function () {
                traxService.addActivity({
                    name: $scope.name,
                    description: $scope.description
                });
                $location.path("/");
            }
        }
    ]);