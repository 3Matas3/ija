package ija.projekt.base;

import ija.projekt.figures.FigureType;
import ija.projekt.figures.King;
import java.util.ArrayList;
import java.util.Iterator;

public class Player {
    private final Player.Color color;
    private Player opponent;
    private Player.Type type;
    private final ArrayList<Figure> figures;

    /**
     *
     * @param p
     */
    public void setOpponent(Player p) {
        this.opponent = p;
    }

    /**
     *
     * @return
     */
    public Player opponent() {
        return this.opponent;
    }

    /**
     *
     * @param color
     */
    public Player(Player.Color color) {
        this.color = color;
        this.figures = new ArrayList<Figure>();
    }

    /**
     *
     * @return
     */
    public boolean isWhite() {
        return this.color == Player.Color.WHITE;
    }

    /**
     *
     * @return
     */
    public boolean isBlack() {
        return this.color == Player.Color.BLACK;
    }

    /**
     *
     * @param newtype
     */
    public void settype(Player.Type newtype) {
        this.type = newtype;
    }

    /**
     *
     * @return
     */
    public Player.Type type() {
        return this.type;
    }

    /**
     *
     * @param newfig
     */
    public void addFigure(Figure newfig) {
        this.figures.add(newfig);
    }

    /**
     *
     * @param delfig
     */
    public void remFigure(Figure delfig) {
        this.figures.remove(delfig);
    }

    /**
     *
     */
    public void purgeFigures() {
        this.figures.removeAll(this.figures);
    }

    /**
     *
     * @return
     */
    public Integer cntFigures() {
        return this.figures.size();
    }

    /**
     *
     * @return
     */
    public ArrayList<Figure> getFigures() {
        return this.figures;
    }

    /**
     *
     * @return
     */
    public boolean isBlocked() {
        Iterator<Figure> var1 = this.figures.iterator();

        while(var1.hasNext()) {
            Figure fig = var1.next();
            if (fig.canCapture()) {
                return false;
            }

            for(int i = 0; i < 8; ++i) {
                for(int j = 0; j < 8; ++j) {
                    Position p = fig.getPosition().getDesk().getPositionAt(i, j);
                    if (fig.canMove(p)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     *
     * @return
     */
    public boolean loses() {
        if(this.figures.contains(FigureType.KING)){
            return true;
        }
        else if(this.isBlocked()){
            return this.isBlocked();
        } 
        else return false;
    }

    /**
     *
     * @return
     */
    public Figure getCaptureFigure() {
        Iterator<Figure> var1 = this.figures.iterator();

        Figure fig;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            fig = var1.next();
        } while(!fig.canCapture());

        return fig;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Player) {
            Player other = (Player)o;
            return this.color == other.color;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.isWhite() ? 1 : 0;
    }

    /**
     *
     */
    public static enum Type {

        /**
         *
         */
        HUMAN,

        /**
         *
         */
        REMOTE,

        /**
         *
         */
        AI;

        private Type() {
        }
    }

    /**
     *
     */
    public static enum Color {

        /**
         *
         */
        BLACK,

        /**
         *
         */
        WHITE;

        private Color() {
        }
    }
}
