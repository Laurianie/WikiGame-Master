
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
    private JTextArea inputEndURL;
    private JTextArea ta;
    private JLabel URL;
    private JLabel EndURL;
    private JButton start;
    private Font font;


    private JPanel panel;
    private JScrollPane scrollBar;

    private int WIDTH = 1400;
    private int HEIGHT = 1200;

    private ArrayList<String> noDupes = new ArrayList<String>();
    private ArrayList<String> Path = new ArrayList<String>();

    boolean foundlink = false;
    boolean noTwice = false;
    int counterr = 0;
    int DEPTH = 2;



//    TEST RUN 1 = https://en.wikipedia.org/wiki/Zendaya (DOES NOT WORK)
//    TEST RUN 1 = https://en.wikipedia.org/wiki/Abbott_Elementary (DOES NOT WORK)
//    TEST RUN 2 = https://en.wikipedia.org/wiki/Beyonc%C3%A9 (WORKS)
//    TEST RUN 2 = https://en.wikipedia.org/wiki/The_Lion_King_(2019_film) (WORKS)



    public static void main(String[] args) {
        WikiGame w = new WikiGame();
        w.prepareGUI();
    }


    private void prepareGUI() {
        font = new Font("Normal", Font.ITALIC, 20);

        mainFrame = new JFrame("Wiki Game");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new BorderLayout());
        panel = new JPanel();
        panel.setLayout(new GridLayout(3,4));
        mainFrame.add(panel,BorderLayout.NORTH);


        inputURL = new JTextArea();
        inputEndURL = new JTextArea();

        ta = new JTextArea("RESULTS: " + "\n");
        ta.setBackground(Color.cyan);
        ta.setForeground(Color.blue);
        ta.setFont(font);
        URL = new JLabel ("INPUT URL: ");
        URL.setSize(2,2);
        URL.setFont(font);
        URL.setForeground(Color.blue);
        EndURL = new JLabel ("INPUT END URL: ");
        EndURL.setSize(2,2);
        EndURL.setFont(font);
        EndURL.setForeground(Color.blue);

        start = new JButton ("START ");
        start.setActionCommand("START");
        start.addActionListener(new ButtonClickListener());


        panel.add(start);
        panel.add(URL);
        panel.add(inputURL);
        panel.add(EndURL);
        panel.add(inputEndURL);
        panel.add(start);
        mainFrame.add(ta,BorderLayout.CENTER);
        scrollBar = new JScrollPane(ta);
        scrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainFrame.add(scrollBar,BorderLayout.CENTER);


        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }});

        mainFrame.setVisible(true);
    }


    public void Wikigame(String originalLink, String fLink, int depth){

        Path.add(originalLink);


        if (foundlink == false) {
            try {
                URL url = new URL(originalLink);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(url.openStream())
                );
                String line;
                    while ((line = reader.readLine()) != null && foundlink == false) {
                        while (line.contains("href=")) {
                            int n = -1;
                            int start = line.indexOf("href=") + 6;

                            line = line.substring(start);
                            int end = line.indexOf("\"");
                            int end2 = line.indexOf("\'");

                            if (end > n) {
                                originalLink = line.substring(0, end);
                            } else if (end2 > n) {
                                originalLink = line.substring(0, end2);
                            }
                            if (end != n && end2 != n) {
                                if (end < end2) {
                                    originalLink = line.substring(0, end);
                                }
                                if (end2 < end) {
                                    originalLink = line.substring(0, end2);
                                }
                            }
                            if (originalLink.contains("/wiki/") && !(originalLink.contains("https:")) && !(originalLink.contains("en")) && !(originalLink.contains("//en.wikipedia.org/"))) {
                                if (!noDupes.contains(originalLink)) {
                                    noDupes.add(originalLink);


                                    String finalLink = "https://en.wikipedia.org" + originalLink;


                                    if (finalLink.contains(fLink)) {
                                        Path.add(finalLink);
                                        foundlink = true;
                                    }

                                    if (foundlink == true){
                                        counterr++;
                                    }

                                    if (counterr == 2){
                                        noTwice = true;
                                    }

                                    if (foundlink == true && noTwice == false ) {
                                        System.out.println("WE FOUND IT");
                                        ta.append("This is the path: ");

                                        for (int i = 0; i < Path.size(); i++) {
                                            System.out.println(Path.get(i));

                                            ta.append("\n ↓");
                                            ta.append(" \n ");
                                            ta.append(Path.get(i));
                                            ta.append("\n");
                                        }

                                        Path.clear();
                                    }

                                    if (depth <= DEPTH && foundlink == false) {
                                        Wikigame(finalLink, fLink, depth + 1);
                                    }
                                }
                            }
                            line = line.substring(end);
                        }
                    }

                print();
                reader.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }

            if (foundlink == false && Path.size() >= 1) {
                Path.remove(Path.size() - 1);
            }
        }}

    public void print(){
        for(int i = 0; i > noDupes.size(); i++){
            System.out.println(noDupes.get(i));
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
                System.out.println("The depth is: " + DEPTH);
                Wikigame(inputURL.getText(),inputEndURL.getText(), 2);
                if (foundlink == false){
                    System.out.println("This link could not be found :( ");
                    ta.append("\n THIS LINK COULD NOT BE FOUND :( ");
                }
            }
        }
    }
}

