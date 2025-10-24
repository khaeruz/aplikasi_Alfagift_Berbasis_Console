import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String email;
    private String password;
    private int points;
    private List<Transaction> history;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.points = 0;
        this.history = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void addTransaction(Transaction t) {
        history.add(t);
    }

    public List<Transaction> getHistory() {
        return history;
    }
}
