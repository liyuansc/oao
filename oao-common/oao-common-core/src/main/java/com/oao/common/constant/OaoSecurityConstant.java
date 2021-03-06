package com.oao.common.constant;

public class OaoSecurityConstant {
    public final static String SUPER_ADMIN_ID = "1";
    public final static String SUPER_ROLE_ID = "1";


    public static class HttpHeader {
        public final static String ACCESS_TOKEN = "X-ACCESS-TOKEN";

        public final static String I = "INTERNAL-";


        public final static String I_USER = I + "USER";

        public final static String I_USERNAME = I + "USERNAME";
    }

    public static class QueryParam {
        public final static String ACCESS_TOKEN = "access_token";
    }

    public static class GrantType {
        public final static String AUTHORIZATION_CODE = "authorization_code";
        public final static String IMPLICIT = "implicit";
        public final static String REFRESH_TOKEN = "refresh_token";
        public final static String CLIENT_CREDENTIALS = "client_credentials";
        public final static String PASSWORD = "password";
    }
}
