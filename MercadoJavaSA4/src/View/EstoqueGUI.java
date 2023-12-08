package View;

import javax.swing.*;
import java.util.*;
import java.util.List;
import javax.swing.table.*;

import java.awt.event.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;


import Model.Estoque;

import Control.EstoqueControl;
import Connection.EstoqueDAO;


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
        // criando os métodos de entrada de dados (input)
        inputIdProduto = new JTextField(10);
        inputNomeProduto = new JTextField(20);
        inputQuantidade = new JTextField(10);
        inputValorUnitario = new JTextField(10);

        cadastrarButton = new JButton("Cadastrar");
        editarButton = new JButton("Editar");
        apagarButton = new JButton("Apagar");
        // adicionando os inputs
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

        atualizarTabela();

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // botões de eventos

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

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                operacoes.cadastrarProduto(inputIdProduto.getText(), inputNomeProduto.getText(),
                        inputQuantidade.getText(), inputValorUnitario.getText());
                inputIdProduto.setText("");
                inputNomeProduto.setText("");
                inputQuantidade.setText("");
                inputValorUnitario.setText("");
                JOptionPane.showMessageDialog(getComponentPopupMenu(), "Produto cadastrado.");
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                operacoes.editarProduto(linhaSelecionada, inputIdProduto.getText(), inputNomeProduto.getText(),
                        inputQuantidade.getText(), inputValorUnitario.getText());
                JOptionPane.showMessageDialog(getComponentPopupMenu(), "Produto editado.");
            }
        });

        apagarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                operacoes.apagarProduto(inputIdProduto.getText());
                JOptionPane.showMessageDialog(getComponentPopupMenu(), "Produto removido.");
            }
        });
    }
    private void atualizarTabela() {
        estoque = new EstoqueDAO().listarTodos();
        tableModel.setRowCount(0);
        for (Estoque estoque : estoque) {
            tableModel.addRow(new Object[] { estoque.getIdProduto(), estoque.getNomeProduto(), estoque.getQuantidade(), estoque.getValorUnitario() });
        }
    }
}
