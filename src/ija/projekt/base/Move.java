package ija.projekt.base;

public class Move {

    /**
     *
     */
    public Position source;

    /**
     *
     */
    public Position destination;
    private boolean capture;

    /**
     *
     * @param s
     * @param d
     * @param c
     */
    public Move(Position s, Position d, boolean c) {
        this.source = s;
        this.destination = d;
        this.capture = c;
    }

    /**
     *
     * @return
     */
    public byte getX1() {
        return this.source.getColumn();
    }

    /**
     *
     * @return
     */
    public byte getY1() {
        return this.source.getRow();
    }

    /**
     *
     * @return
     */
    public byte getX2() {
        return this.destination.getColumn();
    }

    /**
     *
     * @return
     */
    public byte getY2() {
        return this.destination.getRow();
    }

    /**
     *
     * @return
     */
    public Position getSource() {
        return this.source;
    }

    /**
     *
     * @return
     */
    public Position getDestination() {
        return this.destination;
    }

    /**
     *
     * @return
     */
    public boolean isCapture() {
        return this.capture;
    }

    public String toString() {
        return this.source.toString() + (this.capture ? "x" : "-") + this.destination.toString();
    }

    /**
     *
     * @param desk
     * @param s
     * @throws MoveException
     */
    public Move(Desk desk, String s) throws Move.MoveException {
        if (s.length() == 5 && s.charAt(0) >= 'a' && s.charAt(0) <= 'h' && s.charAt(1) >= '1' && s.charAt(1) <= '8' && (s.charAt(2) == '-' || s.charAt(2) == 'x') && s.charAt(3) >= 'a' && s.charAt(3) <= 'h' && s.charAt(4) >= '1' && s.charAt(4) <= '8') {
            if (desk == null) {
                throw new Move.MoveException("Desk je null!");
            } else {
                this.source = desk.getPositionAt(s.charAt(0) - 97, s.charAt(1) - 49);
                this.capture = s.charAt(2) == 'x';
                this.destination = desk.getPositionAt(s.charAt(3) - 97, s.charAt(4) - 49);
            }
        } else {
            throw new Move.MoveException("Tah není zapsán ve správné notaci!");
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public class MoveException extends Exception {

        /**
         *
         * @param message
         */
        public MoveException(String message) {
            super(message);
        }
    }
}
