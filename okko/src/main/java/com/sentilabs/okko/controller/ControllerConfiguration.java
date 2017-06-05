package com.sentilabs.okko.controller;

public class ControllerConfiguration {

    public final static String BASE_URL = "/api";

    public final static class Users {
        public final static String USERS_BASE_URL = "/users";
        public final static String USERS_FULL_BASE_URL = BASE_URL + USERS_BASE_URL;

        public final static String SEARCH_BY_EMAIL_REQ_PARAM = "email";

        public final static class UserById {

            public static final String PATH_PARAM_USER_ID = "userId";
        }
    }
}
