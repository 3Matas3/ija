//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package ija.projekt.gui;

import ija.projekt.base.Desk;
import ija.projekt.base.Game;
import ija.projekt.base.History;
import ija.projekt.base.Move;
import ija.projekt.base.History.HistoryException;
import ija.projekt.base.Move.MoveException;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
    private History history = new History();
    private JTextField actionField;
    private JButton buttonPlay;
    private JPanel desk;
    private JList historyList;
    private JScrollPane jScrollPane1;
    private JToolBar jToolBar1;
    private JTextField timerField;
    private static final String COLS = "ABCDEFGH";
private static final String ROWS = "12345678";

    /**
     *
     */
    public GamePanel() {
        this.initComponents();
        ((Desk)this.desk).setHistory(this.history);
        this.history.setDeskJListAndPlayButton((Desk) this.desk, this.historyList, this.buttonPlay);
        ((Desk)this.desk).newGame();
    }

    /**
     *
     * @return
     */
    public Desk getDesk() {
        return (Desk)this.desk;
    }

    private void initComponents() {
        this.desk = new Desk();
        this.jScrollPane1 = new JScrollPane();
        this.historyList = new JList(history);
        this.jToolBar1 = new JToolBar();
        this.timerField = new JTextField();
        this.buttonPlay = new JButton();
        this.desk.setLayout(new GridLayout(9,9));
        for(int i=0; i < 8; i++){
            this.desk.add(new JLabel(COLS.substring(i, i+1), SwingConstants.CENTER));
        }    
        this.historyList.setModel(this.history);
        this.historyList.setVisibleRowCount(-1);
        this.historyList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                GamePanel.this.historyListValueChanged(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.historyList);
        this.jToolBar1.setFloatable(false);
        this.jToolBar1.setRollover(true);
        this.timerField.setText("1000");
        this.timerField.setPreferredSize(new Dimension(60, 40));
        this.jToolBar1.add(this.timerField);
        this.buttonPlay.setText("Prehrať");
        this.buttonPlay.setFocusable(false);
        this.buttonPlay.setHorizontalTextPosition(0);
        this.buttonPlay.setVerticalTextPosition(3);
        this.buttonPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GamePanel.this.buttonPlayActionPerformed(evt);
            }
        });
        this.jToolBar1.add(this.buttonPlay);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.desk, -1, 284, 32767).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.LEADING, false).addComponent(this.jToolBar1, -1, -1, 32767).addComponent(this.jScrollPane1, Alignment.TRAILING, -2, 0, 32767))));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.desk, -1, 278, 32767).addGroup(layout.createSequentialGroup().addComponent(this.jScrollPane1).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jToolBar1, -2, 25, -2).addPreferredGap(ComponentPlacement.RELATED)));
    }

    private void historyListValueChanged(ListSelectionEvent evt) {
        if (!evt.getValueIsAdjusting() && this.historyList.getSelectedIndex() != -1) {
            try {
                this.history.goToHistoryItem(this.historyList.getSelectedIndex());
            } catch (HistoryException var3) {
                System.err.println("Výmimka pri skoku na položku histórie:");
                var3.printStackTrace(System.err);
                JOptionPane.showMessageDialog((Component)null, var3.toString(), "Chyba pri skoku na položku histórie", 0);
            }
        }

    }

    private void buttonPlayActionPerformed(ActionEvent evt) {
        if (this.history.isPlayingHistory()) {
            this.history.stopPlayingHistory();
        } else {
            try {
                int timerValue = Integer.parseInt(this.timerField.getText());
                this.history.playHistory(timerValue);
            } catch (NumberFormatException var3) {
                System.err.println("Chybne zadany interval prehravania!");
                JOptionPane.showMessageDialog((Component)null, "Zadaný interval prehrávania neni zadaný správne!", "Chybný interval prehrávania", 0);
            } catch (Exception var4) {
                System.err.println("Vynimka pri prehravani partie:");
                var4.printStackTrace(System.err);
                JOptionPane.showMessageDialog((Component)null, var4.toString(), "Chyba pri prehravaní partie", 0);
            }
        }

    }

    private void buttonActionActionPerformed(ActionEvent evt) {
        try {
            Scanner s = new Scanner(this.actionField.getText());

            while(true) {
                String move;
                do {
                    if (!s.hasNext()) {
                        return;
                    }

                    move = s.next();
                } while(move.charAt(0) >= '0' && move.charAt(0) <= '9');

                this.doAction(move);
            }
        } catch (MoveException var4) {
            System.err.println("Chybne zadany tah textom!");
            JOptionPane.showMessageDialog((Component)null, "Zadaný ťah nie je v správnej notacii!", "Chybne zadaný ťah", 2);
        } catch (GamePanel.DoActionException var5) {
            JOptionPane.showMessageDialog(Game.getWindow(), var5.getMessage(), var5.getTitle(), 1);
            System.err.println(var5.getMessage());
        } catch (Exception var6) {
            System.err.println("Vynimka pri skoku podla retazca:");
            var6.printStackTrace(System.err);
            JOptionPane.showMessageDialog(Game.getWindow(), var6.toString(), "Chyba pri ručne zadanom skoku", 0);
        }

    }

    private void doAction(String move) throws MoveException, GamePanel.DoActionException {
        if (this.getDesk().isGameEnded()) {
            throw new GamePanel.DoActionException("Hra už skončila.\nPre novu hru zvolte Hra -> Nová Hra.", "Nemožno hrat");
        } else if (!this.getDesk().getHistory().inPresent()) {
            throw new GamePanel.DoActionException("zmenit historiu nie je mozne", "nemozno");
        } else {
            Move m = new Move((Desk)this.desk, move);
            if (m.getSource().getFigure() == null) {
                throw new GamePanel.DoActionException("Na zdrojovm políčku nestojí žiadna figurka!", "Nemožný tah");
            } else {
                if (m.isCapture()) {
                    if (!m.getSource().getFigure().canCapture(m.getDestination())) {
                        throw new GamePanel.DoActionException("Zadaný ťah nie je možné vykonať!", "Nemožný ťah");
                    }

                    m.getSource().onClick();
                    m.getDestination().onClick();
                } else {
                    if (!m.getSource().getFigure().canMove(m.getDestination())) {
                        throw new GamePanel.DoActionException("Zadaný ťah nie je možné vykonať!", "Nemožný ťah");
                    }

                    m.getSource().onClick();
                    m.getDestination().onClick();
                }

            }
        }
    }

    class DoActionException extends Exception {
        private String title;

        public DoActionException(String message, String title) {
            super(message);
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }
    }
}
