package View;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class PrincipalGUI extends JFrame {
    // criação do tabbedPane para incluir as tabs
    private JTabbedPane jTPane;

    public PrincipalGUI() {
        jTPane = new JTabbedPane();
        add(jTPane);
        // criandos as tabs
        // tab1 carros
        VendasGUI tab1 = new VendasGUI();
        jTPane.add("Vendas", tab1);
        setBounds(100, 100, 600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // métodos para tornar a janela visível
    public void run() {
        this.setVisible(true);
    }

}