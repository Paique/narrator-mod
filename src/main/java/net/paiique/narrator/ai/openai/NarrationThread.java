package net.paiique.narrator.ai.openai;

public class NarrationThread extends Thread {
    @Override
    public void run() {
        new NarrationGenerator().init();
    }
}
