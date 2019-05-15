package ija.projekt.base;

import ija.projekt.base.Move.MoveException;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class History extends DefaultListModel<String> {
    private Desk desk;
    private final LinkedList<Move> list = new LinkedList<>();
    private int currentId = -1;
    private Timer playTimer = null;
    private JList<History> jlist;
    private JButton playButton;

    /**
     *
     */
    public History() {
    }

    /**
     *
     * @param desk
     * @param jlist
     * @param playButton
     */
    public void setDeskJListAndPlayButton(Desk desk, JList<History> jlist, JButton playButton) {
        this.desk = desk;
        this.jlist = jlist;
        this.playButton = playButton;
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
     * @param source
     * @param destination
     */
    public void addMove(Position source, Position destination) {
        Move item = new Move(source, destination, false);
        this.addItem(item);
    }

    /**
     *
     * @param source
     * @param destination
     */
    public void addCapture(Position source, Position destination) {
        Move item = new Move(source, destination, true);
        this.addItem(item);
    }

    /**
     *
     * @param s
     * @throws MoveException
     */
    public void addFromString(String s) throws MoveException {
        Move item = new Move(this.desk, s);
        this.addItem(item);
    }

    private void addItem(Move item) {
        this.list.addLast(item);
        if (this.list.size() % 2 == 1) {
            super.addElement("" + (this.list.size() + 1) / 2 + ". " + item.toString());
        } else {
            super.addElement("                      " + item.toString());
        }

        this.setCurrent(this.getCount() - 1);
    }

    /**
     *
     * @return
     */
    public int getCount() {
        return this.list.size();
    }

    /**
     *
     * @param i
     * @return
     */
    public Move getItem(int i) {
        return this.list.get(i);
    }

    /**
     *
     */
    public void clearItems() {
        this.list.clear();
        super.clear();
    }

    /**
     *
     */
    public void deskWasResetted() {
        this.setCurrent(-1);
    }

    /**
     *
     * @return
     */
    public int getCurrent() {
        return this.currentId;
    }

    private void setCurrent(int i) {
        this.currentId = i;
        if (i == -1) {
            this.jlist.clearSelection();
        } else {
            this.jlist.setSelectedIndex(i);
        }

    }

    /**
     *
     * @return
     */
    public boolean inPresent() {
        return this.currentId == this.getCount() - 1;
    }

    /**
     *
     * @throws HistoryException
     */
    public void goToPresent() throws History.HistoryException {
        this.goToHistoryItem(this.getCount() - 1, true);
    }

    /**
     *
     * @param destinationId
     * @throws HistoryException
     */
    public void goToHistoryItem(int destinationId) throws History.HistoryException {
        this.goToHistoryItem(destinationId, false);
    }

    /**
     *
     * @param destinationId
     * @param repeat
     * @throws HistoryException
     */
    public void goToHistoryItem(int destinationId, boolean repeat) throws History.HistoryException {
        if (destinationId >= -1 && destinationId < this.getCount()) {
            if (destinationId != this.currentId || repeat) {
                this.desk.resetDesk();
                this.desk.clearSelected();
                this.setCurrent(destinationId);

                for(int i = 0; i <= destinationId; ++i) {
                    Move item = this.getItem(i);
                    if (item.getSource().getFigure() == null) {
                        throw new History.HistoryException("Zdroj skoku (" + item.getSource().toString() + ") nema figurku!");
                    }

                    if (item.isCapture()) {
                        item.getSource().getFigure().capture(item.getDestination());
                    } else {
                        item.getSource().getFigure().move(item.getDestination());
                    }
                }

                this.desk.updateAllBackgrounds();
            }
        } else {
            throw new History.HistoryException("Nelze prejit na tah " + destinationId + ", ktery je mimo rozsah!");
        }
    }

    /**
     *
     * @param interval
     */
    public void playHistory(int interval) {
        System.out.println("Zahajene prehravanie partie");
        final int destinationId = this.getCurrent();
        this.desk.resetDesk();
        this.playTimer = new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (History.this.getCurrent() >= destinationId) {
                    System.out.println("Prehravanie partie dokoncene");
                    History.this.stopPlayingHistory();
                    History.this.desk.updateAllBackgrounds();
                } else {
                    try {
                        History.this.goToHistoryItem(History.this.getCurrent() + 1);
                    } catch (HistoryException var3) {
                        System.err.println("Vynimka pri prehravani partie:");
                        var3.printStackTrace(System.err);
                        JOptionPane.showMessageDialog((Component)null, var3.toString(), "Chyba pri prehrávání", 0);
                    }

                }
            }
        });
        this.playTimer.start();
        this.playButton.setText("Stop");
    }

    /**
     *
     */
    public void stopPlayingHistory() {
        this.playTimer.stop();
        this.playTimer = null;
        this.playButton.setText("Prehrat");
    }

    /**
     *
     * @return
     */
    public boolean isPlayingHistory() {
        return this.playTimer != null;
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    public class HistoryException extends Exception {

        /**
         *
         * @param message
         */
        public HistoryException(String message) {
            super(message);
        }
    }
}
