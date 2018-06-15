traxApp
    .factory("TraxTestDataService", ["TraxService", function (traxService) {
        initService = {};
        initService.initialized = false;

        var time = function (timeString) {
            return moment(timeString, "HH:mm").toDate();
        };

        initService.init = function () {
            if (initService.initialized) {
                return;
            }

            var id1 = traxService.addActivity({
                name: "activity 1",
                description: "activity 1 description"
            });
            var id2 = traxService.addActivity({
                name: "activity 2",
                description: "activity 2 description"
            });

            var activity1 = traxService.getActivityById(id1);
            var activity2 = traxService.getActivityById(id2);

            traxService.addBooking({
                from: time("8:00"),
                to: time("8:30"),
                activity: activity1
            });

            traxService.addBooking({
                from: time("8:30"),
                to: time("9:00"),
                activity: activity2
            });

            initService.initialized = true;
        };

        return initService;
    }]);