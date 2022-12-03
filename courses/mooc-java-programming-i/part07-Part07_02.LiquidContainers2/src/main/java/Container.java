public class Container {
    private int amount;

    public Container() {
        amount = 0;
    }

    public int contains() {
        return amount;
    }

    public void add(int amount) {
        if (amount >= 0) {
            int newAmount = this.amount + amount;
            if (newAmount > 100) {
                newAmount = 100;
            }
            this.amount = newAmount;
        }
    }

    public void remove(int amount) {
        if (amount >= 0) {
            int newAmount = this.amount - amount;
            if (newAmount < 0) {
                newAmount = 0;
            }
            this.amount = newAmount;
        }
    }

    public String toString() {
        return amount + "/100";
    }
}
