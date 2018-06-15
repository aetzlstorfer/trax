package at.aetzlstorfer.traxbackend.facades;

import at.aetzlstorfer.traxbackend.facades.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface TrackingFacade {

    List<ActivityDto> getAllActivities();

    ActivityDto getActivityById(long id);

    long addActivity(AddActivityDto addActivityDto);

    DayBookingsDto getBookingsForDay(LocalDate date);

    MaxDateDto getMaxTimeForDay(LocalDate date);

    long addBooking(AddBookingDto addBookingDto);

    long addPauseBooking(AddPauseBookingDto addPauseBookingDto);

    void removeBooking(long bookingId);
}
