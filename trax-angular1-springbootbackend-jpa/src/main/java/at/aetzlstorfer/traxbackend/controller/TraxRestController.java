package at.aetzlstorfer.traxbackend.controller;

import at.aetzlstorfer.traxbackend.facades.TrackingFacade;
import at.aetzlstorfer.traxbackend.facades.dto.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
public class TraxRestController {

    private final TrackingFacade trackingFacade;

    public TraxRestController(TrackingFacade trackingFacade) {
        this.trackingFacade = trackingFacade;
    }

    @GetMapping("/activities/get")
    public List<ActivityDto> getAllActivities() {
        return trackingFacade.getAllActivities();
    }

    @GetMapping("/activities/get/{id}")
    public ActivityDto getActivityById(@PathVariable long id) {
        return trackingFacade.getActivityById(id);
    }

    @PostMapping("/activities/add")
    public long addActivity(@RequestBody AddActivityDto addActivityDto) {
        return trackingFacade.addActivity(addActivityDto);
    }

    @GetMapping("/bookings/get/{date}")
    public DayBookingsDto getBookingsForDay(
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate date
    ) {
        return trackingFacade.getBookingsForDay(date);
    }

    @GetMapping("/bookings/get/{date}/max")
    public MaxDateDto getMaxTimeForDay(
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate date
    ) {
        return trackingFacade.getMaxTimeForDay(date);
    }

    @PostMapping("/bookings/add")
    public long addBooking(@RequestBody AddBookingDto addBookingDto) {
        return trackingFacade.addBooking(addBookingDto);
    }

    @PostMapping("/bookings/addPause")
    public long addPauseBooking(@RequestBody AddPauseBookingDto addPauseBookingDto) {
        return trackingFacade.addPauseBooking(addPauseBookingDto);
    }

    @DeleteMapping("/bookings/{id}")
    public void removeBooking(@PathVariable long id) {
        trackingFacade.removeBooking(id);
    }
}
