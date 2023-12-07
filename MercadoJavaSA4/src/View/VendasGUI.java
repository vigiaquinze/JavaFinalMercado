package View;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;

import Model.Clientes;

public class VendasGUI extends JPanel {
    private JButton cadastrar;
    private JTextField clienteNome, clienteCPF, cpfSearch;
    private List<Clientes> clientes;
    private JCheckBox vipStatus;
    private DefaultTableModel tableModel;
    private int linhaSelecionada = -1;

    public VendasGUI() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Painel para cadastro de clientes
        JPanel cadastroPanel = new JPanel(new GridLayout(4, 2));
        cadastroPanel.add(new JLabel("Nome"));
        clienteNome = new JTextField(20);
        cadastroPanel.add(clienteNome);

        cadastroPanel.add(new JLabel("CPF"));
        clienteCPF = new JTextField(20);
        cadastroPanel.add(clienteCPF);

        cadastroPanel.add(new JLabel("Status VIP"));
        vipStatus = new JCheckBox("VIP");
        cadastroPanel.add(vipStatus);

        // Painel para botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cadastrar = new JButton("Cadastrar"));

        // Painel para pesquisa de clientes
        JPanel pesquisaPanel = new JPanel(new GridLayout(1, 1));
        pesquisaPanel.add(new JLabel("CPF para pesquisa"));
        cpfSearch = new JTextField(2); // Ajuste o tamanho conforme necessário
        pesquisaPanel.add(cpfSearch);

        add(new JLabel("Histórico de Clientes"));
        add(cadastroPanel);
        add(buttonPanel);
        add(pesquisaPanel);
    }
}
