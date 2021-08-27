package com.evatool.common.exception.functional.tag;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class VariantReferencedByRequirementsTag {

    public final UUID variantId;
    public final UUID[] requirementIds;

}
