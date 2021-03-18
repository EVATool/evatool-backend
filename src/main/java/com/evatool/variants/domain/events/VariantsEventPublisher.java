package com.evatool.variants.domain.events;

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
        if(logger.isInfoEnabled())logger.info(String.format("Publishing Event: %s" , applicationEvent.getClass()));
        if(logger.isDebugEnabled())logger.debug(String.format("EVENT: %s  Eventpayload:  %s", applicationEvent.getClass() , applicationEvent.getSource().toString() ));
        applicationEventPublisher.publishEvent(applicationEvent);
    }

}
