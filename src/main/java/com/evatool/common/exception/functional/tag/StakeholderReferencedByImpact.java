package com.evatool.common.exception.functional.tag;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class StakeholderReferencedByImpact {

    public final UUID stakeholderId;
    public final UUID[] impactIds;

}
