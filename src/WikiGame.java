
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class WikiGame implements ActionListener {
    private JFrame mainFrame;
    private JTextArea inputURL;
    private JTextArea search;
    private JTextArea ta;
    private JLabel URL;
    private JLabel SEARCH;
    private JButton start;
    private JPanel panel;
    private JScrollPane scrollBar;

    private int WIDTH = 1400;
    private int HEIGHT = 1200;

    private int maxDepth;
    private ArrayList<String> path = new ArrayList<>();

    boolean visited = false;

    public static void main(String[] args) {
        WikiGame w = new WikiGame();
        w.prepareGUI();
    }

    public WikiGame() {
        String startLink = "https://en.wikipedia.org/wiki/Zendaya";  // beginning link, where the program will start
        String endLink = "https://en.wikipedia.org/wiki/Timoth%C3%A9e_Chalamet";    // ending link, where the program is trying to get to
        maxDepth = 2;           // start this at 1 or 2, and if you get it going fast, increase

        if (depthFirstSearch(startLink, endLink, 0)) {
            System.out.println("found it !!");
            path.add(startLink);
        } else {
            System.out.println("did not find it !!");
        }

    }

    // recursion method, you'll probably want to rename it
    // you may want this method to return something or take additional parameters

    public boolean depthFirstSearch(String startLink, String endLink, int depth) {
        // recursion method
        //public boolean findLink(String startLink, String endLink, int depth) {

        System.out.println("depth is: " + depth + ", link is: https://en.wikipedia.org" + startLink);

        // BASE CASE
        if (startLink.equals(endLink)) {
            System.out.println(endLink);
            System.out.println("found it !!");
        } else if (depth == maxDepth) {
            System.out.println("not found");
        }
        // GENERAL RECURSIVE CASE
        else {
           // boolean already[] = new boolean[V];             //initialize a new boolean array to store the details of explored nodes
            //depthFirstSearch(v, already);


        }

        return false;
    }


    private void prepareGUI() {
        mainFrame = new JFrame("Wiki Game");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new BorderLayout());
        panel = new JPanel();
        panel.setLayout(new GridLayout(2,3));
        mainFrame.add(panel,BorderLayout.NORTH);


        inputURL = new JTextArea();
//        search = new JTextArea();
        ta = new JTextArea("RESULTS: " + "\n");
        URL = new JLabel ("INPUT URL: ");
        URL.setSize(2,2);
//        SEARCH = new JLabel ("SEARCH: ");
//        SEARCH.setSize(2,2);
        start = new JButton ("START ");
        start.setActionCommand("START");
        start.addActionListener(new ButtonClickListener());


//        panel.add(SEARCH);
//        panel.add(search);
        panel.add(start);
        panel.add(URL);
        panel.add(inputURL);
        panel.add(start);
        mainFrame.add(ta,BorderLayout.CENTER);
        scrollBar = new JScrollPane(ta);
        scrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainFrame.add(scrollBar,BorderLayout.CENTER);


        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        mainFrame.setVisible(true);
    }

    private void WikiGame(){
        try {
            System.out.println();
            URL url = new URL (inputURL.getText());
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;
            while ( (line = reader.readLine()) != null ) {
                System.out.println(line);
                while (line.contains("href=")) {
                    int n = -1;
                    int start = line.indexOf("href=") + 6;

                    line = line.substring(start);
                    System.out.println(line);
                    int end = line.indexOf("\"");
                    int end2 = line.indexOf("\'");
                    System.out.println("end \": " + end + " END 2 \': " + end2);

                    String newLine = "";
                    if (end > n) {
                         newLine = line.substring(0, end);

                        System.out.println(":" + newLine);
//                        ta.setText(ta.getText() + "\"" + newLine + "\n");
                    } else if (end2 > n) {
                         newLine = line.substring(0, end2);
                        System.out.println(newLine);
//                        ta.setText(ta.getText() + "\'" + newLine + "\n");
                    }
                    if (end != n && end2 != n) {
                        if (end < end2) {
                             newLine = line.substring(0, end);

                            System.out.println("::" + newLine);
//                            ta.setText(ta.getText() + "\"" + newLine + "\n");
                        }
                        if (end2 < end) {
                             newLine = line.substring(0, end2);
                            System.out.println(":::" + newLine);
//                            ta.setText(ta.getText() + "\'" + newLine + "\n");
                        }
                    }
                    if (newLine.contains("/wiki/")){
                        ta.setText(ta.getText() + "\'" + newLine + "\n");
                    }
                    line = line.substring(end);

                }

            }
            reader.close();
        } catch(Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("START")){
                start.setText("START clicked ");
                WikiGame();
            }
        }
    }
}

