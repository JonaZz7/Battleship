//Import von Sound
import java.io.*;
import javax.sound.sampled.*;
import java.lang.ClassLoader.*;

/**
 * @author selms|t & voellmecke|j|p
 * @version 3.0 (2017|04|26)
 * Spielfeld zum Schiffe versenken
 */
public class Pitch {
    private Field[][] spielfeld;
    
    
    //Sound
    private String schiff;
    private String wasser;
    private String versenkt;
    private String start;
    protected String end; //!hier MUSS protected stehen (s. GUI_project)!
    protected String victory; //!hier MUSS protected stehen (s. GUI_project)!
    private String sound_off;
    private String sound_on;
    
    private boolean on;
    /**
     * Ein neues Spielfeld wird erzeugt.
     */
    public Pitch() {
        //Größe des Feldes
        int dimension = 10;
        
        // Array erstellen
        spielfeld = new Field [dimension] [dimension];
        // Array mit Feldern füllen
        for(int i=0; i<dimension; i++) {
            for(int j=0; j<dimension; j++) {
                spielfeld[i][j] = new Field();
            }
        }
        //Schiff plazieren
        this.schiffPlatzieren();
        
        //Sound
        schiff = "sounds/ship.wav";
        wasser = "sounds/water.wav";
        versenkt = "sounds/sunk.wav";
        start = "sounds/start.wav";
        end = "sounds/goodbye.wav";
        victory = "sounds/victory.wav";
        sound_on = "sounds/sound_activated.wav";
        sound_off = "sounds/sound_deactivated.wav";
        on = true;
        
        //Sound-effect
        playSound(start);
    }

    /** 
     * Das durch die Koordinaten gegebene Feld wird
     * beschossen.
     * 
     * @param pPosX x-Koordinate
     * @param pPosY y-Koordinate
     * @return Status des aufgedeckten Feldes
     */
    public String feldBeschiessen(int pPosX, int pPosY) {
        spielfeld[pPosX][pPosY].aufdecken();
        if(spielfeld[pPosX][pPosY].gibStatus().equals("W")) {
            playSound(wasser);
        }
        else {
            playSound(schiff);
            //Komplette Schiff versenkt?
            versenkt(pPosX,pPosY);
        }
        return spielfeld[pPosX][pPosY].gibStatus();
    }

    /**
     * Das Spielfeld wird zeilenweise ausgegeben. Nach jeder
     * Zeile wird ein Zeilenumbruch "\n" ausgegeben.
     *
     * @return Zeilenweise Ausgabe des Status aller Felder.
     */
    public String ausgabeSpielfeld() {
        String ausgabe = "";
        for(int i=0; i<spielfeld.length; i++) {
            for(int j=0; j<spielfeld.length; j++) {
                ausgabe = ausgabe + spielfeld[i][j].gibStatus() + " ";
            }
            ausgabe = ausgabe + "\n";
        }
        
        return ausgabe + "\n";
    }
    
    /**
     * Diese Methode testet ob alle Schiffe abgeknallt wurden
     * (d.h. das Spiel gewonnen wurde)und gibt einen
     * entsprechenden Wahrheitswert zurück
     * @return boolean Sind alle Schiffe gesunken?
     */
    public boolean gewonnen(){
        boolean returnValue = true;
        for(int i=0; i<spielfeld.length; i++) {
            for(int j=0; j<spielfeld.length; j++) {
                if (!spielfeld[i][j].istAufgedeckt() && !spielfeld[i][j].istWasserfeld()) {
                    returnValue = false;
                }
            }
        }
        return returnValue;
    }
    
    /**
     * @param Position des Schusses
     * Diese Methode prüft, ob ein Schiff vollständig versenkt wurde (wenn es denn getroffen wurde)
     * und gibt dann einen Sound aus
     */
    public void versenkt(int pPosX, int pPosY) {
        //Alle Positionen um den Schuss auf Wasserfeld oder afgedecktes Feld überprüfen (dann dann wurde das Schiff versenkt)
        
        boolean play = true;
        int endeSchiffMX = pPosX;
        int endeSchiffPX = pPosX;
        int endeSchiffMY = pPosY;
        int endeSchiffPY = pPosY;
        
        //Um keine ArrayOutOfBoundsException zu bekommen, wird in jeder while-Schleife zuerst überprüft, ob pPosX und pPosY Randwerte sind
        //Erst dann wird dementsprechend überprüft, wie lang das Schiff ist
        while ( endeSchiffMX>0 && endeSchiffMX<spielfeld.length-1 ) {
            if(spielfeld[endeSchiffMX-1][pPosY].istWasserfeld()) {
                break;
            } else {
                endeSchiffMX--;
            }
        }
        while ( endeSchiffPX>=0 && endeSchiffPX<spielfeld.length-1 ) {
            if( spielfeld[endeSchiffPX+1][pPosY].istWasserfeld() ) {
                break;
            } else {
                endeSchiffPX++;
            }
        }
        while ( endeSchiffMY>0 && endeSchiffMY<spielfeld.length-1 ) {
            if( spielfeld[pPosX][endeSchiffMY-1].istWasserfeld() ) {
                break;
            } else {
                endeSchiffMY--;
            }
        }
        while ( endeSchiffPY>=0 && endeSchiffPY<spielfeld.length-1 ) {
            if( spielfeld[pPosX][endeSchiffPY+1].istWasserfeld() ) {
                break;
            } else {
                endeSchiffPY++;
            }
        }
        
        //Dann wird überprüft, ob das Schiff bereits komplett aufgedeckt ist
        for ( int i=endeSchiffMX; i<=endeSchiffPX; i++ ) {
            if ( !spielfeld[i][pPosY].istAufgedeckt() ) {
                play = false;
            }
        }
        for ( int i=endeSchiffMY; i<=endeSchiffPY; i++ ) {
            if ( !spielfeld[pPosX][i].istAufgedeckt() ) {
                play = false;
            }
        }
        
        //Dann den Sound abspielen, wenn play durch for-Schleifen true wurde
        if ( play ) {
            playSound(versenkt);
        }
    }

    /** 
     * 3 horizontale Schiffe und 3 vertikale Schiffe werden an eine zufällige Position 
     * auf dem Spielfeld platziert.
     */
    public void schiffPlatzieren()
    {  
        //ein neues Object der Klasse LeseSchiffPosition erzeugen, auf diesem werden später mehrere Methoden ausgeführt
        GetShipPosition schiffe = new GetShipPosition();
        
        //Zufallszahl erzeugen, die die Zeile der Schiffs-Datei definiert und diese einlesen
        int zufall = (int) ( (double) schiffe.gibZeilenAnzahl() * Math.random() );
        String zeile = schiffe.lesen(zufall);
        
        //Datei konvertieren & Schiffe plazieren
        
        for ( int i=0; i<10; i++ ){
            for ( int j=0; j<10; j++ ) {
                //xy = Integer.parseInt(""+ i + "" + j) : Die beiden Variablen i & j werden in einen String umgewandelt, hintereinander geschreiben und wieder in einen int umgewandelt
                //Aus z.B. i=8 und j=5 wird also 85
                int xy;
                if ( i == 0 ) {
                    xy = j;
                } else {
                    xy = Integer.parseInt(""+ i + "" + j);
                }
                
                if (zeile.charAt(xy) == '1') {
                    spielfeld[j][i].setzeSchiffsfeld(true); 
                } else {
                    spielfeld[j][i].setzeSchiffsfeld(false);
                }
            }
        }
    }
    
    /**
     * Sound-Wiedergabe
     * @param Sounddatei (siehe Konstruktor)
     */
    public void playSound(String pSound) {
        try{
            //read audio data
            InputStream audioSrc = getClass().getResourceAsStream(pSound);
            //add buffer for mark/reset support
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            //Open the InputStream bufferedIn...
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(bufferedIn);
            clip = AudioSystem.getClip();
            clip.open(inputStream);
            //With volume
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if(!on){ 
                gainControl.setValue(-1000.0f); // Reduce volume by 1000 decibels
            }
            clip.start();
        }
        catch(Exception e){ System.err.println("Got an Exception  when trying to play " + pSound + "! Probably it doesn't excist! However this is the exception:\n" + e + "\nContinue anyway..."); }
    }
    
    /**
     * Sound An/Aus
     * @param true(=an) / false (=aus)
     */
    public void toggleVolume(boolean pOn) {
        //Sound-effect
        if(pOn) {
            on = true;
            playSound(sound_on);
        } else {
            playSound(sound_off);
            on = false;
        }
    }
}//Ende der Klasse Spielfeld