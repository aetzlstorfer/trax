package at.aetzlstorfer.traxbackend.services;

import at.aetzlstorfer.traxbackend.repository.model.ActivityModel;
import at.aetzlstorfer.traxbackend.repository.model.BookingModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;


public interface TrackingService {
    Iterable<ActivityModel> getAllActivities();

    Optional<ActivityModel> getActivityById(long id);

    long addActivity(ActivityModel activity);

    Iterable<BookingModel> getBookingsForDay(LocalDate date);

    LocalTime getMaxTimeForDay(LocalDate date);

    long addBooking(BookingModel booking);

    Optional<BookingModel> getBookingForId(long id);

    void removeBooking(BookingModel booking);
}
