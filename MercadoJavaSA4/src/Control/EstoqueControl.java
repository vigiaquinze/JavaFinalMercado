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
        Estoque produtos = new Estoque(idProduto.trim(), nomeProduto.trim().toUpperCase(), quantidade.trim(), valorUnitario.trim());
        new EstoqueDAO().cadastrar(idProduto.trim(), nomeProduto.trim().toUpperCase(), quantidade.trim(), valorUnitario.trim());
        estoque.add(produtos);
        atualizarTabela();
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
    
    public boolean idJaCadastrada(String idProduto) { // Verifica se a id do produto ja não foi cadastrada
        for (Estoque estoque : estoque) {
            if (estoque.getIdProduto().equalsIgnoreCase(idProduto)) {
                return false;
            }
        }
        return true;
    }

    public boolean validarValor(String valorUnitario) { // Verifica o texto digitado no inputValorUnitario tem apenas dígitos e não é gratuito.
        if (valorUnitario.matches("[0-9]+") && Integer.parseInt(valorUnitario) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarQuantidade(String quantidade) { // Verifica o texto digitado no inputQuantidade tem apenas dígitos e não é zero.
        if (quantidade.matches("[0-9]+") && Integer.parseInt(quantidade) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarId(String idProduto) { // Verifica o texto digitado no inputIdProduto tem apenas dígitos.
        if (idProduto.matches("[0-9]+")) {
            return true;
        } else {
            return false;
        }
    }
}
