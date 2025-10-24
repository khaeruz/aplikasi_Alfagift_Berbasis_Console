import java.util.List;

public class Transaction {
    private List<CartItem> items;
    private double totalPrice;
    private int earnedPoints;

    public Transaction(List<CartItem> items, double totalPrice, int earnedPoints) {
        this.items = items;
        this.totalPrice = totalPrice;
        this.earnedPoints = earnedPoints;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getEarnedPoints() {
        return earnedPoints;
    }

    public void printTransaction() {
        System.out.println("\n--- Rincian Transaksi ---");
        for (CartItem item : items) {
            System.out.println(item.getProduct().getName() + " x" + item.getQuantity() + 
                               " = Rp" + item.getSubtotal());
        }
        System.out.println("Total: Rp" + totalPrice);
        System.out.println("Poin Diperoleh: " + earnedPoints);
    }
}
