import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ZmenaMen {
    // Mapa pro uložení měn podle jejich kódů
    private Map<String, Mena> menaMap = new HashMap<>();

    public ZmenaMen(String filePath) throws IOException {
        loadKurzy(filePath); // Načtení kurzů ze souboru
    }

    // Metoda pro načtení kurzů ze souboru
    private void loadKurzy(String filePath) throws IOException {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            // Přeskočení prvních dvou řádků
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Přeskočení řádku s datem
            }
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Přeskočení řádku s hlavičkami sloupců
            }

            // Čtení dat ze souboru
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                String zeme = parts[0];
                String nazevMeny = parts[1];
                int mnozstvi = Integer.parseInt(parts[2].replace(",", ""));
                String kodMeny = parts[3];
                double kurz = Double.parseDouble(parts[4].replace(",", "."));

                // Vytvoření objektu Mena a uložení do mapy
                Mena mena = new Mena(zeme, nazevMeny, mnozstvi, kodMeny, kurz);
                menaMap.put(kodMeny, mena);
            }
        }
    }

    // Metoda pro získání seznamu kódů měn
    public List<String> getCurrencyList() {
        List<String> formattedList = new ArrayList<>();
        for (Mena mena : menaMap.values()) {
            formattedList.add(mena.getKodMeny() + " - " + mena.getZeme());
        }
        return formattedList;
    }

    // Metoda pro provedení konverze mezi dvěma měnami
    public double convert(String fromCurrency, String toCurrency, double amount) {
        String fromCode = fromCurrency.split(" - ")[0];
        String toCode = toCurrency.split(" - ")[0];
        Mena fromMena = menaMap.get(fromCode);
        Mena toMena = menaMap.get(toCode);
        if (fromMena != null && toMena != null) {
            // Převod částky na CZK a následně na cílovou měnu
            double amountInCzk = (amount * fromMena.getKurz()) / fromMena.getMnozstvi();
            return (amountInCzk * toMena.getMnozstvi()) / toMena.getKurz();
        }
        return 0.0;
    }
}
