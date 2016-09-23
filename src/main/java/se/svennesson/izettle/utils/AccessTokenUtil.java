package se.svennesson.izettle.utils;

import se.svennesson.izettle.models.AccessToken;

import java.time.LocalDateTime;

public class AccessTokenUtil {

    public static boolean isAccessTokenValid(final AccessToken accessToken) {
        return !LocalDateTime.now().isAfter(accessToken.getDistributedTime().plusMonths(1));
    }
}
