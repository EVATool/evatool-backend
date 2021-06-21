package com.evatool.common.exception.functional.tag;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class ValueReferencedByImpacts {

    public final UUID valueId;
    public final UUID[] impactIds;

}
