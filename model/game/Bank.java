package alsid.model.game;

/**
 * Class for the bank of the game. The bank contains a bank of money that players get from in certain events.
 * The game ends when <code>money</code> falls below zero.
 */
public class Bank implements Payable {

    private double money;

    public Bank(int nPlayerCount)
    {
        money = 2500 * nPlayerCount;
    }

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

    public void setMoney(double money)
    {
        this.money = money;
    }

    public String toString()
    {
        return "The Bank";
    }
}
