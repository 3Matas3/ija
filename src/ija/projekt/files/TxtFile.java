//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package ija.projekt.files;

import ija.projekt.base.History;
import ija.projekt.base.Move.MoveException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.filechooser.FileFilter;

public class TxtFile extends FileFilter {

    /**
     *
     */
    public TxtFile() {
    }

    /**
     *
     * @param path
     * @return
     */
    public static boolean acceptable(File path) {
        return path.isDirectory() ? true : path.getAbsolutePath().endsWith(".txt");
    }

    public boolean accept(File path) {
        return acceptable(path);
    }

    public String getDescription() {
        return "Základna notacia (*.txt)";
    }

    /**
     *
     * @param path
     * @param history
     * @throws FileNotFoundException
     * @throws IOException
     * @throws MoveException
     */
    public static void loadFile(File path, History history) throws FileNotFoundException, IOException, MoveException {
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        history.getDesk().newGame();

        String line;
        while((line = br.readLine()) != null) {
            Scanner scanner = new Scanner(line);
            scanner.next();

            while(scanner.hasNext()) {
                String temp = scanner.next();
                history.addFromString(temp);
            }
        }

        br.close();
        fr.close();
    }

    /**
     *
     * @param path
     * @param history
     * @throws IOException
     */
    public static void saveFile(File path, History history) throws IOException {
        FileWriter fw = new FileWriter(path);

        for(int i = 0; i * 2 < history.getCount(); ++i) {
            fw.write("" + (i + 1) + ". " + history.getItem(i * 2).toString());
            if (i * 2 + 1 < history.getCount()) {
                fw.write(" " + history.getItem(i * 2 + 1).toString());
            }

            fw.write("\n");
        }

        fw.close();
    }
}
