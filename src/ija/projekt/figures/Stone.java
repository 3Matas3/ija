package ija.projekt.figures;

import ija.projekt.base.Figure;
import ija.projekt.base.Player;
import ija.projekt.base.Position;

public class Stone extends Figure {

    /**
     *
     * @param position
     * @param player
     */
    public Stone(Position position, Player player) {
        super(position, player);
    }

    /**
     *
     * @param destination
     * @return
     */
    public boolean canMove(Position destination) {
        if (destination.getFigure() != null) {
            return false;
        } else {
            int direction = this.getPosition().getDesk().getPlayer().isWhite() ? 1 : -1;
            if (this.getPosition().nextPosition(1, direction) == destination) {
                return true;
            } else {
                return this.getPosition().nextPosition(-1, direction) == destination;
            }
        }
    }

    /**
     *
     * @return
     */
    public boolean canCapture() {
        int direction = this.getPosition().getDesk().getPlayer().isWhite() ? 1 : -1;
        Position[] destinations = new Position[]{this.getPosition().nextPosition(2, 2 * direction), this.getPosition().nextPosition(-2, 2 * direction)};

        for(int i = 0; i < destinations.length; ++i) {
            if (this.canCapture(destinations[i])) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param destination
     * @return
     */
    public boolean canCapture(Position destination) {
        if (destination == null) {
            return false;
        } else if (destination.getFigure() != null) {
            return false;
        } else {
            int direction = this.getPosition().getDesk().getPlayer().isWhite() ? 1 : -1;

            for(int side = -1; side < 2; side += 2) {
                if (this.getPosition().nextPosition(2 * side, 2 * direction) == destination) {
                    Figure jumpedFig = this.getPosition().nextPosition(1 * side, direction).getFigure();
                    if (jumpedFig == null) {
                        return false;
                    }

                    if (jumpedFig.getPlayer() == this.getPlayer()) {
                        return false;
                    }

                    return true;
                }
            }

            return false;
        }
    }

    /**
     *
     * @param destination
     */
    public void capture(Position destination) {
        Position stoneInWayPos = this.getPosition().getFirstStoneInWay(destination);
        if (stoneInWayPos != null) {
            stoneInWayPos.getFigure().getPlayer().remFigure(stoneInWayPos.getFigure());
            stoneInWayPos.removeFigure();
            stoneInWayPos.updateIcon();
            this.move(destination);
        }
    }
    
    /**
     *
     * @param position
     * @return
     */
    @Override
    public boolean isCheck(Position position){
        return this.canCapture(position) == position.isKing(position);
    }
}
