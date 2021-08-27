package com.evatool.common.exception.functional.http404;

import com.evatool.common.exception.functional.Tag;
import com.evatool.common.util.FunctionalErrorCodesUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UsernameNotFoundException extends NotFoundException {
    public UsernameNotFoundException(String username) {
        super("Username \"" + username + "\" not found", FunctionalErrorCodesUtil.LOGIN_USERNAME_NOT_FOUND, new UsernameNotFoundTag(username));
    }

    @Getter
    @RequiredArgsConstructor
    public static class UsernameNotFoundTag extends Tag {
        public final String username;
    }
}
