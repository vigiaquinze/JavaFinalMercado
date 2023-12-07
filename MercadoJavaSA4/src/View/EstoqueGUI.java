package View;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Model.Estoque;
import javafx.event.ActionEvent;
import Control.EstoqueControl;

public class EstoqueGUI extends JPanel {
    private JTextField inputIdProduto;
    private JTextField inputNomeProduto;
    private JTextField inputQuantidade;
    private JTextField inputValorUnitario;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Estoque> estoque = new ArrayList<>();
    private int linhaSelecionada = -1;
    private JButton cadastrarButton, editarButton, apagarButton;
    public EstoqueGUI() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Quantidade");
        tableModel.addColumn("Valor Unitário");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        //criando os métodos de entrada de dados (input)
        inputIdProduto = new JTextField(10);
        inputNomeProduto = new JTextField(20);
        inputQuantidade = new JTextField(10);
        inputValorUnitario = new JTextField(10);
        cadastrarButton = new JButton("Cadastrar");
        editarButton = new JButton("Editar");
        apagarButton = new JButton("Apagar");
        //adicionando os inputs
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(inputIdProduto);
        inputPanel.add(new JLabel("Nome:"));
        inputPanel.add(inputNomeProduto);
        inputPanel.add(new JLabel("Quantidade:"));
        inputPanel.add(inputQuantidade);
        inputPanel.add(new JLabel("Valor:"));
        inputPanel.add(inputValorUnitario);
        inputPanel.add(cadastrarButton);
        inputPanel.add(editarButton);
        inputPanel.add(apagarButton);
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        //botões de eventos

        EstoqueControl operacoes = new EstoqueControl(estoque, tableModel, table);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                linhaSelecionada = table.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    inputIdProduto.setText((String) table.getValueAt(linhaSelecionada, 0));
                    inputNomeProduto.setText((String) table.getValueAt(linhaSelecionada, 1));
                    inputQuantidade.setText((String) table.getValueAt(linhaSelecionada, 2));
                    inputValorUnitario.setText((String) table.getValueAt(linhaSelecionada, 3));
                }
            }
        });
    }
}
