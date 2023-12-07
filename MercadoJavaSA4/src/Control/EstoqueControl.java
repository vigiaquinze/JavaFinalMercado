package Control;

import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import Connection.EstoqueDAO;
import Model.Estoque;
import View.EstoqueGUI;

public class EstoqueControl {
    // atributos
    private List<Estoque> estoque;
    private DefaultTableModel tableModel;
    private JTable table;

    // ctor
    public EstoqueControl(List<Estoque> estoque, DefaultTableModel tableModel, JTable table) {
        this.estoque = estoque;
        this.tableModel = tableModel;
        this.table = table;
       }

       
    // CRUD
    public void cadastrarProduto(String idProduto, String nomeProduto, String quantidade, String valorUnitario) {
        Estoque produtos = new Estoque(idProduto, nomeProduto, quantidade, valorUnitario);
        new EstoqueDAO().cadastrar(idProduto, nomeProduto, quantidade, valorUnitario);
        estoque.add(produtos);
        atualizarTabela();
    }

    public void editarProduto(int linhaSelecionada, String idProduto, String nomeProduto, String quantidade, String valorUnitario) {
        if (linhaSelecionada != -1) {
            Estoque produtos = new Estoque(idProduto, nomeProduto, quantidade, valorUnitario);
            new EstoqueDAO().atualizar(idProduto, nomeProduto, quantidade, valorUnitario);
            estoque.set(linhaSelecionada, produtos);
            atualizarTabela();
        }
    }

    public void apagarProduto(String idProduto) {
        new EstoqueDAO().apagar(idProduto);
        atualizarTabela();
    }

    public void atualizar(String idProduto, String nomeProduto, String quantidade, String valorUnitario) {
        new EstoqueDAO().atualizar(idProduto, nomeProduto, quantidade, valorUnitario);
        // Chama o método de atualização no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização
    }

    private void atualizarTabela() {
        estoque = new EstoqueDAO().listarTodos();
        tableModel.setRowCount(0);
        for (Estoque estoque : estoque) {
            tableModel.addRow(new Object[] { estoque.getIdProduto(), estoque.getNomeProduto(), estoque.getQuantidade(), estoque.getValorUnitario() });
        }
    }
}
