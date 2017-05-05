//Import
import java.io.*;
import java.net.*;

/**
 * @author voellmecke|j|p
 * @version 3.0 (2017|04|26)
 * zum einlesen der schiffPositionen.txt
 */
class GetShipPosition {
    //Array (mit allen Zeilen der Datei) deklarieren
    String[] zeilen;
    public GetShipPosition() {
        try {
            //Datei öffnen
            BufferedReader br1 = new BufferedReader(new InputStreamReader(GetShipPosition.class.getResourceAsStream("/shipPositions.txt")));

            
            //Zeilen-Anzahl auslesen
            int count = 0;
            while(br1.readLine() != null) {
                //count erhöhen
                count++;
            }
            //zeilen[] mit count instanziieren
            zeilen = new String[count];
            
            //Datei schließen und erneut öffnen, um wieder zu Zeile 0 zu gelangen
            br1.close();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(GetShipPosition.class.getResourceAsStream("shipPositions.txt")));
            
            //Jede Zeile auslesen und im Array (mit der jeweiligen Zeilenangabe durch den Index) abspeichern
            String br2Line = "";
            int i = 0;
            while( (br2Line = br2.readLine()) != null) {
                //Array an der Position i mit dem Inhalt jeder Zeile initialisieren
                zeilen[i] = br2Line;
                i++;
            }
        
            //Datei schließen
            br2.close();
        } catch (Exception e) {System.err.println("Got an Exception: " + e + " when reading shipPositions.txt! Probably it doesn't exsist! \nThis is fatal! Quitting...");System.exit(-1);}
    }
    
    /**
     * Diese Methode gibt zurück, wie viele Zeilen die Textdatei hat
     * @return Anzahl der Zeilen der schiffPositionen.txt
     */
    public int gibZeilenAnzahl() {
        return zeilen.length;
    }
    
    /**
     * Methode zum lesen einer Zeile der Datei
     * @param Zeilennummer die gelesen werden soll
     * @return Inhalt der Zeile
     */
    public String lesen(int pZeile) {
        return zeilen[pZeile];
    }
}
