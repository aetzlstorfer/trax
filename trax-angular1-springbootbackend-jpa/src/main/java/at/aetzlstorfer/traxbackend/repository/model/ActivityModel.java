package at.aetzlstorfer.traxbackend.repository.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class ActivityModel {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String description;
}
