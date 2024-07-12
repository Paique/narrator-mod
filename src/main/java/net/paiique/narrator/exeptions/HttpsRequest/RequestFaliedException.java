package net.paiique.narrator.exeptions.HttpsRequest;

public class RequestFaliedException extends Exception {
    public RequestFaliedException() {}

    public RequestFaliedException(String message) {
        super(message);
    }
}
