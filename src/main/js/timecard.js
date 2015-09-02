"use strict";

class MainController {

    constructor($resource) {
        this.$resource = $resource;

        this.now = new Date();

        this.days = [
            {name: "Monday", entries: []},
            {name: "Tuesday", entries: []},
            {name: "Wednesday", entries: []},
            {name: "Thursday", entries: []},
            {name: "Friday", entries: []},
            {name: "Saturday", entries: []},
            {name: "Sunday", entries: []}
        ];

        this.types = [
            "Billable",
            "Non-billable",
            "Break"
        ];

        this.offset = 0;

        this.updateWeek();
        this.currentDay = this.days[this.dayIndex()];
    }

    dayIndex() {
        var day = (this.now.getDay() + 6) % 7;
        return day;
    }

    updateWeek() {
        for (var i = 0; i < 7; i++) {
            let day = this.days[i];
            let currentDay = this.dayIndex();
            let diff = i - currentDay - this.offset;
            day.date = new Date(this.now.getTime() + diff * (60 * 60 * 24 * 1000));
            this.$resource('day').get({date: this.dateOnly(day.date)}).$promise.then(dayFromServer => {
                day.id = dayFromServer.id;
                day.entries = dayFromServer.entries || [];
            });


        }
    }


    prevWeek() {
        this.offset += 1;
        this.updateWeek();
    }

    nextWeek() {
        this.offset -= 1;
        this.updateWeek();
    }


    start(day) {
        day.entries.push({start: new Date()});
        day.running = true;
    }

    stop(day) {
        day.entries[day.entries.length - 1].stop = new Date();
        day.running = false;
    }


    sum(entries) {
        if (!entries) {
            return "0:00";
        }
        var sumMin = entries.map(function (entry) {
            var start = new Date("2015-04-03 " + entry.start);
            var stop = new Date("2015-04-03 " + entry.stop);
            var diffMs = stop - start;
            var diffMin = diffMs / (1000 * 60);
            return diffMin;
        }).reduce(function (sum, elem) {
            return sum + elem;
        }, 0);

        var hours = sumMin / 60;
        var minutes = sumMin % 60;
        if (minutes < 10) {
            minutes = "0" + minutes;
        }
        return hours + ":" + minutes;
    }

;

    sum2(day, type) {
        if (!day || !day.entries) {
            return;
        }
        var entriesOfType = day.entries.filter(function (entry) {
            return entry.type === type;
        });
        return this.sum(entriesOfType);
    }

    deleteEntry(day, index) {
        if (!day.running) {
            day.entries.splice(index, 1);
        }
    }

    dateOnly(date) {
        var dateStr = date.toISOString();
        return dateStr.split("T")[0];
    }

    save(day) {
        var Day = this.$resource("day");
        var dayApi = angular.copy(day);
        var dateString = this.dateOnly(dayApi.date);
        dayApi.date = [
            parseInt(dateString.split("-")[0]), // year
            parseInt(dateString.split("-")[1]), // month
            parseInt(dateString.split("-")[2])]; // day
        Day.save(dayApi).$promise.then(function (savedDay) {
            savedDay.date = new Date(savedDay.date[0] + "-" + savedDay.date[1] + "-" + savedDay.date[2]);
            angular.copy(savedDay, day);
        });
    }

    lock(day) {
        throw "Not implemented!";
    }
}

angular.module("mainApp", ['ngResource']).controller("MainController", MainController);