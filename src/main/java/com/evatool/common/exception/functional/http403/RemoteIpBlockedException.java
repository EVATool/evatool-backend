package com.evatool.common.exception.functional.http403;

import com.evatool.common.util.FunctionalErrorCodesUtil;

public class RemoteIpBlockedException extends ForbiddenException {
    public RemoteIpBlockedException() {
        super("The ip address is blocked from further login attempts", FunctionalErrorCodesUtil.REMOTE_IP_BLOCKED, null);
    }
}
