package ija.projekt.base;

import ija.projekt.base.Player.Color;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Desk extends JPanel {
    private final Position[][] positions;
    private Position selected = null;
    private final Player playerWhite;
    private final Player playerBlack;
    private Player player;
    private History history;
    private boolean gameEnded = false;

    /**
     *
     */
    public Desk() {
        this.playerWhite = new Player(Color.WHITE);
        this.playerBlack = new Player(Color.BLACK);
        this.playerWhite.setOpponent(this.playerBlack);
        this.playerBlack.setOpponent(this.playerWhite);
        this.positions = new Position[8][8];

        for(int y = 7; y >= 0; --y) {
            for(int x = 0; x <= 7; ++x) {
                this.positions[x][y] = new Position(this, x, y);
                this.add(this.positions[x][y]);
            }
        }

    }

    /**
     *
     * @param history
     */
    public void setHistory(History history) {
        assert this.history == null : "Metoda setHistory(History) sa neda volat opakovane";

        this.history = history;
    }

    /**
     *
     * @return
     */
    public History getHistory() {
        return this.history;
    }

    /**
     *
     */
    public void newGame() {
        this.player = this.playerWhite;
        this.resetDesk();
        this.updateAllBackgrounds();
        this.history.clearItems();
    }

    /**
     *
     */
    public void endGame() {
        this.gameEnded = true;
    }

    /**
     *
     * @return
     */
    public boolean isGameEnded() {
        return this.gameEnded;
    }

    /**
     *
     */
    public void resetDesk() {
        this.getWhitePlayer().purgeFigures();
        this.getBlackPlayer().purgeFigures();
        this.selected = null;

        for(int x = 0; x < 8; ++x) {
            for(int y = 0; y < 8; ++y) {
                this.positions[x][y].resetPosition();
            }
        }

        this.history.deskWasResetted();
    }

    /**
     *
     */
    public void nextPlayer() {
        this.player = this.player.opponent();
        this.updateAllBackgrounds();
    }

    /**
     *
     * @return
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     *
     * @return
     */
    public Player getWhitePlayer() {
        return this.playerWhite;
    }

    /**
     *
     * @return
     */
    public Player getBlackPlayer() {
        return this.playerBlack;
    }

    /**
     *
     * @return
     */
    public Position selected() {
        return this.selected;
    }

    /**
     *
     * @param position
     */
    public void select(Position position) {
        this.selected = position;
    }

    /**
     *
     */
    public void clearSelected() {
        this.selected = null;
        this.updateAllBackgrounds();
    }

    /**
     *
     */
    public void updateAllBackgrounds() {
        for(int x = 0; x < 8; ++x) {
            for(int y = 0; y < 8; ++y) {
                this.positions[x][y].updateBackground();
            }
        }

    }

    private boolean isAtDesk(int column, int row) {
        return column >= 0 && column < 8 && row >= 0 && row < 8;
    }

    /**
     *
     * @param column
     * @param row
     * @return
     */
    public Position getPositionAt(int column, int row) {
        return this.isAtDesk(column, row) ? this.positions[column][row] : null;
    }
}
