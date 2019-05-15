package ija.projekt.figures;

import ija.projekt.base.Figure;
import ija.projekt.base.Player;
import ija.projekt.base.Position;

public class Rook extends Figure {

    /**
     *
     * @param position
     * @param player
     */
    public Rook(Position position, Player player) {
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

        if(!this.getPosition().isStraightOf(destination)){
            return false;
        }

        int xinc = this.getPosition().x;
        int yinc = this.getPosition().y;
        if(xinc > destination.x){
            xinc = -1;
        } else if(xinc < destination.x){
            xinc = 1;
        } else{
            xinc = 0;
        }
        if(yinc > destination.y){
            yinc = -1;
        }else if(yinc < destination.y){
            yinc = 1;
        } else{
            yinc = 0;
        }
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
                } else if(xinc < 0){
                    xinc--;
                } else{
                    xinc = 0;
                }
                if(yinc > 0){
                    yinc++;
                }
                else if(yinc < 0){
                    yinc--;
                } else{
                    yinc = 0;
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
        for(int dirx = -1; dirx <= 1; dirx +=2) {
            for(int diry = -1; diry <= 1; diry+=2) {
               for(Position act = this.getPosition().nextPosition(dirx, 0); act != null; act = act.nextPosition(dirx, 0)) {
                    if (this.canCapture(act)) {
                        return true;
                    }
                }
               
               for(Position act = this.getPosition().nextPosition(0, diry); act != null; act = act.nextPosition(0, diry)) {
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
        }else if(!this.getPosition().isStraightOf(destination)){
            return false;
        }else {
            int xinc = this.getPosition().x;
            int yinc = this.getPosition().y;
            if(xinc > destination.x){
                xinc = -1;
            } else if(xinc < destination.x){
                xinc = 1;
            } else{
                xinc = 0;
            }   
            if(yinc > destination.y){
                yinc = -1;
            }else if(yinc < destination.y){
                yinc = 1;
            } else{
                yinc = 0;
            } 
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
                    } else if(xinc < 0){
                        xinc--;
                    }else{
                        xinc = 0;
                    }
                    if(yinc > 0){
                        yinc++;
                    }
                    else if(yinc == 0){
                        yinc = 0;
                    } else{
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
        return this.canCapture(position) == position.isKing(position);
    }
}
