package View;

import javax.swing.*;
import java.util.*;
import java.util.List;
import javax.swing.table.*;

import java.awt.event.*;

import java.awt.*;
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
    private JButton cadastrarButton, atualizarButton, editarButton, apagarButton;

    public EstoqueGUI() {
        //Criando a tabela e adicionando as colunas
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

        //Adicionando os botões
        cadastrarButton = new JButton("Cadastrar");
        atualizarButton = new JButton("Atualizar");
        editarButton = new JButton("Editar");
        apagarButton = new JButton("Apagar");

        //Estilizando os botões
        cadastrarButton.setBackground(new Color(8, 24, 20));
        atualizarButton.setBackground(new Color(201, 168, 82));
        editarButton.setBackground(new Color(136, 114, 58));
        apagarButton.setBackground(new Color(239, 97, 49));

        cadastrarButton.setForeground(new Color(255, 255, 255));
        atualizarButton.setForeground(new Color(255, 255, 255));
        editarButton.setForeground(new Color(255, 255, 255));
        apagarButton.setForeground(new Color(255, 255, 255));

        cadastrarButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        atualizarButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        editarButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        apagarButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        //Adicionando os inputs
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(inputIdProduto);
        inputPanel.add(new JLabel("Nome:"));
        inputPanel.add(inputNomeProduto);
        inputPanel.add(new JLabel("Quantidade:"));
        inputPanel.add(inputQuantidade);
        inputPanel.add(new JLabel("Valor: R$"));
        inputPanel.add(inputValorUnitario);

        //Estilizando os inputs
        inputPanel.setBackground(new Color (139, 234, 210));
        inputIdProduto.setBackground(new Color (89, 184, 160));
        inputIdProduto.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        inputNomeProduto.setBackground(new Color (89, 184, 160));
        inputNomeProduto.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        inputQuantidade.setBackground(new Color (89, 184, 160));
        inputQuantidade.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        inputValorUnitario.setBackground(new Color (89, 184, 160));
        inputValorUnitario.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        //Adicionando os botões ao painel de inputs
        inputPanel.add(cadastrarButton);
        inputPanel.add(atualizarButton);
        inputPanel.add(editarButton);
        inputPanel.add(apagarButton);

        atualizarTabela();

        //Definindo o Layout
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        //Botões de eventos
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

        cadastrarButton.addActionListener(e -> {
            try {
                if (!inputIdProduto.getText().isEmpty() && !inputNomeProduto.getText().isEmpty()
                        && !inputQuantidade.getText().isEmpty() && !inputValorUnitario.getText().isEmpty()) {
                    if (operacoes.validarId(inputIdProduto.getText())
                            && operacoes.idJaCadastrada(inputIdProduto.getText())
                            && operacoes.validarValor(inputValorUnitario.getText())) {
                        // Se a id não estiver cadastrada, realiza o cadastro
                        operacoes.cadastrarProduto(inputIdProduto.getText(), inputNomeProduto.getText(),
                                inputQuantidade.getText(), inputValorUnitario.getText());

                        // Limpa os campos de entrada após a operação de cadastro
                        inputIdProduto.setText("");
                        inputValorUnitario.setText("");
                        inputNomeProduto.setText("");
                        inputQuantidade.setText("");

                    } else {
                        JOptionPane.showMessageDialog(null, "Preencha os campos corretamente e tente novamente.", null,
                                JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(inputPanel,
                            "Preencha os campos corretamente e tente novamente.");
                }
            } catch (Exception err) {
                System.out.println(err.getMessage());
                JOptionPane.showMessageDialog(null,
                        "Preencha os campos corretamente e tente novamente.", "ERRO",
                        JOptionPane.WARNING_MESSAGE);
            }

        });

        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                operacoes.atualizar(inputIdProduto.getText(), inputNomeProduto.getText(),
                        inputQuantidade.getText(), inputValorUnitario.getText());
                JOptionPane.showMessageDialog(getComponentPopupMenu(), "Produto atualizado.");
            }
        });

        editarButton.addActionListener(e -> {
            operacoes.editar(inputIdProduto.getText(), inputNomeProduto.getText(),
                    inputQuantidade.getText(), inputValorUnitario.getText());
            JOptionPane.showMessageDialog(getComponentPopupMenu(), "Produto editado.");
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
        tableModel.setRowCount(0);
        estoque = new EstoqueDAO().listarTodos();
        for (Estoque estoque : estoque) {
            tableModel.addRow(new Object[] { estoque.getIdProduto(), estoque.getNomeProduto(), estoque.getQuantidade(),
                    estoque.getValorUnitario() });
        }
    }
}
