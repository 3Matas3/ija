//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package ija.projekt.base;

import ija.projekt.figures.Queen;
import ija.projekt.figures.Bishop;
import ija.projekt.figures.King;
import ija.projekt.figures.Knight;
import ija.projekt.figures.Pawn;
import ija.projekt.figures.Rook;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.Border;


@SuppressWarnings("serial")
public final class Position extends JButton {
    private static ImageIcon blackIconPawn = new ImageIcon(Position.class.getResource("/ija/projekt/images/blackPawn.png"));
    private static ImageIcon blackIconKnight = new ImageIcon(Position.class.getResource("/ija/projekt/images/blackKnight.png"));
    private static ImageIcon blackIconKing = new ImageIcon(Position.class.getResource("/ija/projekt/images/blackKing.png"));
    private static ImageIcon blackIconBishop = new ImageIcon(Position.class.getResource("/ija/projekt/images/blackBishop.png"));
    private static ImageIcon blackIconRook = new ImageIcon(Position.class.getResource("/ija/projekt/images/blackRook.png"));
    private static ImageIcon blackIconQueen = new ImageIcon(Position.class.getResource("/ija/projekt/images/blackQueen.png"));
    private static ImageIcon whiteIconPawn = new ImageIcon(Position.class.getResource("/ija/projekt/images/whitePawn.png"));
    private static ImageIcon whiteIconRook = new ImageIcon(Position.class.getResource("/ija/projekt/images/whiteRook.png"));
    private static ImageIcon whiteIconKnight = new ImageIcon(Position.class.getResource("/ija/projekt/images/whiteKnight.png"));
    private static ImageIcon whiteIconBishop = new ImageIcon(Position.class.getResource("/ija/projekt/images/whiteBishop.png"));
    private static ImageIcon whiteIconQueen = new ImageIcon(Position.class.getResource("/ija/projekt/images/whiteQueen.png"));
    private static ImageIcon whiteIconKing = new ImageIcon(Position.class.getResource("/ija/projekt/images/whiteKing.png"));

    /**
     *
     */
    public int x;

    /**
     *
     */
    public int y;
    private final Desk desk;
    private Figure figure;

    /**
     *
     * @param desk
     * @param x
     * @param y
     */
    public Position(Desk desk, int x, int y) {
        this.desk = desk;
        this.x = x;
        this.y = y;
        this.figure = null;
        this.addActionListener((ActionEvent evt) -> {
            ((Position)evt.getSource()).onClick();
        });
        this.setFocusPainted(false);
        this.setBorder((Border)null);
    }

    /**
     *
     * @return
     */
    public boolean isPositionBlack() {
        return (this.x + this.y) % 2 == 0;
    }

    /**
     *
     */
    public void updateBackground() {
        if (this.desk.selected() == this) {
            this.setBackground(Color.green);
        } else if (this.isPositionBlack()) {
            this.setBackground(Color.gray);
        } else {
            this.setBackground(Color.white);
        }
    }

    /**
     *
     * @param figure
     */
    public void placeFigure(Figure figure) {
        this.figure = figure;
        this.updateIcon();
    }

    /**
     *
     * @return
     */
    public Figure getFigure() {
        return this.figure;
    }

    /**
     *
     * @return
     */
    public Figure removeFigure() {
        Figure old = this.figure;
        this.figure = null;
        return old;
    }

    /**
     *
     */
    public void updateIcon() {
        if (this.figure == null) {
            this.setIcon((Icon)null);
        } else if(this.figure instanceof Pawn){
            if(this.figure.getPlayer().isBlack())
                this.setIcon(blackIconPawn);
            else
                this.setIcon(whiteIconPawn);
        }else if (this.figure instanceof Queen) {
            if (this.figure.getPlayer().isBlack()) {
                this.setIcon(blackIconQueen);
            } else {
                this.setIcon(whiteIconQueen);
            }
        } else if (this.figure instanceof Bishop) {
            if (this.figure.getPlayer().isBlack()) {
                this.setIcon(blackIconBishop);
            } else {
                this.setIcon(whiteIconBishop);
            }
        } else if(this.figure instanceof Rook){
            if(this.figure.getPlayer().isBlack()){
                this.setIcon(blackIconRook);
            }else{
                this.setIcon(whiteIconRook);
            }
        } else if(this.figure instanceof Knight){
            if(this.figure.getPlayer().isBlack()){
                this.setIcon(blackIconKnight);
            }else{
                this.setIcon(whiteIconKnight);
            }
        } else if(this.figure instanceof Queen){
            if(this.figure.getPlayer().isBlack()){
                this.setIcon(blackIconQueen);
            }else{
                this.setIcon(whiteIconQueen);
            }
        } else if(this.figure instanceof King){
            if(this.figure.getPlayer().isBlack()){
                this.setIcon(blackIconKing);
            }else{
                this.setIcon(whiteIconKing);
            }
        }
    }

    /**
     *
     */
    public void select() {
        if (this.figure != null && this.figure.getPlayer() == this.desk.getPlayer()) {
            this.desk.select(this);
        } else {
            this.desk.select((Position)null);
        }

        
        this.desk.updateAllBackgrounds();
    }

    /**
     *
     */
    public void onClick() {
        if (this.desk.isGameEnded()) {
            JOptionPane.showMessageDialog(Game.getWindow(), "Hra uz skoncila.\nPre novu hru zvolte Hra -> Nová Hra.", "Nemozno hrat", 1);
            System.err.println("Hra uz skončila!");
        } else if (!this.desk.getHistory().inPresent()) {
            JOptionPane.showMessageDialog(Game.getWindow(), "nie je mozne menit historiu.", "Jste včerejší", 1);
            System.err.println("Uživatel nemoze hrat ak sa pohybuje v historii");
        } else {
            if (this.desk.selected() == null && this.getFigure() != null) {
                    this.select();
            } else if (this.desk.selected() != null && this.desk.selected().getFigure() != null) {
                Figure selFigure = this.desk.selected().getFigure();
                if (selFigure.canCapture(this)) {
                    this.desk.selected().getFigure().capture(this);
                    this.desk.getHistory().addCapture(this.desk.selected(), this);
                    this.desk.clearSelected();
                    this.desk.nextPlayer();                        
                }
             
                else if (this.desk.selected().getFigure().canMove(this)) {
                    this.desk.selected().getFigure().move(this);
                    this.desk.getHistory().addMove(this.desk.selected(), this);
                    this.desk.clearSelected();
                    this.desk.nextPlayer();
                }   
                this.desk.clearSelected();
                if (!this.desk.isGameEnded() && this.getDesk().getWhitePlayer().loses() && this.desk.getPlayer().isWhite()) {
                    this.getDesk().endGame();
                    JOptionPane.showMessageDialog(Game.getWindow(), "Vyhrava cierny", "Koniec hry", 1);
                }
                if (!this.desk.isGameEnded() && this.getDesk().getBlackPlayer().loses() && this.getDesk().getHistory().getCurrent() != -1 && this.desk.getPlayer().isBlack()) {
                    System.out.println("History: " + this.getDesk().getHistory().getCurrent());
                    this.getDesk().endGame();
                    JOptionPane.showMessageDialog(Game.getWindow(), "Vyhrava biely", "Koniec hry", 1);
                }
            }
        }
    }

    public String toString() {
        char col = (char)(97 + this.x);
        int row = this.y + 1;
        return col + Integer.toString(row);
    }

    /**
     *
     */
    public void resetPosition() {
        
            if (this.y == 1) {
                this.figure = new Pawn(this, this.desk.getWhitePlayer());
            } else if (this.y == 6) {
                this.figure = new Pawn(this, this.desk.getBlackPlayer());
            } else if((this.x == 0 && this.y ==0) || (this.x == 7 && this.y == 0)){
                this.figure = new Rook(this, this.desk.getWhitePlayer());
            } else if((this.x == 0 && this.y ==7) || (this.x == 7 && this.y == 7)){
                this.figure = new Rook(this, this.desk.getBlackPlayer());
            } else if ((this.x == 1 && this.y ==0) || (this.x == 6 && this.y == 0)){
                this.figure = new Knight(this, this.desk.getWhitePlayer());
            } else if((this.x == 1 && this.y == 7) || (this.x == 6 &&this.y == 7)){
                this.figure = new Knight(this, this.desk.getBlackPlayer());
            } else if((this.x == 2 && this.y == 0) || (this.x == 5 && this.y == 0)){
                this.figure = new Bishop(this, this.desk.getWhitePlayer());
            } else if((this.x == 2 && this.y == 7) || (this.x == 5 &&this.y == 7)){
                this.figure = new Bishop(this, this.desk.getBlackPlayer());
            }else if(this.x == 3 && this.y == 0){
                this.figure = new Queen(this, this.desk.getWhitePlayer());
            }else if(this.x == 3 && this.y == 7){
                this.figure = new Queen(this, this.desk.getBlackPlayer());
            }else if(this.x == 4 && this.y == 0){
                this.figure = new King(this, this.desk.getWhitePlayer());
            }else if(this.x == 4 && this.y == 7){
                this.figure = new King(this, this.desk.getBlackPlayer());
            }
        
        

        this.updateIcon();
        this.updateBackground();
    }

    /**
     *
     * @return
     */
    public Desk getDesk() {
        return this.desk;
    }

    /**
     *
     * @return
     */
    public byte getColumn() {
        return (byte)this.x;
    }

    /**
     *
     * @return
     */
    public byte getRow() {
        return (byte)this.y;
    }

    /**
     *
     * @param dx
     * @param dy
     * @return
     */
    public Position nextPosition(int dx, int dy) {
        return this.desk.getPositionAt(this.x + dx, this.y + dy);
    }

    /**
     *
     * @param p
     * @return
     */
    public boolean isDiagonalOf(Position p) {
        return Math.abs(p.x - this.x) == Math.abs(p.y - this.y);
    }
    
    /**
     *
     * @param p
     * @return
     */
    public boolean isStraightOf(Position p){
        return (this.x == p.x || this.y == p.y);
    }

    /**
     *
     * @param destination
     * @return
     */
    public Position getFirstStoneInWay(Position destination) {
        if ((this.isDiagonalOf(destination))|| (this.isStraightOf(destination))) {
            return null;
        } else if (this.isDiagonalOf(destination)){
            int xinc = this.x < destination.x ? 1 : -1;
            int yinc = this.y < destination.y ? 1 : -1;
            Position curr = this;

            Figure figureOfCurrent;
            do {
                if ((curr = curr.nextPosition(xinc, yinc)) == destination) {
                    return null;
                }

                figureOfCurrent = curr.getFigure();
            } while(figureOfCurrent == null);

            return figureOfCurrent.getPosition();
        } else{
            int xinc;
            int yinc;
            if(this.x < destination.x){
                xinc = 1;
            } else if(this.x > destination.x){
                xinc = -1;
            } else{
                xinc = 0;
            }
            if(this.y < destination.y){
                yinc = 1;
            }else if (this.y > destination.y){
                yinc = -1;
            } else {
                yinc = 0;
            }
            Position curr = this;

            Figure figureOfCurrent;
            do {
                if ((curr = curr.nextPosition(xinc, yinc)) == destination) {
                    return null;
                }

                figureOfCurrent = curr.getFigure();
            } while(figureOfCurrent == null);

            return figureOfCurrent.getPosition();
            
        }
    }
    
    /**
     *
     * @param position
     * @return
     */
    public boolean isKing(Position position){
        return position.figure instanceof King;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position)) {
            return false;
        } else {
            Position p = (Position)o;
            return p.y == this.y && p.x == this.x;
        }
    }

    @Override
    public int hashCode() {
        return this.x + this.y * 10;
    }
   
}
