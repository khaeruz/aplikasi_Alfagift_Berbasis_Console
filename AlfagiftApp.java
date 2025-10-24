import java.util.*;

public class AlfagiftApp {
    private Scanner scanner = new Scanner(System.in);
    private List<User> users = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private User currentUser;
    private List<CartItem> cart = new ArrayList<>();

    public AlfagiftApp() {
        // Daftar produk awal
        products.add(new Product("P001", "Susu Ultra 1L", 18000, 10));
        products.add(new Product("P002", "Roti Coklat", 7000, 5));
        products.add(new Product("P003", "Air Mineral 600ml", 4000, 8));
    }

    public void run() {
        int choice;
        do {
            System.out.println("\n=== Selamat Datang di Alfagift Console ===");
            System.out.println("1. Login");
            System.out.println("2. Daftar");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> login();
                case 2 -> register();
                case 3 -> System.out.println("Terima kasih telah menggunakan Alfagift Console!");
                default -> System.out.println("Pilihan tidak valid.");
            }
        } while (choice != 3);
    }

    private void register() {
        System.out.print("Nama: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        users.add(new User(name, email, password));
        System.out.println("Registrasi berhasil! Silakan login.");
    }

    private void login() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                currentUser = u;
                System.out.println("Login berhasil. Selamat datang, " + u.getName() + "!");
                mainMenu();
                return;
            }
        }
        System.out.println("Login gagal. Email atau password salah.");
    }

    private void mainMenu() {
        int choice;
        do {
            System.out.println("\n=== Menu Utama ===");
            System.out.println("1. Lihat Produk");
            System.out.println("2. Lihat Keranjang");
            System.out.println("3. Checkout");
            System.out.println("4. Lihat Riwayat & Poin");
            System.out.println("5. Logout");
            System.out.print("Pilih menu: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> showProducts();
                case 2 -> viewCart();
                case 3 -> checkout();
                case 4 -> viewHistory();
                case 5 -> System.out.println("Logout berhasil!");
                default -> System.out.println("Pilihan tidak valid.");
            }
        } while (choice != 5);
    }

    private void showProducts() {
        System.out.println("\n--- Daftar Produk ---");
        for (Product p : products) {
            System.out.println(p.getCode() + " - " + p.getName() + " | Rp" + p.getPrice() + " | Stok: " + p.getStock());
        }
        System.out.print("Masukkan kode produk untuk ditambahkan ke keranjang (atau '0' untuk kembali): ");
        String code = scanner.nextLine();
        if (code.equals("0")) return;

        for (Product p : products) {
            if (p.getCode().equalsIgnoreCase(code)) {
                System.out.print("Jumlah: ");
                int qty = scanner.nextInt();
                scanner.nextLine();

                if (qty > p.getStock()) {
                    System.out.println("Stok tidak mencukupi!");
                } else {
                    cart.add(new CartItem(p, qty));
                    System.out.println("Produk berhasil ditambahkan ke keranjang!");
                }
                return;
            }
        }
        System.out.println("Produk tidak ditemukan.");
    }

    private void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Keranjang kosong.");
            return;
        }
        System.out.println("\n--- Isi Keranjang ---");
        for (CartItem item : cart) {
            System.out.println(item.getProduct().getName() + " x" + item.getQuantity() + " = Rp" + item.getSubtotal());
        }
    }

    private void checkout() {
        if (cart.isEmpty()) {
            System.out.println("Keranjang kosong.");
            return;
        }

        double total = 0;
        for (CartItem item : cart) {
            total += item.getSubtotal();
        }

        int earnedPoints = (int) (total / 10000);
        System.out.println("\nTotal belanja: Rp" + total);
        System.out.println("Poin yang diperoleh: " + earnedPoints);
        System.out.print("Lanjutkan pembayaran? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            for (CartItem item : cart) {
                item.getProduct().reduceStock(item.getQuantity());
            }

            currentUser.addPoints(earnedPoints);
            Transaction t = new Transaction(new ArrayList<>(cart), total, earnedPoints);
            currentUser.addTransaction(t);
            cart.clear();
            System.out.println("Checkout berhasil! Terima kasih telah berbelanja.");
        }
    }

    private void viewHistory() {
        System.out.println("\n=== Riwayat Transaksi ===");
        if (currentUser.getHistory().isEmpty()) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        for (Transaction t : currentUser.getHistory()) {
            t.printTransaction();
        }
        System.out.println("Total poin Anda: " + currentUser.getPoints());
    }
}
