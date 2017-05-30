package com.mycompany.fakedevice;


/**
 * Spustacia trieda programu
 * @author Chalani
 */
public class Main {

    /**
     * Spustacia metoda programu
     * Zobrazenie GUI
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String args[]) throws InterruptedException {
        Main main = new Main();

        Gui gui = new Gui();
        gui.setLocationRelativeTo(null);
        gui.setTitle("Fake device");
        gui.setVisible(true);

    }
}
