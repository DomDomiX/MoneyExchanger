import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Scanner;

public class MainFrame extends JFrame {
    private JComboBox<String> prvniMenaCB;
    private JComboBox<String> druhaMenaCB;
    private JButton convertButton;
    private JLabel vysledekLabel;
    private ZmenaMen zmenaMen;
    private JTextField amountField;

    // Data o kurzech
    /*private String[] zeme;
    private String[] nazevMeny;
    private String[] kodMeny;
    private double[] kurz;*/

    public MainFrame(){
        // Nastavení vlastností okna
        setTitle("Currency Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setSize(700, 100); // Nastavení velikosti okna
        setResizable(false);

        try {
            // Inicializace objektu ZmenaMen a načtení dat o kurzech
            zmenaMen = new ZmenaMen("denni_kurz.txt");
        } catch (IOException e) {
            // Zobrazení chybové zprávy, pokud se nepodaří načíst data
            JOptionPane.showMessageDialog(this, "Error loading currency data: " + e.getMessage());
            System.exit(1);
        }

        // Vytvoření a naplnění ComboBoxů pomocí formátovaných dat o měnách
        prvniMenaCB = new JComboBox<>(zmenaMen.getCurrencyList().toArray(new String[0]));
        druhaMenaCB = new JComboBox<>(zmenaMen.getCurrencyList().toArray(new String[0]));

        amountField = new JTextField(10);

        // Přidání hodnot do combo boxů
        /*for (int i = 0; i < kodMeny.length; i++) {
            prvniMenaCB.addItem(kodMeny[i] + " - " + zeme[i]);
            druhaMenaCB.addItem(kodMeny[i] + " - " + zeme[i]);
        }*/

        // Vytvoření tlačítka pro konverzi a přidání akce pro jeho stisknutí
        convertButton = new JButton("Convert");
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });

        // Vytvoření labelu pro výsledek
        vysledekLabel = new JLabel("Result: ");

        // Přidání komponent do hlavního okna
        add(new JLabel("From: "));
        add(prvniMenaCB);
        add(new JLabel("To: "));
        add(druhaMenaCB);
        add(new JLabel("Amount: "));
        add(amountField);
        add(convertButton);
        add(vysledekLabel);

        setVisible(true); // Zobrazení okna
    }

    private void convertCurrency() {
        try {
            // Získání vybraných měn a částky z GUI
            String fromCurrency = prvniMenaCB.getSelectedItem().toString();
            String toCurrency = druhaMenaCB.getSelectedItem().toString();
            double amount = Double.parseDouble(amountField.getText());

            // Provedení konverze pomocí objektu ZmenaMen
            double convertedAmount = zmenaMen.convert(fromCurrency, toCurrency, amount);

            // Zobrazení výsledku konverze
            vysledekLabel.setText(String.format("Výsledek: %.2f %s", convertedAmount, toCurrency));
        } catch (NumberFormatException e) {
            // Zobrazení chybové zprávy, pokud je zadána neplatná částka
            JOptionPane.showMessageDialog(this, "Invalid amount.");
        }
    }

    // Metoda pro načtení dat o kurzech ze souboru
    /*private void loadExchangeRates(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));

            // Přeskočení prvních dvou řádků
            if (scanner.hasNextLine()) scanner.nextLine();
            if (scanner.hasNextLine()) scanner.nextLine();

            int count = 0;
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                count++;
            }

            // Resetování scanneru a inicializace polí
            scanner.close();
            scanner = new Scanner(new File(filename));

            if (scanner.hasNextLine()) scanner.nextLine();
            if (scanner.hasNextLine()) scanner.nextLine();

            zeme = new String[count];
            nazevMeny = new String[count];
            kodMeny = new String[count];
            kurz = new double[count];

            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

            int index = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length != 5) {
                    continue; // Přejít na další řádek, pokud nemá správný počet částí
                }
                zeme[index] = parts[0];
                nazevMeny[index] = parts[1];
                kodMeny[index] = parts[3];

                try {
                    kurz[index] = format.parse(parts[4]).doubleValue();
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(this, "Chyba při parsování kurzu: " + parts[4], "Chyba", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }

                index++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Soubor nebyl nalezen: " + filename, "Chyba", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }*/
}
