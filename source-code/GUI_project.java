//Import
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.border.Border;
import javax.swing.*;

//Beginn der Klasse
/**
 * @author voellmecke|j|p
 * @version 3.0 (2017|04|26)
 * Das GUI des Projektes
 */
public class GUI_project extends JFrame {

    //Spielfeld
    private Pitch spielfeld;
    
    //GUI
    private JMenuBar menuBar;
    private JButton[][] buttons;
    private JLabel label1;
    private JLabel label2;
    private JPanel panel1;
    
    //Angabe der xy-Koordinate beim schießen
    private int shotX;
    private int shotY;


    //Constructor 
    public GUI_project(){

        //Spielfeld
        spielfeld = new Pitch();
        
        //GUI
        this.setTitle("Battleship 3.1 (by Tobias Selms & Jonas Patrick V\u00f6llmecke)"); //this.setTitle("Schiffe versenken 2 (by Jonas Patrick Völlmecke)");
        this.setSize(620,680);
        //menu generate method
        generateMenu();
        this.setJMenuBar(menuBar);

        //pane with null layout
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(620,680));
        contentPane.setBackground(new Color(192,192,192));


        //GUI
        buttons = new JButton[10][10];
        
        
        //Koordinaten der Button
        int x = 15;
        int y = 15;
        for ( int i=0; i<10; i++ ) {
            for ( int j=0; j<10; j++ ) {
                int feldX = i;
                int feldY = j;
                buttons[i][j] = new JButton();
                buttons[i][j].setOpaque(true);
                buttons[i][j].setBounds(x,y,50,50);
                buttons[i][j].setBackground(new Color(255,0,0));
                buttons[i][j].setForeground(new Color(0,0,0));
                buttons[i][j].setEnabled(true);
                buttons[i][j].setFont(new Font("SansSerif",0,15));
                buttons[i][j].setText("?");
                buttons[i][j].setVisible(true);
                
                //Set action for button click
                //Call defined method
                buttons[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        shotX = feldX;
                        shotY = feldY;
                        shot(evt);
                    }
                });
                
                y = y + 60;
            }
            x = x + 60;
            y = 15;
        }
        


        label1 = new JLabel();
        label1.setBounds(165,5,421,55);
        label1.setBackground(new Color(214,217,223));
        label1.setForeground(new Color(0,0,0));
        label1.setEnabled(true);
        label1.setFont(new Font("SansSerif",1,35));
        label1.setText("-Battleship 3.1-");
        label1.setVisible(true);

        label2 = new JLabel();
        label2.setBounds(165,165,355,216);
        label2.setBackground(new Color(0,0,0));
        label2.setForeground(new Color(0,0,0));
        label2.setEnabled(true);
        label2.setFont(new Font("SansSerif",1,50));
        label2.setText("VICTORY!!!");
        label2.setVisible(false);

        panel1 = new JPanel(null);
        panel1.setBorder(BorderFactory.createEtchedBorder(1));
        panel1.setBounds(0,62,620,618);
        panel1.setBackground(new Color(255,255,255));
        panel1.setForeground(new Color(0,0,0));
        panel1.setEnabled(true);
        panel1.setFont(new Font("sansserif",0,12));
        panel1.setVisible(true);

        //adding components to contentPane
        contentPane.add(label1);
        contentPane.add(label2);
        contentPane.add(panel1);
        //adding components to panel1
        for ( int i=0; i<10; i++ ) {
            for ( int j=0; j<10; j++ ) {
                panel1.add(buttons[i][j]);
            }
        }

        //adding panel to JFrame and seting of window position and close operation
        this.add(contentPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    //Method actionPerformed for buttons[i][j]
    private void shot (ActionEvent evt) {
            //Einlesen von x- & y-Koordinate
            int x = shotX;
            int y = shotY;
            //Feld beschießen, Text nach Ergebniss verändern und versenken-Sound abspielen
            if (spielfeld.feldBeschiessen(x,y).equals("W")) {
                buttons[x][y].setText("W");
                buttons[x][y].setBackground(new Color(0,0,255));
            } else {
                buttons[x][y].setText("S");
                buttons[x][y].setBackground(new Color(0,0,0));
            }

            //Button Deaktivieren, auf den soeben geschossen wurde
            buttons[x][y].setEnabled(false);


            //Wurde mit diesem Zug gewonnen?
            panel1.setVisible(!spielfeld.gewonnen());
            label2.setVisible(spielfeld.gewonnen());
            if (spielfeld.gewonnen()) {
                spielfeld.playSound(spielfeld.victory);
            }
    }


    
    //MENÜ
    //method for generate menu
    public void generateMenu(){
        menuBar = new JMenuBar();

        JMenu spiel = new JMenu("Game");
        JMenu einstellungen = new JMenu("Settings");
        JMenu sound = new JMenu("Sound");

        JMenuItem neu = new JMenuItem("New   ");
        neu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK));

        JMenuItem beenden = new JMenuItem("Quit   ");
        beenden.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,Event.CTRL_MASK));

        JMenuItem an = new JMenuItem("on   ");
        JMenuItem aus = new JMenuItem("off   ");

        //Setings action for menu item
        //Call defined method
        neu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                spielNeu(evt);
            }
        });

        //Setings action for menu item
        //Call defined method
        beenden.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SpielBeenden(evt);
            }
        });

        //Setings action for menu item
        //Call defined method
        an.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                soundAn(evt);
            }
        });

        //Setings action for menu item
        //Call defined method
        aus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SoundAus(evt);
            }
        });


        spiel.add(neu);
        spiel.add(beenden);
        einstellungen.add(sound);
        sound.add(an);
        sound.add(aus);

        menuBar.add(spiel);
        menuBar.add(einstellungen);
    }
    
    
    //Method for Neu from menuSpiel 
    private void spielNeu (ActionEvent evt) {
            new GUI_project();
    }

    //Method for Beenden from menuSpiel 
    private void SpielBeenden (ActionEvent evt) {
            spielfeld.playSound(spielfeld.end);
            //Das Beenden muss verlangsamt werden, da sonst der Sound nicht komplett wiedergegeben wird
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) { System.err.println("Got Exception: " + e + " while trying to sleep in methode SpielBeenden of GUI_project. Quitting anyway...;)"); System.exit(0); }
            System.exit(0);
    }

    //Method for an from menuSound 
    private void soundAn (ActionEvent evt) {
            spielfeld.toggleVolume(true);
    }

    //Method for aus from menuSound 
    private void SoundAus (ActionEvent evt) {
            spielfeld.toggleVolume(false);
    }




    //main
    public static void main(String[] args){
        System.setProperty("swing.defaultlaf", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI_project();
            }
        });
    }
}