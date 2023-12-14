package View;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("Controle de Mercado");
        setDefaultCloseOperation(2);
        // adicionando abas do painel
        JTabbedPane abas = new JTabbedPane();
        abas.add("Estoque", new EstoqueGUI());
        abas.add("Vendas", new VendasGUI());
        abas.setBackground(new Color(255, 215, 167));
        add(abas);
    }

    public void run() {
        pack();
        setVisible(true);
    }
}
