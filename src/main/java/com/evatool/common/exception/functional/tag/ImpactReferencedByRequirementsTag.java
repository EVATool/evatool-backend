package com.evatool.common.exception.functional.tag;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;


@Getter
@RequiredArgsConstructor
public class ImpactReferencedByRequirementsTag {

    public final UUID impactId;
    public final UUID[] requirementIds;
    public final UUID[] requirementDeltaIds;

}
