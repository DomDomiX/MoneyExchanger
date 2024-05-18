import javax.swing.*;

public class Mena {
    private String zeme;
    private String nazevMeny;
    private String kodMeny;
    private double kurz;
    private int mnozstvi;

    public Mena(String zeme, String nazevMeny, int mnozstvi, String kodMeny, double kurz){
        this.zeme = zeme;
        this.nazevMeny = nazevMeny;
        this.mnozstvi = mnozstvi;
        this.kodMeny = kodMeny;
        this.kurz = kurz;
    }

    public String getZeme() {
        return zeme;
    }

    public double getKurz() {
        return kurz;
    }

    public String getKodMeny() {
        return kodMeny;
    }
    public int getMnozstvi() {
        return mnozstvi;
    }
}
