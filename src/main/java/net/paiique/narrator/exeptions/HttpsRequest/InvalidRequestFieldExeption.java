package net.paiique.narrator.exeptions.HttpsRequest;

public class InvalidRequestFieldExeption extends Exception {
    public InvalidRequestFieldExeption() {}

    public InvalidRequestFieldExeption(String message) {
        super(message);
    }
}
