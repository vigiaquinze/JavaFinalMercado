import View.MainFrame;
import Connection.EstoqueDAO;

public class App {
    public static void main(String[] args) throws Exception {
        new EstoqueDAO().criaTabela();
        new MainFrame().run();
    }
}