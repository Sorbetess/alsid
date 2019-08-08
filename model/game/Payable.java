package alsid.model.game;

public interface Payable {

    public String payTo(Payable payee, double amount);

    public void add(double amount);

    public boolean isBankrupt();

}
