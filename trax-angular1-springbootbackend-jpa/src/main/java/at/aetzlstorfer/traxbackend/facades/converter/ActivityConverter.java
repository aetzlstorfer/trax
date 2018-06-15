package at.aetzlstorfer.traxbackend.facades.converter;

import at.aetzlstorfer.traxbackend.facades.dto.ActivityDto;
import at.aetzlstorfer.traxbackend.repository.model.ActivityModel;
import org.springframework.stereotype.Component;


@Component
public class ActivityConverter implements Converter<ActivityModel, ActivityDto> {

    @Override
    public ActivityDto convert(ActivityModel source) {
        ActivityDto activityDto = new ActivityDto();
        activityDto.setId(source.getId());
        activityDto.setName(source.getName());
        activityDto.setDescription(source.getDescription());
        return activityDto;
    }

}
