
public class Money {

    private final int euros;
    private final int cents;

    public Money(int euros, int cents) {

        if (cents > 99) {
            euros = euros + cents / 100;
            cents = cents % 100;
        }

        this.euros = euros;
        this.cents = cents;
    }

    public int euros() {
        return this.euros;
    }

    public int cents() {
        return this.cents;
    }

    public String toString() {
        String zero = "";
        if (this.cents < 10) {
            zero = "0";
        }

        return this.euros + "." + zero + this.cents + "e";
    }

    public Money plus(Money addition) {
        Money newMoney = new Money(
            euros + addition.euros(),
            cents + addition.cents()
        );

        // return the new Money object
        return newMoney;
    }

    public boolean lessThan(Money compared) {
        if (euros > compared.euros()) {
            return false;
        } else if (euros == compared.euros()) {
            return cents < compared.cents();
        } else {
            return true;
        }
    }

    public Money minus(Money decreaser) {
        if (lessThan(decreaser)) {
            return new Money(0, 0);
        } else if (cents < decreaser.cents()) {
            return new Money(
                euros - decreaser.euros() - 1,
                cents - decreaser.cents() + 100
            );
        } else {
            return new Money(
                euros - decreaser.euros(),
                cents - decreaser.cents()
            );
        }
    }
}
