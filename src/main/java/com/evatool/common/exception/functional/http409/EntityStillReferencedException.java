package com.evatool.common.exception.functional.http409;

import com.evatool.common.exception.functional.FunctionalException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

public class EntityStillReferencedException extends FunctionalException {
    public EntityStillReferencedException(String message, int functionalErrorCode, Object tag) {
        super(message, functionalErrorCode, tag);
    }

    @Getter
    @RequiredArgsConstructor
    public static class ImpactReferencedByRequirementsTag {
        public final UUID impactId;
        public final UUID[] requirementIds;
        public final UUID[] requirementDeltaIds;
    }

    @Getter
    @RequiredArgsConstructor
    public static class StakeholderReferencedByImpactsTag {
        public final UUID stakeholderId;
        public final UUID[] impactIds;
    }

    @Getter
    @RequiredArgsConstructor
    public static class ValueReferencedByImpactsTag {
        public final UUID valueId;
        public final UUID[] impactIds;
    }

    @Getter
    @RequiredArgsConstructor
    public static class VariantReferencedByRequirementsTag {
        public final UUID variantId;
        public final UUID[] requirementIds;
    }
}
