package ws2021_aufgabe4;

import java.util.Arrays;

public class UnionFind
{
    private int p[];
    private int size;

    public UnionFind(int n)
    {
        p = new int[n];
        for (int i = 0; i < p.length; i++)
            p[i] = -1;
        size = n;
    }

    //Skript 9-24
    public int find(int e)
    {
        //wenn e < 0 => Dann ist e eine Wurzel
        while (p[e] >= 0)
            e = p[e];
        return e;
    }

    //Skript 9-26 => union by height
    public void union(int s1, int s2)
    {
        if (p[s1] >= 0 || p[s2] >= 0)
            return;
        if (s1 == s2)
            return;
        if (-p[s1] < -p[s2]) //Höhe von s1 < Höhe von s2
            p[s1] = s2;
        else
        {
            if (-p[s1] == -p[s2])
                p[s1]--; //Höhe von s1 erhöht sich um 1
            p[s2] = s1;

        }
        size--; //dekrementieren weil 2 Bäume zusammengefügt wurden
    }

    public int size()
    {
        return this.size;
    }

    public static void main(String[] args)
    {
        UnionFind u = new UnionFind(10);

        System.out.println("Anzahl der Partionierungen " + u.size());
        System.out.println();

        u.union(0, 1);
        System.out.println("Anzahl der Partionierungen " + u.size());
        System.out.println("1 befindet sich in " + u.find(1));
        System.out.println();

        u.union(3, 2);
        System.out.println("Anzahl der Partionierungen " + u.size());
        System.out.println();

        u.union(4, 5);
        System.out.println("Anzahl der Partionierungen " + u.size());
        System.out.println();

        u.union(3, 4);
        System.out.println("Anzahl der Partionierungen " + u.size());
        System.out.println("5 befindet sich in " + u.find(5));
        System.out.println();

        u.union(0, 3);
        System.out.println("Anzahl der Partionierungen " + u.size());
        System.out.println("1 befindet sich in " + u.find(1));
        System.out.println();

        u.union(7, 8);
        System.out.println("Anzahl der Partionierungen " + u.size());
        System.out.println();

        // u.union(7, 0); // throws IllegalArgumentException
        u.union(7, 3);
        System.out.println("Anzahl der Partionierungen " + u.size());
        System.out.println("8 befindet sich in " + u.find(8));
        System.out.println();
    }
}
