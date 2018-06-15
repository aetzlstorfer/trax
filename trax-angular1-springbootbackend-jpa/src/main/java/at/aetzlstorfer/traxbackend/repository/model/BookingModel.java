package at.aetzlstorfer.traxbackend.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Getter
@Setter
public class BookingModel {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private ActivityModel activity;

    private LocalDate day;

    private LocalTime fromTime;

    private LocalTime toTime;
}
