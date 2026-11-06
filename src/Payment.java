import java.util.Objects;

public class Payment {

    private static int nextId = 1;
    private int id;
    private double amount;
    private String method;
    private String status;

    public Payment(double amount, String method) {
        this.id = nextId++;
        this.amount = amount;
        this.method = method;
        this.status = "Pending";
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void showPayment() {
        System.out.println("Payment ID: " + id + "\nPayment: " + amount + "\nMethod: " + method + "\nStatus: " + status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Payment)) return false;
        Payment payment = (Payment) obj;
        return id == payment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
