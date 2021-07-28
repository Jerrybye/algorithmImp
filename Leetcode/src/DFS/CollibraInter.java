package DFS;

import java.util.Objects;

public class CollibraInter {
    private int accountId;
    private String type;
    private double amount;
    private double SD;

    public int getAccountId() {
        return accountId;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getSD() {
        return SD;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollibraInter that = (CollibraInter) o;
        return accountId == that.accountId &&
                Double.compare(that.amount, amount) == 0 &&
                Double.compare(that.SD, SD) == 0 &&
                Objects.equals(type, that.type);
    }

    public int hashCode() {
        return Objects.hash(accountId, type, amount, SD);
    }
}
