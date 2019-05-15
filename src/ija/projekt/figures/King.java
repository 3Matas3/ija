package ija.projekt.figures;

import ija.projekt.base.Figure;
import ija.projekt.base.Player;
import ija.projekt.base.Position;

public class King extends Figure{

    /**
     *
     * @param position
     * @param player
     */
    public King(Position position, Player player) {
        super(position, player);
     }

    /**
     *
     * @param destination
     * @return
     */
    @Override
    public boolean canMove(Position destination) {
        if(destination.getFigure() != null){
            return false;
        }
       if (this.getPosition().nextPosition(1, 0) == destination) {
            return true;
        }else if (this.getPosition().nextPosition(1, 1) == destination) {
            return true;
        }else if (this.getPosition().nextPosition(0, 1) == destination) {
            return true;
        } else if (this.getPosition().nextPosition(-1, 1) == destination) {
            return true;
        } else if (this.getPosition().nextPosition(-1, 0) == destination) {
            return true;
        } else if (this.getPosition().nextPosition(-1, -1) == destination) {
            return true;
        } else if (this.getPosition().nextPosition(0, -1) == destination) {
            return true;
        } else return this.getPosition().nextPosition(1, -1) == destination;
        
    }

    /**
     *
     * @return
     */
    @Override
    public boolean canCapture() {
        Position[] destinations = new Position[]{this.getPosition().nextPosition(1, 0), this.getPosition().nextPosition(1, 1), this.getPosition().nextPosition(0, 1), this.getPosition().nextPosition(-1, 1), this.getPosition().nextPosition(-1, 0), this.getPosition().nextPosition(-1, 1), this.getPosition().nextPosition(0, -1), this.getPosition().nextPosition(1, -1)};

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
        for(int side = -1; side <= 1; side++) {
            for(int direction = -1; direction <= 1; direction++){
                if (this.getPosition().nextPosition(side,direction) == destination) {
                    Figure capFig = this.getPosition().nextPosition(side, direction).getFigure();
                    if (capFig == null) {
                        return false;
                    }
                    return capFig.getPlayer() != this.getPlayer();
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
        if(this.canCapture(position) == position.isKing(position)){
            return true;
        }
        return false;
    }
}    
   