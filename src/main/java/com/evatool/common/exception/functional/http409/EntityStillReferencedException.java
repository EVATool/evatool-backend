package com.evatool.common.exception.functional.http409;

import com.evatool.common.exception.functional.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

public class EntityStillReferencedException extends ConflictException {
    public EntityStillReferencedException(String message, int functionalErrorCode, EntityStillReferencedExceptionTag tag) {
        super(message, functionalErrorCode, tag);
    }

    public abstract static class EntityStillReferencedExceptionTag extends Tag {

    }

    @Getter
    @RequiredArgsConstructor
    public static class ImpactReferencedByRequirementsTag extends EntityStillReferencedExceptionTag {
        public final UUID impactId;
        public final UUID[] requirementIds;
        public final UUID[] requirementDeltaIds;
    }

    @Getter
    @RequiredArgsConstructor
    public static class StakeholderReferencedByImpactsTag extends EntityStillReferencedExceptionTag {
        public final UUID stakeholderId;
        public final UUID[] impactIds;
    }

    @Getter
    @RequiredArgsConstructor
    public static class ValueReferencedByImpactsTag extends EntityStillReferencedExceptionTag {
        public final UUID valueId;
        public final UUID[] impactIds;
    }

    @Getter
    @RequiredArgsConstructor
    public static class VariantReferencedByRequirementsTag extends EntityStillReferencedExceptionTag {
        public final UUID variantId;
        public final UUID[] requirementIds;
    }
}
