package com.evatool.impact.domain.event.dimension;

import com.evatool.global.event.dimension.DimensionDeletedEvent;
import com.evatool.impact.application.json.mapper.DimensionJsonMapper;
import com.evatool.impact.domain.entity.Dimension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DimensionDeletedEventPublisher {

    private ApplicationEventPublisher applicationEventPublisher;

    public DimensionDeletedEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void onDimensionDeleted(final Dimension dimension) {
        var dimensionJson = DimensionJsonMapper.toJson(dimension);
        var dimensionDeletedEvent = new DimensionDeletedEvent(this, dimensionJson.toString());
        applicationEventPublisher.publishEvent(dimensionDeletedEvent);
    }
}
