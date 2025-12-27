import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class ExchangeRateProvider {
    private final Map<String, Double> rates = new HashMap<>();

    public ExchangeRateProvider() {
        // Base Currency is USD (1.0)
        rates.put("USD", 1.0);
        rates.put("EUR", 0.92);
        rates.put("GBP", 0.79);
        rates.put("INR", 83.30);
        rates.put("JPY", 151.60);
        rates.put("CAD", 1.35);
    }

    public double getRate(String currency) {
        return rates.getOrDefault(currency.toUpperCase(), -1.0);
    }

    public boolean isValid(String currency) {
        return rates.containsKey(currency.toUpperCase());
    }

    public void displayAvailableCurrencies() {
        System.out.println("Available Currencies: " + rates.keySet());
    }
}

class CurrencyConverter {
    private final ExchangeRateProvider rateProvider;

    public CurrencyConverter(ExchangeRateProvider rateProvider) {
        this.rateProvider = rateProvider;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("=== Static Currency Converter ===");
        rateProvider.displayAvailableCurrencies();

        System.out.print("\nEnter Base Currency (e.g., USD): ");
        String base = sc.next().toUpperCase();

        System.out.print("Enter Target Currency (e.g., INR): ");
        String target = sc.next().toUpperCase();

        if (!rateProvider.isValid(base) || !rateProvider.isValid(target)) {
            System.out.println("Error: One or both currency codes are not supported.");
            return;
        }

        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();

        double result = convert(base, target, amount);
        
        System.out.println("\n------------------------------");
        System.out.printf("Result: %.2f %s = %.2f %s\n", amount, base, result, target);
        System.out.println("------------------------------");
        
        sc.close();
    }

    private double convert(String from, String to, double amount) {
        // Formula: (Amount / From_Rate) * To_Rate
        double fromRate = rateProvider.getRate(from);
        double toRate = rateProvider.getRate(to);
        return (amount / fromRate) * toRate;
    }
}

public class Main {
    public static void main(String[] args) {
        ExchangeRateProvider provider = new ExchangeRateProvider();
        CurrencyConverter converter = new CurrencyConverter(provider);
        converter.start();
    }
}
