package ija.projekt.base;

public abstract class Figure {
    private Position position;
    private final Player player;

    /**
     *
     * @param position
     * @param player
     */
    public Figure(Position position, Player player) {
        this.position = position;
        this.player = player;
        player.addFigure(this);
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
    public Position getPosition() {
        return this.position;
    }

    /**
     *
     * @param p
     * @return
     */
    public boolean isAtPosition(Position p) {
        return this.position.equals(p);
    }

    /**
     *
     * @param destination
     */
    public void move(Position destination) {
        this.position.removeFigure();
        this.position.updateIcon();
        destination.placeFigure(this);
        destination.updateIcon();
        this.position = destination;
     //  destination.tryChangeStoneToQueen();
    }

    /**
     *
     * @return
     */
    public boolean canMove() {
        for(int x = 0; x < 8; ++x) {
            for(int y = 0; y < 8; ++y) {
                if (this.canMove(this.position.getDesk().getPositionAt(x, y))) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     *
     * @param var1
     * @return
     */
    public abstract boolean canMove(Position var1);

    /**
     *
     * @return
     */
    public abstract boolean canCapture();

    /**
     *
     * @param var1
     * @return
     */
    public abstract boolean canCapture(Position var1);

    /**
     *
     * @param var1
     */
    public abstract void capture(Position var1);
    
    /**
     *
     * @param position
     * @return
     */
    public abstract boolean isCheck(Position position);
}
