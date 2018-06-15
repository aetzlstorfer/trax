package at.aetzlstorfer.traxbackend.repository;

import at.aetzlstorfer.traxbackend.repository.model.BookingModel;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface BookingRepository extends CrudRepository<BookingModel, Long> {

    Iterable<BookingModel> findByDay(LocalDate day);

    Optional<BookingModel> findTopByDayOrderByToTimeDesc(LocalDate day);
}
