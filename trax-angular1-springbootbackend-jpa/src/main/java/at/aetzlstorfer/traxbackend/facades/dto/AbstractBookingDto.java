package at.aetzlstorfer.traxbackend.facades.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
abstract class AbstractBookingDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate day;

}
