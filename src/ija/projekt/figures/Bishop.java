package ija.projekt.figures;

import ija.projekt.base.Figure;
import ija.projekt.base.Player;
import ija.projekt.base.Position;


public class Bishop extends Figure {

    static Figure ca;

    /**
     *
     * @param position
     * @param player
     */
    public Bishop(Position position, Player player) {
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

        if(!this.getPosition().isDiagonalOf(destination)){
            return false;
        }

        int xinc = this.getPosition().x < destination.x ? 1 : -1;
        int yinc = this.getPosition().y < destination.y ? 1 : -1;        
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(this.getPosition().nextPosition(xinc, yinc).getFigure() != null){
                    return false;
                }
                else if(this.getPosition().nextPosition(xinc, yinc) == destination){
                    return true;
                }
                if(xinc > 0){
                    xinc++;
                } else{
                    xinc--;
                }
                if(yinc > 0){
                    yinc++;
                }
                else{
                    yinc--;
                }
                                   
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean canCapture() {
        for(int dirx = -1; dirx <= 1; dirx += 2) {
            for(int diry = -1; diry <= 1; diry += 2) {
                for(Position act = this.getPosition().nextPosition(2*dirx, 2 * diry); act != null; act = act.nextPosition(dirx, diry)) {
                    if (this.canCapture(act)) {
                        return true;
                    }
                }
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
        }else if(!this.getPosition().isDiagonalOf(destination)){
            return false;
        }else {
            int xinc = this.getPosition().x < destination.x ? 1 : -1;
            int yinc = this.getPosition().y < destination.y ? 1 : -1;  
            for(int direction = 0; direction < 8; direction++){
                for(int side = 0; side < 8; side++) {
                if(this.getPosition().nextPosition(xinc, yinc).getFigure() != null && this.getPosition().nextPosition(xinc, yinc) != destination){
                    return false;
                }
                    if(this.getPosition().nextPosition(xinc, yinc) == destination){
                        Figure capFig = this.getPosition().nextPosition(xinc, yinc).getFigure();
                        if (capFig == null) {
                            return false;
                        }
                        return capFig.getPlayer() != this.getPlayer(); 
                    }
                    if(xinc > 0){
                        xinc++;
                    } else{
                        xinc--;
                    }
                    if(yinc > 0){
                        yinc++;
                    }
                    else{
                        yinc--;
                    }
                }
             }
         }
        return false;
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
        return this.canCapture() == position.isKing(position);
    }
}
