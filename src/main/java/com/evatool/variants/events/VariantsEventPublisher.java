package com.evatool.variants.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class VariantsEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    final Logger logger = LoggerFactory.getLogger(VariantsEventPublisher.class);

    public void publishEvent(ApplicationEvent applicationEvent){
        logger.info("Publishing Event: "+ applicationEvent.getClass());
        logger.debug(applicationEvent.getClass() + "  Eventpayload: " + applicationEvent.getSource().toString());
        applicationEventPublisher.publishEvent(applicationEvent);
    }

}
