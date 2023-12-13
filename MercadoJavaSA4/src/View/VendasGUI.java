package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.Cliente;
import Model.Vendas;

public class VendasGUI extends JPanel {
    private JButton comprar, cadastrarCliente, listarClientes, finalizarCompra;
    private JTextField nomeProduto, idProduto, valorProduto, dataCompra, quantidadeVendida, cpfCliente;
    private DefaultTableModel tableModel;
    private JTable tabelaCompras;
    private List<Cliente> clientes;
    private double valorTotalCompra = 0.0;

    public VendasGUI() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Inicializa a lista de clientes
        clientes = new ArrayList<>();

        // Painel para botões
        JPanel buttonPanel = new JPanel();
        comprar = new JButton("Comprar");
        cadastrarCliente = new JButton("Cadastrar Cliente");
        listarClientes = new JButton("Listar Clientes");
        finalizarCompra = new JButton("Finalizar Compra");

        buttonPanel.add(comprar);
        buttonPanel.add(cadastrarCliente);
        buttonPanel.add(listarClientes);
        buttonPanel.add(finalizarCompra);

        // Painel para CPF do cliente
        JPanel cpfPanel = new JPanel(new GridLayout(1, 3)); // Aumente para 3 colunas
        cpfPanel.add(new JLabel("CPF do Cliente"));
        cpfCliente = new JTextField(20);
        cpfPanel.add(cpfCliente);

        // Adicione um JLabel para a palavra "VIP"
        JLabel labelVip = new JLabel("VIP");
        labelVip.setForeground(Color.RED); // Defina a cor vermelha
        cpfPanel.add(labelVip);

        // Painel para informações de produtos
        JPanel produtoPanel = new JPanel(new GridLayout(5, 2));
        produtoPanel.add(new JLabel("Nome do Produto"));
        nomeProduto = new JTextField(20);
        produtoPanel.add(nomeProduto);

        produtoPanel.add(new JLabel("ID do Produto"));
        idProduto = new JTextField(20);
        produtoPanel.add(idProduto);

        produtoPanel.add(new JLabel("Valor do Produto"));
        valorProduto = new JTextField(20);
        produtoPanel.add(valorProduto);

        produtoPanel.add(new JLabel("Data da Compra"));
        dataCompra = new JTextField(20);
        produtoPanel.add(dataCompra);

        produtoPanel.add(new JLabel("Quantidade Vendida"));
        quantidadeVendida = new JTextField(20);
        produtoPanel.add(quantidadeVendida);

        // Configurando a tabela para exibir as compras
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Cliente");
        tableModel.addColumn("Produto");
        tableModel.addColumn("ID");
        tableModel.addColumn("Preço Unitário");
        tableModel.addColumn("Quantidade");
        tableModel.addColumn("Total");
        tabelaCompras = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaCompras);

        // Ação do botão "Comprar"
        comprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarCompraNaTabela();
            }
        });

        // Ação do botão "Cadastrar Cliente"
        cadastrarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarNovoCliente();
            }
        });

        // Ação do botão "Listar Clientes"
        listarClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarClientesCadastrados();
            }
        });

        // Ação do botão "Finalizar Compra"
        finalizarCompra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizarCompra();
            }
        });

        // Dois cliques para inserir a data do dia
        dataCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    dataCompra.setText(dateFormat.format(new Date()));
                }
            }
        });

        add(new JLabel("Estoque de Vendas"));
        add(buttonPanel);
        add(cpfPanel);
        add(new JLabel("Informações do Produto"));
        add(produtoPanel);
        add(new JLabel("Tabela de Compras"));
        add(scrollPane);
    }

//ja foi    
    private void adicionarCompraNaTabela() {
        String produto = nomeProduto.getText();
        String idProdutoText = idProduto.getText();
        double valorProdutoDouble;
        try {
            valorProdutoDouble = Double.parseDouble(valorProduto.getText().replace(",", "."));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite um valor válido para o produto.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        int quantidadeVendidaInt;
        try {
            quantidadeVendidaInt = Integer.parseInt(quantidadeVendida.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite uma quantidade válida para o produto.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        String clienteNome = buscarNomeClientePorCpf(cpfCliente.getText());
        Cliente cliente = buscarClientePorCpf(cpfCliente.getText());

        if (cliente != null && cliente.isVip()) {
            valorProdutoDouble *= 0.8; // Aplica desconto de 20% se o cliente for VIP
        }

        double valorTotal = valorProdutoDouble * quantidadeVendidaInt;

        tableModel.addRow(new Object[]{clienteNome + (cliente != null && cliente.isVip() ? " VIP" : ""), produto, idProdutoText,
                formatarMoeda(valorProdutoDouble), quantidadeVendidaInt, formatarMoeda(valorTotal)});

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
    

//ja foi 
    private void finalizarCompra() {
        valorTotalCompra = calcularValorTotalCompra();

        if (clienteEhVip()) {
            valorTotalCompra *= 0.8;
        }

        limparCampos();
        exibirOpcoesPagamento();
    }
//--ja foi

//ja foi
    private void exibirOpcoesPagamento() {
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
//--ja foi

//ja foi
    private void processarPagamento(String formaPagamento) {
        JOptionPane.showMessageDialog(this, "Pagamento de R$ " + formatarMoeda(valorTotalCompra) +
                " realizado com sucesso por " + formaPagamento, "Compra Finalizada", JOptionPane.INFORMATION_MESSAGE);

        // Zera o valor total da compra após o pagamento ser processado
        valorTotalCompra = 0.0;

        // Limpa a tabela de compras
        tableModel.setRowCount(0);
    }
//--ja foi

//ja foi
    private String formatarMoeda(double valor) {
        DecimalFormat formatoMoeda = new DecimalFormat("#,##0.00");
        return "R$ " + formatoMoeda.format(valor);
    }
//--ja foi

//ja foi
    private String buscarNomeClientePorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente.getNome();
            }
        }
        return "";
    }
//--ja foi

//ja foi
    private Cliente buscarClientePorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }
//--ja foi

//ja foi
    private void listarClientesCadastrados() {
        StringBuilder clientesInfo = new StringBuilder("Clientes Cadastrados:\n");

        for (Cliente cliente : clientes) {
            clientesInfo.append("CPF: ").append(cliente.getCpf()).append(", Nome: ").append(cliente.getNome())
                    .append(", VIP: ").append(cliente.isVip() ? "Sim" : "Não").append("\n");
        }

        JOptionPane.showMessageDialog(this, clientesInfo.toString(), "Lista de Clientes",
                JOptionPane.INFORMATION_MESSAGE);
    }
//--ja foi

//ja foi
    private void cadastrarNovoCliente() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do cliente:");
        String cpf = JOptionPane.showInputDialog(this, "Digite o CPF do cliente:");
        boolean vip = JOptionPane.showConfirmDialog(this, "O cliente é VIP?", "Cadastro de Cliente",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

        if (nome != null && cpf != null) {
            clientes.add(new Cliente(nome, cpf, vip));
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!", "Cadastro de Cliente",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
//--ja foi
}
