package at.aetzlstorfer.traxbackend.facades;

import at.aetzlstorfer.traxbackend.facades.converter.ActivityConverter;
import at.aetzlstorfer.traxbackend.facades.converter.BookingConverter;
import at.aetzlstorfer.traxbackend.facades.dto.*;
import at.aetzlstorfer.traxbackend.repository.model.ActivityModel;
import at.aetzlstorfer.traxbackend.repository.model.BookingModel;
import at.aetzlstorfer.traxbackend.services.TrackingService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;


@Component
public class DefaultTrackingFacade implements TrackingFacade {

    private final TrackingService trackingService;
    private final ActivityConverter activityConverter;
    private final BookingConverter bookingConverter;

    public DefaultTrackingFacade(
            TrackingService trackingService,
            ActivityConverter activityConverter,
            BookingConverter bookingConverter
    ) {
        this.trackingService = trackingService;
        this.activityConverter = activityConverter;
        this.bookingConverter = bookingConverter;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ActivityDto> getAllActivities() {
        Iterable<ActivityModel> activities = trackingService.getAllActivities();
        return activityConverter.convertIterable(activities);
    }

    @Transactional(readOnly = true)
    @Override
    public ActivityDto getActivityById(long id) {
        Optional<ActivityModel> activity = trackingService.getActivityById(id);
        return activityConverter.convert(activity);
    }

    @Transactional
    @Override
    public long addActivity(AddActivityDto addActivityDto) {
        ActivityModel activity = new ActivityModel();
        activity.setName(addActivityDto.getName());
        activity.setDescription(addActivityDto.getDescription());
        return trackingService.addActivity(activity);
    }

    @Transactional(readOnly = true)
    @Override
    public DayBookingsDto getBookingsForDay(LocalDate date) {
        Iterable<BookingModel> bookings = trackingService.getBookingsForDay(date);

        List<BookingDto> bookingDtoItems = bookingConverter.convertIterable(bookings);

        long sum = bookingDtoItems
                .stream()
                .filter(item -> item.getActivity() != null)
                .map(item -> Duration.between(item.getFromTime(), item.getToTime()))
                .mapToLong(duration -> duration.get(ChronoUnit.SECONDS))
                .sum();

        DayBookingsDto dayBookingsDto = new DayBookingsDto();
        dayBookingsDto.setBookings(bookingDtoItems);
        dayBookingsDto.setHours(sum / 3600d);

        return dayBookingsDto;
    }

    @Transactional(readOnly = true)
    @Override
    public MaxDateDto getMaxTimeForDay(LocalDate date) {
        LocalTime time = trackingService.getMaxTimeForDay(date);
        MaxDateDto maxDateDto = new MaxDateDto();
        maxDateDto.setTime(time);
        return maxDateDto;
    }

    @Transactional
    @Override
    public long addBooking(AddBookingDto addBookingDto) {
        long activityId = addBookingDto.getActivityId();

        Optional<ActivityModel> activityOptional = trackingService.getActivityById(activityId);
        ActivityModel activity = activityOptional.orElseThrow(() ->
                new EntityNotFoundException("Could not find matching activity for id: " + activityId));

        BookingModel booking = new BookingModel();
        booking.setDay(addBookingDto.getDay());
        booking.setFromTime(addBookingDto.getFromTime());
        booking.setToTime(addBookingDto.getToTime());

        booking.setActivity(activity);

        return trackingService.addBooking(booking);
    }

    @Transactional
    @Override
    public long addPauseBooking(AddPauseBookingDto addPauseBookingDto) {
        MaxDateDto maxTimeForDay = this.getMaxTimeForDay(addPauseBookingDto.getDay());
        LocalTime from = maxTimeForDay.getTime();
        if (from == null) {
            throw new EntityNotFoundException("Could not find other bookings for the day: " + addPauseBookingDto.getDay());
        }

        LocalTime to = from.plusMinutes(addPauseBookingDto.getMinutes());

        BookingModel booking = new BookingModel();
        booking.setDay(addPauseBookingDto.getDay());
        booking.setFromTime(from);
        booking.setToTime(to);

        return trackingService.addBooking(booking);
    }

    @Transactional
    @Override
    public void removeBooking(long bookingId) {
        Optional<BookingModel> bookingOptional = trackingService.getBookingForId(bookingId);
        BookingModel booking = bookingOptional.orElseThrow(
                () -> new IllegalStateException("Could not find booking for id: " + bookingId)
        );
        trackingService.removeBooking(booking);
    }
}
