package at.aetzlstorfer.traxbackend.services;

import at.aetzlstorfer.traxbackend.repository.ActivityRepository;
import at.aetzlstorfer.traxbackend.repository.BookingRepository;
import at.aetzlstorfer.traxbackend.repository.model.ActivityModel;
import at.aetzlstorfer.traxbackend.repository.model.BookingModel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;


@Service
public class DefaultTrackingService implements TrackingService {

    private final ActivityRepository activityRepository;
    private final BookingRepository bookingRepository;

    public DefaultTrackingService(
            ActivityRepository activityRepository,
            BookingRepository bookingRepository) {
        this.activityRepository = activityRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Iterable<ActivityModel> getAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public Optional<ActivityModel> getActivityById(long id) {
        return activityRepository.findById(id);
    }

    @Override
    public long addActivity(ActivityModel activity) {
        ActivityModel savedActivity = activityRepository.save(activity);
        return savedActivity.getId();
    }

    @Override
    public Iterable<BookingModel> getBookingsForDay(LocalDate day) {
        return bookingRepository.findByDay(day);
    }

    @Override
    public LocalTime getMaxTimeForDay(LocalDate date) {
        Optional<BookingModel> top = bookingRepository.findTopByDayOrderByToTimeDesc(date);
        return top.map(BookingModel::getToTime).orElse(null);
    }

    @Override
    public long addBooking(BookingModel booking) {
        BookingModel savedBooking = bookingRepository.save(booking);
        return savedBooking.getId();
    }

    @Override
    public Optional<BookingModel> getBookingForId(long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public void removeBooking(BookingModel booking) {
        bookingRepository.delete(booking);
    }
}
