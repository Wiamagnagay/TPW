package ticketmachine;

public class TicketMachine {
    private final int price;
    private int balance;
    private int total;

    public TicketMachine(int ticketCost) {
        if (ticketCost <= 0) {
            throw new IllegalArgumentException("Ticket price must be positive");
        }
        price = ticketCost;
        balance = 0;
        total = 0;
    }

    public int getPrice() {
        return price;
    }

    public int getTotal() {
        return total;
    }

    public int getBalance() {
        return balance;
    }

    public void insertMoney(int amount) {
        // ✅ CORRECTION 1 : Validation montant négatif
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot insert negative amount");
        }
        balance = balance + amount;
    }

    public int refund() {
        // ✅ CORRECTION 2 : Sauvegarder puis remettre à zéro
        int amountToRefund = balance;
        balance = 0;
        System.out.println("Je vous rends : " + amountToRefund + " centimes");
        return amountToRefund;
    }

    public boolean printTicket() {
        if (balance < price) {
            return false;
        }
        
        // Impression
        System.out.println("##################");
        System.out.println("# The BlueJ Line");
        System.out.println("# Ticket");
        System.out.println("# " + price + " cents.");
        System.out.println("##################");
        System.out.println();
        
        // ✅ CORRECTION 3 et 4 : Mettre à jour total et balance
        total = total + price;
        balance = balance - price;
        
        return true;
    }
}
