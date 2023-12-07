package View;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("Controle de Mercado");
        setDefaultCloseOperation(2);
        // adicionando abas do painel
        JTabbedPane abas = new JTabbedPane();
        abas.add("Estoque", new EstoqueGUI());
        abas.add("Vendas", new VendasGUI());
        abas.add("Clientes", new ClientesGUI());
        add(abas);
    }

    public void run() {
        pack();
        setVisible(true);
    }
}
