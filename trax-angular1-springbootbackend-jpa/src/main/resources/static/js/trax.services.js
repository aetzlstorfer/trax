traxApp
    .factory("TraxService", ["$http", function ($http) {
        traxService = {};

        traxService.lastUsedDate = new Date();

        traxService.getLastUsedDate = function () {
            return traxService.lastUsedDate;
        };

        traxService.setLastUsedDate = function (date) {
            traxService.lastUsedDate = date;
        };

        traxService.addActivity = function (item) {
            return $http.post("/activities/add", item);
        };

        traxService.getAllActivities = function () {
            return $http.get("/activities/get");
        };

        traxService.getActivityById = function (id) {
            return $http.get("/activities/get/" + id);
        };

        traxService.addBooking = function (item) {
            return $http.post("/bookings/add", item);
        };

        traxService.addPauseBooking = function (item) {
            return $http.post("/bookings/addPause", item);
        };

        traxService.removeBooking = function (item) {
            return $http.delete("/bookings/" + item.id);
        };

        traxService.getAllBookingsForDate = function (date) {
            return $http.get("/bookings/get/" + moment(date).format("YYYY-MM-DD"));
        };

        traxService.getLatestBookingTimeForDate = function (date) {
            return $http.get("/bookings/get/" + moment(date).format("YYYY-MM-DD") + "/max");
        };

        return traxService;
    }]);