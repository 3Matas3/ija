package ija.projekt.figures;

import ija.projekt.base.Figure;
import ija.projekt.base.Player;
import ija.projekt.base.Position;

public class Knight extends Figure {

    /**
     *
     * @param position
     * @param player
     */
    public Knight(Position position, Player player) {
        super(position, player);
    }

    /**
     *
     * @param destination
     * @return
     */
    public boolean canMove(Position destination) {
        if(destination.getFigure() != null){
            return false;
        } else if (this.getPosition().nextPosition(1, 2) == destination) {
            return true;
        }else if (this.getPosition().nextPosition(-1, 2) == destination) {
            return true;
        }else if (this.getPosition().nextPosition(1, -2) == destination) {
            return true;
        }else if (this.getPosition().nextPosition(-1, -2) == destination) {
            return true;
        }else if (this.getPosition().nextPosition(2, 1) == destination) {
            return true;
        }else if (this.getPosition().nextPosition(-2, -1) == destination) {
            return true;
        }else if (this.getPosition().nextPosition(-2, 1) == destination) {
            return true;
        }else if (this.getPosition().nextPosition(2, -1) == destination) {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public boolean canCapture() {
        Position[] destinations = new Position[]{this.getPosition().nextPosition(1, 2), this.getPosition().nextPosition(-1, 2), this.getPosition().nextPosition(1, -2), this.getPosition().nextPosition(-1, -2), this.getPosition().nextPosition(2, 1), this.getPosition().nextPosition(2, -1), this.getPosition().nextPosition(-2, -1), this.getPosition().nextPosition(-2, 1)};

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
    @Override
    public boolean canCapture(Position destination) {
        if (destination == null) {
            return false;
        }
        else {
        for(int side = -2; side < 3; side++) {
            for(int direction = -2; direction < 3; direction++){
                if (this.getPosition().nextPosition(side,direction) == destination) {
                    Figure capFig = this.getPosition().nextPosition(side, direction).getFigure();
                    if (capFig == null) {
                        return false;
                    }
                    if (capFig.getPlayer() == this.getPlayer()) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
        }
    }

    /**
     *
     * @param destination
     */
    @Override
    public void capture(Position destination) {
        Position act = destination;   
            if (act.getFigure().getPlayer() != this.getPlayer()) {
                act.getFigure().getPlayer().remFigure(act.getFigure());
                act.removeFigure();
                act.updateIcon();
            }
            this.move(destination);
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
