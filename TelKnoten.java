package ws2021_aufgabe4;

import java.util.Objects;

//Klasse für einen Telefonknoten.
public class TelKnoten
{
    public final int x; //x-Koordinate
    public final int y; //y-Koordinate

    /*
    Legt einen neuen Telefonknoten mit den Koordinaten (x,y) an.
    x - x-Koordinate.
    y - y-Koordinate.
     */
    public TelKnoten(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString()
    {
        return "(" + this.x + "," + this.y + ")";
    }

    @Override
    public boolean equals(Object obj)
    {
        if ((obj instanceof TelKnoten) == false){
            return false;
        }

        TelKnoten knoten = (TelKnoten) obj;
        if(knoten.x == this.x && knoten.y == this.y){
            return true;
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.x, this.y);
    }
}

