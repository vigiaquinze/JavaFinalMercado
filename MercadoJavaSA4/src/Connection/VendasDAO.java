package Connection;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Model.Cliente;

import Control.VendasControl;

import View.VendasGUI;

public class VendasDAO {
    private JButton comprar, cadastrarCliente, listarClientes, finalizarCompra;
    private JTextField nomeProduto, idProduto, valorProduto, dataCompra, quantidadeVendida, cpfCliente;
    private DefaultTableModel tableModel;
    private JTable tabelaCompras;
    private List<Cliente> clientes;
    private double valorTotalCompra = 0.0;

    public VendasDAO(List<Cliente> clientes) {
        this.clientes = clientes;

    }

    private void adicionarCompraNaTabela() {
        String produto = nomeProduto.getText();
        String idProdutoText = idProduto.getText();
        double valorProdutoDouble;
        try {
            valorProdutoDouble = Double.parseDouble(valorProduto.getText().replace(",", "."));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Digite um valor válido para o produto.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        int quantidadeVendidaInt;
        try {
            quantidadeVendidaInt = Integer.parseInt(quantidadeVendida.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Digite uma quantidade válida para o produto.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        String clienteNome = buscarNomeClientePorCpf(cpfCliente.getText());
        Cliente cliente = buscarClientePorCpf(cpfCliente.getText());

        if (cliente != null && cliente.isVip()) {
            valorProdutoDouble *= 0.8; // Aplica desconto de 20% se o cliente for VIP
        }

        double valorTotal = valorProdutoDouble * quantidadeVendidaInt;

        tableModel.addRow(
                new Object[] { clienteNome + (cliente != null && cliente.isVip() ? " VIP" : ""), produto, idProdutoText,
                        formatarMoeda(valorProdutoDouble), quantidadeVendidaInt, formatarMoeda(valorTotal) });

        // Adiciona o valor total ao total da compra
        valorTotalCompra += valorTotal;

        limparCampos();
    }

    private double calcularValorTotalCompra() {
        double total = 0.0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String valorTotalStr = tableModel.getValueAt(i, 5).toString();
            total += Double.parseDouble(valorTotalStr.replace("R$ ", "").replace(",", "."));
        }
        return total;
    }

    private boolean clienteEhVip() {
        Cliente cliente = buscarClientePorCpf(cpfCliente.getText());
        return cliente != null && cliente.isVip();
    }

    private void limparCampos() {
        nomeProduto.setText("");
        idProduto.setText("");
        valorProduto.setText("");
        dataCompra.setText("");
        quantidadeVendida.setText("");
        cpfCliente.setText("");
    }

    private Cliente buscarClientePorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    private String formatarMoeda(double valor) {
        DecimalFormat formatoMoeda = new DecimalFormat("#,##0.00");
        return "R$ " + formatoMoeda.format(valor);
    }

        private String buscarNomeClientePorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente.getNome();
            }
        }
        return "";
    }
}