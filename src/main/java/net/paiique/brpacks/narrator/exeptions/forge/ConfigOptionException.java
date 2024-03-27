package net.paiique.brpacks.narrator.exeptions.forge;

public class ConfigOptionException extends Exception {
    public ConfigOptionException() {
        super("Error while loading config file.");
    }

    public ConfigOptionException(String cause) {
        super("Cause: " + cause);
    }
}
