package com.evatool.common.exception.functional.http409;

import com.evatool.common.exception.functional.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

public class EntityStillReferencedException extends ConflictException {
    public EntityStillReferencedException(String message, int functionalErrorCode, EntityStillReferencedTag tag) {
        super(message, functionalErrorCode, tag);
    }

    public abstract static class EntityStillReferencedTag extends Tag {

    }

    @Getter
    @RequiredArgsConstructor
    public static class ImpactReferencedByRequirementsTag extends EntityStillReferencedTag {
        public final UUID impactId;
        public final UUID[] requirementIds;
        public final UUID[] requirementDeltaIds;
    }

    @Getter
    @RequiredArgsConstructor
    public static class StakeholderReferencedByImpactsTag extends EntityStillReferencedTag {
        public final UUID stakeholderId;
        public final UUID[] impactIds;
    }

    @Getter
    @RequiredArgsConstructor
    public static class ValueReferencedByImpactsTag extends EntityStillReferencedTag {
        public final UUID valueId;
        public final UUID[] impactIds;
    }

    @Getter
    @RequiredArgsConstructor
    public static class VariantReferencedByRequirementsTag extends EntityStillReferencedTag {
        public final UUID variantId;
        public final UUID[] requirementIds;
    }
}
