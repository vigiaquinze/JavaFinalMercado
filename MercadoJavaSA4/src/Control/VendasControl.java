package Control;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.text.DecimalFormat;
import java.util.List;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import View.VendasGUI;

import Model.Cliente;
import Model.Vendas;

public class VendasControl {
        private VendasGUI vendasGUI;
        private Vendas vendasDAO;
        private JButton comprar, cadastrarCliente, listarClientes, finalizarCompra;
        private JTextField nomeProduto, idProduto, valorProduto, dataCompra, quantidadeVendida, cpfCliente;
        private DefaultTableModel tableModel;
        private JTable tabelaCompras;
        private List<Cliente> clientes;
        private double valorTotalCompra = 0.0;

        public VendasControl(VendasGUI vendasGUI, Vendas vendasDAO) {
                this.vendasGUI = vendasGUI;
                this.vendasDAO = vendasDAO;
        }

        public void adicionarCompraNaTabela() {

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

                tableModel.addRow(new Object[] { clienteNome + (cliente != null && cliente.isVip() ? " VIP" : ""),
                                produto, idProdutoText,
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

        public void cadastrarNovoCliente() {
                String nome = JOptionPane.showInputDialog(null, "Digite o nome do cliente:");
                String cpf = JOptionPane.showInputDialog(null, "Digite o CPF do cliente:");
                boolean vip = JOptionPane.showConfirmDialog(null, "O cliente é VIP?", "Cadastro de Cliente",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

                if (nome != null && cpf != null) {
                        clientes.add(new Cliente(nome, cpf, vip));
                        JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!", "Cadastro de Cliente",
                                        JOptionPane.INFORMATION_MESSAGE);
                }
        }

        public void listarClientesCadastrados() {
                StringBuilder clientesInfo = new StringBuilder("Clientes Cadastrados:\n");

                for (Cliente cliente : clientes) {
                        clientesInfo.append("CPF: ").append(cliente.getCpf()).append(", Nome: ")
                                        .append(cliente.getNome())
                                        .append(", VIP: ").append(cliente.isVip() ? "Sim" : "Não").append("\n");
                }

                JOptionPane.showMessageDialog(null, clientesInfo.toString(), "Lista de Clientes",
                                JOptionPane.INFORMATION_MESSAGE);
        }

        public void finalizarCompra() {
                valorTotalCompra = calcularValorTotalCompra();

                if (clienteEhVip()) {
                        valorTotalCompra *= 0.8;
                }

                limparCampos();
                exibirOpcoesPagamento();
        }

        public void exibirOpcoesPagamento() {
                JFrame pagamentoFrame = new JFrame("Opções de Pagamento");
                pagamentoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                pagamentoFrame.setSize(400, 200);

                JPanel pagamentoPanel = new JPanel();
                pagamentoPanel.setLayout(new GridLayout(4, 1));

                JLabel labelPagamento = new JLabel("Escolha a forma de pagamento:");
                JButton dinheiroButton = new JButton("Dinheiro");
                JButton debitoButton = new JButton("Cartão de Débito");
                JButton creditoButton = new JButton("Cartão de Crédito");

                dinheiroButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                        
                                processarPagamento("Dinheiro");
                                pagamentoFrame.dispose();
                        }
                });

                debitoButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                processarPagamento("Cartão de Débito");
                                pagamentoFrame.dispose();
                        }
                });

                creditoButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                processarPagamento("Cartão de Crédito");
                                pagamentoFrame.dispose();
                        }
                });

                pagamentoPanel.add(labelPagamento);
                pagamentoPanel.add(dinheiroButton);
                pagamentoPanel.add(debitoButton);
                pagamentoPanel.add(creditoButton);

                pagamentoFrame.getContentPane().add(pagamentoPanel);
                pagamentoFrame.setVisible(true);
        }

        public void processarPagamento(String formaPagamento) {
                JOptionPane.showMessageDialog(null, "Pagamento de R$ " + formatarMoeda(valorTotalCompra) +
                                " realizado com sucesso por " + formaPagamento, "Compra Finalizada",
                                JOptionPane.INFORMATION_MESSAGE);

                // Zera o valor total da compra após o pagamento ser processado
                valorTotalCompra = 0.0;

                // Limpa a tabela de compras
                tableModel.setRowCount(0);
        }

        public String formatarMoeda(double valor) {
                DecimalFormat formatoMoeda = new DecimalFormat("#,##0.00");
                return "R$ " + formatoMoeda.format(valor);
        }

        public String buscarNomeClientePorCpf(String cpf) {
                for (Cliente cliente : clientes) {
                        if (cliente.getCpf().equals(cpf)) {
                                return cliente.getNome();
                        }
                }
                return "";
        }

        public Cliente buscarClientePorCpf(String cpf) {
                for (Cliente cliente : clientes) {
                        if (cliente.getCpf().equals(cpf)) {
                                return cliente;
                        }
                }
                return null;
        }
}
