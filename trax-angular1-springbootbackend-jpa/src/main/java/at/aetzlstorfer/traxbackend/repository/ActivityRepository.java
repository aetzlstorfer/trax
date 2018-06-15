package at.aetzlstorfer.traxbackend.repository;

import at.aetzlstorfer.traxbackend.repository.model.ActivityModel;
import org.springframework.data.repository.CrudRepository;

public interface ActivityRepository extends CrudRepository<ActivityModel, Long> {

}
