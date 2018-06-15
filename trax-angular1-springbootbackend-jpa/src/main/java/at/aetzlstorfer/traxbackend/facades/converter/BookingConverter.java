package at.aetzlstorfer.traxbackend.facades.converter;

import at.aetzlstorfer.traxbackend.facades.dto.ActivityDto;
import at.aetzlstorfer.traxbackend.facades.dto.BookingDto;
import at.aetzlstorfer.traxbackend.repository.model.BookingModel;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class BookingConverter implements Converter<BookingModel, BookingDto> {

    private final ActivityConverter activityConverter;

    public BookingConverter(ActivityConverter activityConverter) {
        this.activityConverter = activityConverter;
    }

    @Override
    public BookingDto convert(BookingModel source) {

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(source.getId());
        bookingDto.setDay(source.getDay());
        bookingDto.setFromTime(source.getFromTime());
        bookingDto.setToTime(source.getToTime());

        ActivityDto activityDto = activityConverter.convert(Optional.ofNullable(source.getActivity()));
        bookingDto.setActivity(activityDto);

        return bookingDto;
    }
}
