package ija.projekt.base;

import ija.projekt.gui.GameWindow;
import java.applet.Applet;

@SuppressWarnings("serial")
public class Game extends Applet {
    private static GameWindow gw;

    /**
     *
     */
    public Game() {
    }

    @Override
    public void init() {
        gw = new GameWindow();
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        gw = new GameWindow();
        gw.setVisible(true);
    }

    /**
     *
     * @return
     */
    public static GameWindow getWindow() {
        return gw;
    }
}
