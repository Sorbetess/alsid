package alsid.model.game;

/**
 * Class for the bank of the game. The bank contains a bank of money that players get from in certain events.
 * The game ends when <code>money</code> falls below zero.
 */
public class Bank implements Payable {

    private double money;

    @Override
    public String payTo(Payable payee, double amount) {
        money -= amount;
        payee.add(amount);

        return toString() + " paid $" + amount + " to " + payee.toString() + ".";
    }

    @Override
    public void add(double amount) {
        money += amount;
    }

    @Override
    public boolean isBankrupt() {
        return money <= 0;
    }

    public double getMoney() {
        return money;
    }

    public String toString()
    {
        return "The Bank";
    }
}
