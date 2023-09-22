package utils;

import java.util.List;
public class Utilities {
    public static <T> void imprimirElementos(List<T> lista) {
        for (T elemento : lista) {
            System.out.print(elemento);
        }
    }
}
