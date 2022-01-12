package ws2021_aufgabe4;

//Klasse für eine Telefonverbindung.
public class TelVerbindung {
    public final TelKnoten u; //Anfangsknoten
    public final TelKnoten v; //Endknoten
    public final int c;//Verbindungskosten

    /*
    Legt eine neue Telefonverbindung von u nach v mit Verbindungskosten c an.
    u - Anfangsknoten.
    v - Endknoten.
    c - Verbindungskosten
     */
    public TelVerbindung(TelKnoten u, TelKnoten v, int c)
    {
        this.u = u; //Anfangsknoten
        this.v = v; //Endknoten
        this.c = c; //Verbindungskosten
    }

    @Override
    public String toString()
    {
        return this.u + " -> " + this.v + " : " + this.c;
    }

}
