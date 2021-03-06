package com.evatool.common.exception.functional.tag;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class VariantReferencedByRequirements {

    public final UUID variantId;
    public final UUID[] requirementIds;

}
