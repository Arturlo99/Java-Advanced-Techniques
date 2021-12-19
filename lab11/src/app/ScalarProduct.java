package app;

public class ScalarProduct {
    static {
        System.loadLibrary("JNI");
    }
    {
        inputForm = new InputForm();
    }
    private InputForm inputForm;
    public Double[] a;
    public Double[] b;
    public Double c;

    public native Double multi01(Double[] a, Double[] b);
    // zakładamy, że po stronie kodu natywnego wyliczony zostanie iloczyn skalarny dwóch wektorów
    public native Double multi02(Double[] a);
    // zakładamy, że drugi atrybut będzie pobrany z obiektu przekazanego do metody natywnej
    public native void multi03();
    // zakładamy, że po stronie natywnej utworzone zostanie okienko na atrybuty,
    // a po ich wczytaniu i przepisaniu do a,b obliczony zostanie wynik.
    // Wynik powinna wyliczać metoda Javy multi04
    // (korzystająca z parametrów a,b i wpisująca wynik do c).

    public void multi04(){
        c = 0.0;
        int length = a.length;
        if (length > b.length){
            length = b.length;
        };
        for (int i = 0; i < length; i++) {
            c += a[i] * b[i];
        }
    // mnoży a i b, wynik wpisuje do c
    }
}
