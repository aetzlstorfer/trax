package at.aetzlstorfer.traxbackend.facades.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DayBookingsDto {

    private List<BookingDto> bookings;

    private double hours;
}
