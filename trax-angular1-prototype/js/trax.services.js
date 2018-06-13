traxApp
    .factory("TraxService", function () {
        traxService = {};

        traxService.activities = [];
        traxService.bookings = [];
        traxService.lastUsedDate = new Date();

        traxService.getLastUsedDate = function () {
            return traxService.lastUsedDate;
        };

        traxService.setLastUsedDate = function (date) {
            traxService.lastUsedDate = date;
        };

        traxService.addActivity = function (item) {
            var maxIdElement = _.max(traxService.activities, function (item) {
                return item.id
            });
            item.id = maxIdElement === -Infinity ? 0 : maxIdElement.id + 1;
            traxService.activities.push(item);
            return item.id;
        };

        traxService.getAllActivities = function () {
            return traxService.activities;
        };

        traxService.getActivityById = function (id) {
            return _.find(traxService.activities, function (item) {
                return item.id === id
            });
        };

        traxService.addBooking = function (item) {
            var maxIdElement = _.max(traxService.bookings, function (item) {
                return item.id
            });
            item.id = maxIdElement === -Infinity ? 0 : maxIdElement.id + 1;
            traxService.bookings.push(item);
            return item.id;
        };

        traxService.removeBooking = function (item) {
            var index = traxService.bookings.indexOf(item);
            traxService.bookings.splice(index, 1);
        };

        traxService.getAllBookingsForDate = function (date) {
            return _.filter(traxService.bookings, function (item) {
                return item.from.getDay() === date.getDay() &&
                    item.from.getMonth() === date.getMonth() &&
                    item.from.getFullYear() === date.getFullYear();
            });
        };

        traxService.getLatestBookingTimeForDate = function (date) {
            var bookingsForDate = traxService.getAllBookingsForDate(date);
            var maxBookingItem = _.max(bookingsForDate, function (item) {
                return item.to;
            });
            return maxBookingItem === -Infinity ? null : maxBookingItem.to;
        };

        traxService.createPauseBooking = function (date, minutes) {
            var fromDate = traxService.getLatestBookingTimeForDate(date);
            var toDate = moment(fromDate)
                .add(minutes, "minutes")
                .toDate();

            traxService.addBooking({
                from: fromDate,
                to: toDate,
                pause: true
            })
        };

        return traxService;
    });