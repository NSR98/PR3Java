package coprime;

import static coprime.NumberFileManager.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Coprime implements Iterable {
    private ArrayList<Integer> arr; // ArrayList de valores
    
    public Coprime(String path) {
        try {
            arr = new NumberFileManager().readFromFile(path);
        } catch (IOException ex) {
            System.out.println("Can't read data from file: " + path);
            arr = new ArrayList<>();
        }
    }
    
    public static void main(String[] args) {
        cleanResults();
        while (true) {
            System.out.println("Introduce la ruta del fichero a leer:");
            Scanner in = new Scanner(System.in);
            String path = in.nextLine();
            if (path.equals("exit")) break;

            Coprime c = new Coprime(path);
            c.showInput(); // Mostramos los valores de monedas leídos del fichero

            Iterator pi = c.iterator();
            double t = System.currentTimeMillis();
            while (pi.hasNext()) {
                boolean[] b = (boolean[]) pi.next();
                ArrayList<Integer> aux = c.parseCombinationValues(b);
                if (c.isCoprime(aux)) {
                    System.out.println(aux);
                }
            }
            t = System.currentTimeMillis() - t;
            writeResult(path, t);
            System.out.println("Process time:\t" + t/1000);
        }
    }
    
    /**
     * Devuelve verdadero si todos los números del ArrayList arr son coprimos
     * entre sí, y devuelve falso si no.
     */
    public boolean isCoprime(ArrayList<Integer> arr) {
        for (int i = 0; i < arr.size(); i++) {
            for (int j = i + 1; j < arr.size(); j++) {
                if (isCoprimeAux(arr.get(i), arr.get(j)) != 1) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Método auxiliar que devuelve el máximo común divisor de dos números.
     */
    private static int isCoprimeAux(int a, int b) {
        int aux;
        while (b != 0) {
            aux = a;
            a = b;
            b = aux % b;
        }
        return a;
    }
    
    /**
     * Devuelve un ArrayList de enteros con los elementos de arr representados
     * en la combinación comb.
     */
    public ArrayList<Integer> parseCombinationValues(boolean[] comb) {
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < comb.length; i++) {
            if (comb[i]) {
                res.add(arr.get(i));
            }
        }
        return res;
    }
    
    /**
     * Muestra por la salida estándar los valores de las monedas actuales.
     */
    public void showInput() {
        System.out.println("*** Input");
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(i + " => " + arr.get(i));
        }
        System.out.println("-------------------------------");
    }

    @Override
    public Iterator iterator() {
        return new PermutationIterator(arr.size());
    }
}

