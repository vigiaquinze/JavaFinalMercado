package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VendasGUI extends JPanel {
    private JButton comprar, buscarVip, cadastrarCliente, listarClientes, finalizarCompra;
    private JTextField nomeProduto, idProduto, valorProduto, dataCompra, quantidadeVendida, cpfCliente;
    private DefaultTableModel tableModel;
    private JTable tabelaCompras;
    private List<Cliente> clientes;
    private List<Produto> produtosComprados;
    private JTextField valorFinalField;

    public VendasGUI() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Inicializa a lista de clientes e produtos comprados
        clientes = new ArrayList<>();
        produtosComprados = new ArrayList<>();

        // Painel para botões
        JPanel buttonPanel = new JPanel();
        comprar = new JButton("Comprar");
        buscarVip = new JButton("Buscar Cliente VIP");
        cadastrarCliente = new JButton("Cadastrar Cliente");
        listarClientes = new JButton("Listar Clientes");
        finalizarCompra = new JButton("Finalizar Compra");

        buttonPanel.add(comprar);
        buttonPanel.add(buscarVip);
        buttonPanel.add(cadastrarCliente);
        buttonPanel.add(listarClientes);
        buttonPanel.add(finalizarCompra);

        // Painel para CPF do cliente
        JPanel cpfPanel = new JPanel(new GridLayout(1, 2));
        cpfPanel.add(new JLabel("CPF do Cliente"));
        cpfCliente = new JTextField(20);
        cpfPanel.add(cpfCliente);

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
        // Adiciona um ouvinte de clique duplo para incrementar a data ao dar dois cliques
        dataCompra.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    incrementarData();
                }
            }
        });
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

        // Campo para exibir o valor final
        JPanel valorFinalPanel = new JPanel(new GridLayout(1, 2));
        valorFinalPanel.add(new JLabel("Valor Final a Pagar"));
        valorFinalField = new JTextField(20);
        valorFinalField.setEditable(false);
        valorFinalPanel.add(valorFinalField);

        // Ação do botão "Comprar"
        comprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarCompraNaTabela();
            }
        });

        // Ação do botão "Buscar Cliente VIP"
        buscarVip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarClienteVipPorCpf();
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

        add(new JLabel("Estoque de Vendas"));
        add(buttonPanel);
        add(cpfPanel);
        add(new JLabel("Informações do Produto"));
        add(produtoPanel);
        add(new JLabel("Tabela de Compras"));
        add(scrollPane);
        add(valorFinalPanel);
    }

    private void finalizarCompra() {
        // Aplica desconto ao finalizar a compra
        double total = calcularTotalAPagar();
        // Exibe o valor final na interface
        DecimalFormat df = new DecimalFormat("#.##");
        valorFinalField.setText(df.format(total));
    }

    private double calcularTotalAPagar() {
        double total = 0;

        // Soma o valor total de cada produto
        for (Produto produto : produtosComprados) {
            total += produto.getTotal();
        }

        // Aplica descontos adicionais conforme necessário

        return total;
    }

    private void adicionarCompraNaTabela() {
        String produto = nomeProduto.getText();
        String idProdutoText = idProduto.getText();
        double valorProdutoDouble = Double.parseDouble(valorProduto.getText());
        String dataCompraText = dataCompra.getText();
        int quantidadeVendidaInt = Integer.parseInt(quantidadeVendida.getText());
        String clienteNome = buscarNomeClientePorCpf(cpfCliente.getText());

        // Calcula o valor total da compra
        double valorTotal = valorProdutoDouble * quantidadeVendidaInt;

        // Adiciona o produto à lista de produtos comprados
        produtosComprados.add(new Produto(produto, idProdutoText, valorProdutoDouble, quantidadeVendidaInt, valorTotal));

        // Atualiza a tabela com os produtos comprados
        atualizarTabelaCompras(clienteNome);
        

        // Limpa os campos após a compra
        nomeProduto.setText("");
        idProduto.setText("");
        valorProduto.setText("");
        dataCompra.setText("");
        quantidadeVendida.setText("");
        cpfCliente.setText("");
    }

    private void atualizarTabelaCompras(String clienteNome) {
        // Limpa a tabela
        tableModel.setRowCount(0);

        // Adiciona os produtos comprados à tabela
        for (Produto produto : produtosComprados) {
            tableModel.addRow(new Object[]{clienteNome, produto.getNome(), produto.getId(), produto.getPrecoUnitario(), produto.getQuantidade(), produto.getTotal()});
        }
    }

    private void buscarClienteVipPorCpf() {
        String cpf = cpfCliente.getText();
        Cliente cliente = buscarClientePorCpf(cpf);

        if (cliente != null) {
            if (cliente.isVip()) {
                JOptionPane.showMessageDialog(this, "Cliente VIP encontrado! O preço será reduzido pela metade.", "Cliente VIP", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cliente não é VIP.", "Cliente Não VIP", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Cliente não encontrado.", "Cliente Não Encontrado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void cadastrarNovoCliente() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do cliente:");
        String cpf = JOptionPane.showInputDialog(this, "Digite o CPF do cliente:");
        boolean vip = JOptionPane.showConfirmDialog(this, "O cliente é VIP?", "Cadastro de Cliente", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

        if (nome != null && cpf != null) {
            clientes.add(new Cliente(nome, cpf, vip));
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!", "Cadastro de Cliente", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String buscarNomeClientePorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente.getNome();
            }
        }
        return "";
    }

    private Cliente buscarClientePorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    private void listarClientesCadastrados() {
        StringBuilder clientesInfo = new StringBuilder("Clientes Cadastrados:\n");

        for (Cliente cliente : clientes) {
            clientesInfo.append("CPF: ").append(cliente.getCpf()).append(", Nome: ").append(cliente.getNome()).append("\n");
        }

        JOptionPane.showMessageDialog(this, clientesInfo.toString(), "Lista de Clientes", JOptionPane.INFORMATION_MESSAGE);
    }

    private void incrementarData() {
        // Obtém a data atual
        Date dataAtual = new Date();
        // Adiciona um dia à data atual
        dataAtual.setDate(dataAtual.getDate() + 1);
        // Atualiza o campo de data com a nova data
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dataCompra.setText(sdf.format(dataAtual));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VendasGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new VendasGUI());
        frame.pack();
        frame.setVisible(true);
    }

    private static class Cliente {
        private String nome;
        private String cpf;
        private boolean vip;

        public Cliente(String nome, String cpf, boolean vip) {
            this.nome = nome;
            this.cpf = cpf;
            this.vip = vip;
        }

        public String getCpf() {
            return cpf;
        }

        public String getNome() {
            return nome;
        }

        public boolean isVip() {
            return vip;
        }
    }

    private static class Produto {
        private String nome;
        private String id;
        private double precoUnitario;
        private int quantidade;
        private double total;

        public Produto(String nome, String id, double precoUnitario, int quantidade, double total) {
            this.nome = nome;
            this.id = id;
            this.precoUnitario = precoUnitario;
            this.quantidade = quantidade;
            this.total = total;
        }

        public String getNome() {
            return nome;
        }

        public String getId() {
            return id;
        }

        public double getPrecoUnitario() {
            return precoUnitario;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public double getTotal() {
            return total;
        }
    }
}
