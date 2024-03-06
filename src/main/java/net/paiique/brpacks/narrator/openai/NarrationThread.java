package net.paiique.brpacks.narrator.openai;

public class NarrationThread extends Thread {
    @Override
    public void run() {
        new NarrationGenerator().generate();
    }
}
