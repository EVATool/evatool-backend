package com.evatool.common.config;


import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.method.AbstractFallbackMethodSecurityMetadataSource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.security.access.annotation.Jsr250SecurityConfig.DENY_ALL_ATTRIBUTE;

// This class denies all requests if a method of a rest controller is not annotated with auth annotations at all.
public class CustomPermissionAllowedMethodSecurityMetadataSource extends AbstractFallbackMethodSecurityMetadataSource {
    @Override
    protected Collection findAttributes(Class<?> clazz) {
        return null;
    }

    @Override
    protected Collection findAttributes(Method method, Class<?> targetClass) {
        Annotation[] annotations = AnnotationUtils.getAnnotations(method);
        List attributes = new ArrayList<>();

        // if the class is annotated as @Controller we should by default deny access to all methods
        if (AnnotationUtils.findAnnotation(targetClass, Controller.class) != null) {
            attributes.add(DENY_ALL_ATTRIBUTE);
        }

        if (annotations != null) {
            for (Annotation a : annotations) {
                // but not if the method has at least a PreAuthorize or PostAuthorize annotation
                if (a instanceof PreAuthorize || a instanceof PostAuthorize) {
                    return null;
                }
            }
        }
        return attributes;
    }

    @Override
    public Collection getAllConfigAttributes() {
        return null;
    }
}
