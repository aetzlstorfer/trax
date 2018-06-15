package at.aetzlstorfer.traxbackend.facades.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddPauseBookingDto extends AbstractBookingDto {

    private int minutes;

}
