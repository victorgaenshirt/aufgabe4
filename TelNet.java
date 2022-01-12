package ws2021_aufgabe4;

import java.awt.*;
import java.util.*;
import java.util.List;

/*
Klasse zur Verwaltung von Telefonknoten mit (x,y)-Koordinaten und zur Berechnung eines minimal aufspannenden Baums
mit dem Algorithmus von Kruskal. Kantengewichte sind durch den Manhattan-Abstand definiert.
 */

public class TelNet {

    int lbg;
    int knotenNummer = 0;
    HashMap<TelKnoten, Integer> telKnotenMap; //alle Knoten
    List<TelVerbindung> minSpanTree; //Minimal aufspannender Baum

    //Legt ein neues Telefonnetz mit dem Leitungsbegrenzungswert lbg an.
    public TelNet(int lbg){
        this.lbg = lbg;
        this.telKnotenMap = new HashMap<>();
    }

    //Fügt einen neuen Telefonknoten mit Koordinate (x,y) dazu.
    public boolean addTelKnoten(int x, int y){
        TelKnoten telKnoten = new TelKnoten(x, y);

        if(telKnotenMap.containsKey(telKnoten)){
            return false;
        }

        telKnotenMap.put(telKnoten, knotenNummer);
        knotenNummer++;

        return true;
    }

    //Berechnet ein optimales Telefonnetz als minimal aufspannenden Baum mit dem Algorithmus von Kruskal.
    //Skript 9-17
    public boolean computeOptTelNet(){
        UnionFind forest = new UnionFind(knotenNummer);
        PriorityQueue<TelVerbindung> edges = new PriorityQueue<>(knotenNummer, Comparator.comparing(x -> x.c));//Vergleiche nach Verbindungskosten
        List<TelVerbindung> minSpanTree = new LinkedList<>();

        setUpPrioQueue(edges);

        while (forest.size() != 1 && !edges.isEmpty())
        {
            TelVerbindung verbindung = edges.poll();
            int t1 = forest.find(telKnotenMap.get(verbindung.u));//u => Anfangsknoten
            int t2 = forest.find(telKnotenMap.get(verbindung.v));//v => Endknoten

            if (t1 != t2)
            {
                forest.union(t1, t2);
                minSpanTree.add(verbindung);
            }
        }
        if (edges.isEmpty() && forest.size() != 1)
            return false;
        else
            this.minSpanTree = minSpanTree;

        return true;

    }

    //fügt alle verbindungen von telKnotenMap in die prioQueue ein
    private void setUpPrioQueue(PriorityQueue<TelVerbindung> edges) {
        for (var v : telKnotenMap.entrySet())
        {
            for (var u : telKnotenMap.entrySet())
            {
                if (v.equals(u)){
                    continue;
                }

                int cost = cost(v.getKey(), u.getKey());
                if (cost <= this.lbg){
                    edges.add(new TelVerbindung(v.getKey(), u.getKey(), cost));
                }
            }
        }

    }

    //berechnet kosten zwischen v und u
    private int cost(TelKnoten v, TelKnoten u)
    {
        return Math.abs(v.x - u.x) + Math.abs(v.y - u.y);
    }

    //Zeichnet das gefundene optimale Telefonnetz mit der Größe xMax*yMax in ein Fenster.
    public void drawOptTelNet(int xMax, int yMax, double penRadius, double squareRadius){
        StdDraw.setCanvasSize(1024, 1024);
        StdDraw.setPenRadius(penRadius);
        StdDraw.setXscale(0, xMax + 1);
        StdDraw.setYscale(0, yMax + 1);

        for (TelVerbindung minSpanTreeVerbindung : this.minSpanTree)
        {
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.filledSquare(minSpanTreeVerbindung.u.x, minSpanTreeVerbindung.u.y, squareRadius);
            StdDraw.filledSquare(minSpanTreeVerbindung.v.x, minSpanTreeVerbindung.v.y, squareRadius);

            StdDraw.setPenColor(Color.RED);
            StdDraw.line(minSpanTreeVerbindung.u.x, minSpanTreeVerbindung.u.y, minSpanTreeVerbindung.v.x, minSpanTreeVerbindung.u.y);
            StdDraw.line(minSpanTreeVerbindung.v.x, minSpanTreeVerbindung.v.y, minSpanTreeVerbindung.v.x, minSpanTreeVerbindung.u.y);
        }
        StdDraw.show(0);
    }

    //Fügt n zufällige Telefonknoten zum Netz dazu mit x-Koordinate aus [0,xMax] und y-Koordinate aus [0,yMax].
    public void generateRandomTelNet(int n, int xMax, int yMax){
        Random random = new Random();
        for(int i = 0; i<n;++i){
            addTelKnoten(random.nextInt(xMax + 1), random.nextInt(yMax + 1));//+1 damit xMax und yMax einschließlich sind
        }
    }

    //Liefert ein optimales Telefonnetz als Liste von Telefonverbindungen zurück.
    public List<TelVerbindung> getOptTelNet(){
        return this.minSpanTree;
    }

    //Liefert die Gesamtkosten eines optimalen Telefonnetzes zurück.
    public int getOptTelNetKosten(){
        int cost = 0;
        for (TelVerbindung tv: this.minSpanTree) {
            cost += tv.c;
        }
        return cost;
    }

    //Liefert die Anzahl der Knoten des Telefonnetzes zurück.
    public int size(){
        return telKnotenMap.size();
    }

    //tostring
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (TelVerbindung minSpanTreeVerbindung : this.minSpanTree)
        {
            sb.append(minSpanTreeVerbindung).append("\n");
        }
        return sb.toString();
    }

    //main
    public static void main(String[] args) {
//        abbildung3();
        abbildung1();
    }

    private static void abbildung1(){
        int n = 1000;
        int lbg = 100;
        TelNet telNet = new TelNet(lbg);
        telNet.generateRandomTelNet(n, n, n);

        telNet.computeOptTelNet();
        System.out.println(telNet);
        System.out.println("Cost: " + telNet.getOptTelNetKosten());
        telNet.drawOptTelNet(n, n, 0.001, 1);
    }

    private static void abbildung3(){
        TelNet telNet = new TelNet(7);

        telNet.addTelKnoten(1, 1);
        telNet.addTelKnoten(3, 1);
        telNet.addTelKnoten(4, 2);
        telNet.addTelKnoten(3, 4);
        telNet.addTelKnoten(7, 5);
        telNet.addTelKnoten(2, 6);
        telNet.addTelKnoten(4, 7);

        telNet.computeOptTelNet();
        System.out.println(telNet);
        System.out.println("Cost: " + telNet.getOptTelNetKosten());

        telNet.drawOptTelNet(7, 7,0.025, 0.5);
    }
}
