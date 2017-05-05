/**
 * @author selms|t & voellmecke|j|p
 * @version 3.0 (2017|04|08)
 * on position on the pitch
 */
public class Field 
{
    private boolean istAufgedeckt;
    private boolean istWasserfeld;

    /**
     * Ein Feld erstellen. Zu Beginn ist das Feld verdeckt
     * und enthält kein Schiff.
     */
    public Field()
    {
        istAufgedeckt = false;
        istWasserfeld = true;
    }

    /**
     * Liefert den Status für die Ausgabe: "X" bei nicht
     * aufgedecktem Feld, "W" bei aufgedecktem Feld mit
     * Wasser, "S" bei aufgedecktem Feld mit Schiff.
     * 
     * @return Status X = verdeckt, sonst W = Wasser oder S = Schiff
     */
    public String gibStatus()
    {
        if( !this.istAufgedeckt )
        {
            return "X";
        }
        else if( this.istWasserfeld )
        {
            return "W";
        }
        else
        {
            return "S";
        }
    }

    /**
     * Ein Feld aufdecken.
     */
    public void aufdecken()
    {
        istAufgedeckt = true;
    }
    
    /** 
     * @return aufgedeckt true = das Feld wurde bereits aufgedeckt
     */
    public boolean istAufgedeckt()
    {
        return this.istAufgedeckt;
    }

    /**
     * @return wasserfeld true = Wasser, leider kein Schiff
     */
    public boolean istWasserfeld()
    {
        return this.istWasserfeld;
    }

    /** 
     * Auf dieses Feld wird ein Abschnitt des Schiffes platziert.
     * 
     * @param pIstSchiff true = hier wird ein Schiff platziert,
     *                   false = ein evtl vorhandenes Schiff wird entfernt
     */
    public void setzeSchiffsfeld(boolean pIstSchiff)
    {
        this.istWasserfeld = !pIstSchiff;
    }

}//Ende der Klasse Feld
