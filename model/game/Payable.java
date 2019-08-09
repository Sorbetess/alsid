package alsid.model.game;

public interface Payable {

    String payTo(Payable payee, double amount);

    void add(double amount);

    boolean isBankrupt();

}
