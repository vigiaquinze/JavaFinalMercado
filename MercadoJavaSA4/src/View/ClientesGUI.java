package View;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;

import Model.Clientes;

public class ClientesGUI extends JPanel {
    private JButton cadastrar, apagar, editar;
    private JTextField clintNome, clintCPF;
    private List<Clientes> clientes;
    private JTable table;
    private DefaultTableModel tableModel;
    private int linhaSelecionada = -1;

    public ClientesGUI() {
        super();
        // entrada de dados
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Histórico de Clientes"));
        JPanel inputPanel = new JPanel();

        inputPanel.setLayout(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Nome"));
        clintNome = new JTextField(20);
        inputPanel.add(clintNome);

        inputPanel.setLayout(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Nome"));
        clintCPF = new JTextField(20);
        inputPanel.add(clintCPF);

        add(inputPanel);
        JPanel botoes = new JPanel();
        botoes.add(cadastrar = new JButton("Cadastrar"));
        botoes.add(editar = new JButton("Editar"));
        botoes.add(apagar = new JButton("Apagar"));
        add(botoes);
        // tabela de clientes
        JScrollPane jSPane = new JScrollPane();
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Nome", "Endereço", "Contato", "CPF" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);
    }
}