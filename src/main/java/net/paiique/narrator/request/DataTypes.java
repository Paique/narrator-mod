package net.paiique.narrator.request;

import lombok.Getter;

public class DataTypes {

    @Getter
    public static enum Request {
        POST("POST"), GET("GET");

        private final String requestType;

        Request(String requestType) {
            this.requestType = requestType;
        }

    }

    @Getter
    public static enum Response {
        JSON("application/json"), HTML("text/html"), PLAIN("text/plain");


        private final String contentType;

        Response(String contentType) {
            this.contentType = contentType;
        }
    }

    public static enum Output {
        VOICE, TEXT;
    }

}