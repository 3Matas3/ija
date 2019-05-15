//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package ija.projekt.gui;

import ija.projekt.base.Desk;
import ija.projekt.files.TxtFile;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.GroupLayout.Alignment;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {
    private int maxTabNumber = 0;
    private JMenuBar jMenuBar;
    private JMenu jMenuGame;
    private JMenuItem menuItemExitGame;
    private JMenuItem menuItemNewGame;
    private JMenuItem menuItemOpen;
    private JMenuItem menuItemSave;
    private JTabbedPane tabs;

    /**
     *
     */
    public GameWindow() {
        this.initComponents();
        this.newTab();
    }

    /**
     *
     */
    public final void newTab() {
        ++this.maxTabNumber;
        this.tabs.addTab("Hra " + this.maxTabNumber, new GamePanel());
        this.tabs.setSelectedIndex(this.tabs.getTabCount() - 1);
    }

    /**
     *
     */
    public void closeCurrentTab() {
        if (this.tabs.getTabCount() > 1) {
            this.tabs.removeTabAt(this.tabs.getSelectedIndex());
        } else {
            System.exit(0);
        }

    }

    /**
     *
     * @return
     */
    public Desk getDesk() {
        return ((GamePanel)this.tabs.getSelectedComponent()).getDesk();
    }

    private void initComponents() {
        this.tabs = new JTabbedPane();
        this.jMenuBar = new JMenuBar();
        this.jMenuGame = new JMenu();
        this.menuItemNewGame = new JMenuItem();
        this.menuItemSave = new JMenuItem();
        this.menuItemOpen = new JMenuItem();
        this.menuItemExitGame = new JMenuItem();
        this.setDefaultCloseOperation(3);
        this.setTitle("ŠACH");
        this.jMenuGame.setText("Hra");
        this.menuItemNewGame.setText("Nová hra");
        this.menuItemNewGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GameWindow.this.menuItemNewGameActionPerformed(evt);
            }
        });
        this.jMenuGame.add(this.menuItemNewGame);
        this.menuItemOpen.setText("Otvoriť hru");
        this.menuItemOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GameWindow.this.menuItemOpenActionPerformed(evt);
            }
        });
        this.jMenuGame.add(this.menuItemOpen);
        this.menuItemSave.setText("Uložiť hru");
        this.menuItemSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GameWindow.this.menuItemSaveActionPerformed(evt);
            }
        });
        this.jMenuGame.add(this.menuItemSave);
        this.menuItemExitGame.setText("Ukončiť hru");
        this.menuItemExitGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GameWindow.this.menuItemExitGameActionPerformed(evt);
            }
        });
        this.jMenuGame.add(this.menuItemExitGame);
        this.jMenuBar.add(this.jMenuGame);
        this.setJMenuBar(this.jMenuBar);
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.tabs, -1, 871, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.tabs, -1, 576, 32767));
        this.pack();
    }

    private void menuItemNewGameActionPerformed(ActionEvent evt) {
        this.newTab();
    }

    private void menuItemExitGameActionPerformed(ActionEvent evt) {
        this.closeCurrentTab();
    }

    /**
     *
     * @return
     */
    public File getExamplesFolder() {
        File folder = new File(GameWindow.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        folder = new File(folder.getParentFile().getParentFile(), "examples");
        if (!folder.isDirectory()) {
            folder = new File("examples");
        }

        System.out.println(folder.getAbsolutePath());
        return folder;
    }

    private void menuItemOpenActionPerformed(ActionEvent evt) {
        JFileChooser fileDialog = new JFileChooser(this.getExamplesFolder());
        fileDialog.setAcceptAllFileFilterUsed(false);
        fileDialog.setFileFilter(new TxtFile());
        if (fileDialog.showOpenDialog(this) == 0) {
            this.newTab();
            File file = fileDialog.getSelectedFile();

            try {
                if (!(fileDialog.getFileFilter() instanceof TxtFile)) {
                    return;
                }

                TxtFile.loadFile(file, this.getDesk().getHistory());
                this.getDesk().getHistory().goToPresent();
                if (this.getDesk().getHistory().getCount() % 2 == 1 != this.getDesk().getPlayer().isBlack()) {
                    this.getDesk().nextPlayer();
                }

                if (this.getDesk().getHistory().getCurrent() > 1 && (this.getDesk().getWhitePlayer().loses() && this.getDesk().getPlayer().isWhite() || this.getDesk().getBlackPlayer().loses() && this.getDesk().getPlayer().isBlack())) {
                    this.getDesk().endGame();
                }
            } catch (Exception var5) {
                System.err.println("Chyba pri otvárání:");
                var5.printStackTrace(System.err);
                JOptionPane.showMessageDialog(this, "Chyba pri otváraní: " + var5.toString(), "Chyba pri otváraní", 0);
            }
        }

    }

    private void menuItemSaveActionPerformed(ActionEvent evt) {
        JFileChooser fileDialog = new JFileChooser(this.getExamplesFolder());
        fileDialog.setAcceptAllFileFilterUsed(false);
        fileDialog.setFileFilter(new TxtFile());
        if (fileDialog.showSaveDialog(this) == 0) {
            File file = fileDialog.getSelectedFile();

            try {
                if (fileDialog.getFileFilter() instanceof TxtFile) {
                    if (!file.getPath().endsWith(".txt")) {
                        file = new File(file.getPath() + ".txt");
                    }

                    TxtFile.saveFile(file, this.getDesk().getHistory());
                }
            } catch (Exception var5) {
                System.err.println("Chyba pri ukladaní: " + var5.toString());
                JOptionPane.showMessageDialog(this, "Chyba pri ukladaní: " + var5.toString(), "Chyba pri ukladání", 0);
            }
        }

    }
}
