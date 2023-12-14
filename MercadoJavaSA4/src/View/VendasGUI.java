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

// JPanel para lidar com a interface gráfica de vendas
public class VendasGUI extends JPanel {
    // Botões para várias ações
    private JButton comprar, cadastrarCliente, listarClientes, finalizarCompra, editarProduto, apagarProduto;

    // Campos de texto para entrada
    private JTextField nomeProduto, idProduto, valorProduto, dataCompra, quantidadeVendida, cpfCliente;

    // Modelo de tabela para exibir compras
    private DefaultTableModel tableModel;
    private JTable tabelaCompras;

    // Lista para armazenar informações do cliente
    private List<Cliente> clientes;

    // Variável para acompanhar o valor total da compra
    private double valorTotalCompra = 0.0;

    // Construtor para a interface gráfica de vendas
    public VendasGUI() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Inicializar a lista de clientes
        clientes = new ArrayList<>();

        // Painel para botões
        JPanel buttonPanel = new JPanel();
        comprar = new JButton("Comprar");
        cadastrarCliente = new JButton("Cadastrar Cliente");
        listarClientes = new JButton("Listar Clientes");
        finalizarCompra = new JButton("Finalizar Compra");
        editarProduto = new JButton("Editar Produto");
        apagarProduto = new JButton("Apagar Produto");

        buttonPanel.add(comprar);
        buttonPanel.add(cadastrarCliente);
        buttonPanel.add(listarClientes);
        buttonPanel.add(finalizarCompra);
        buttonPanel.add(editarProduto);
        buttonPanel.add(apagarProduto);

        // Painel para CPF do cliente
        JPanel cpfPanel = new JPanel(new GridLayout(1, 3));
        cpfPanel.add(new JLabel("CPF do Cliente"));
        cpfCliente = new JTextField(20);
        cpfPanel.add(cpfCliente);

        // Adicionar um JLabel para a palavra "VIP"
        JLabel labelVip = new JLabel("VIP");
        labelVip.setForeground(Color.RED); // Definir a cor como vermelha
        cpfPanel.add(labelVip);

        // Painel para informações do produto
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

        // Configurar a tabela para exibir compras
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Cliente");
        tableModel.addColumn("Produto");
        tableModel.addColumn("ID");
        tableModel.addColumn("Preço Unitário");
        tableModel.addColumn("Quantidade");
        tableModel.addColumn("Total");
        tabelaCompras = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaCompras);

//////////////////////////////////////////////////////////////////////////////////DESCOMENTE A PARTIDIR DAQUI////////////////////////////////////////////////////////////////////////

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

        // Ação do botão "Editar Produto"
        editarProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarProdutoNaTabela();
            }
        });

        // Ação do botão "Apagar Produto"
        apagarProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apagarProdutoDaTabela();
            }
        });

        // Adicionar componentes ao painel
        add(new JLabel("Estoque de Vendas"));
        add(buttonPanel);
        add(cpfPanel);
        add(new JLabel("Informações do Produto"));
        add(produtoPanel);
        add(new JLabel("Tabela de Compras"));
        add(scrollPane);
    }

    // Método para adicionar uma compra à tabela
    private void adicionarCompraNaTabela() {
        // Extrair informações do produto dos campos de entrada
        String produto = nomeProduto.getText();
        String idProdutoText = idProduto.getText();
        double valorProdutoDouble;
        try {
            // Analisar o valor do produto, lidando com possíveis problemas de formato numérico
            valorProdutoDouble = Double.parseDouble(valorProduto.getText().replace(",", "."));
        } catch (NumberFormatException e) {
            // Exibir uma mensagem de erro se o valor não for válido
            JOptionPane.showMessageDialog(this, "Digite um valor válido para o produto.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        int quantidadeVendidaInt;
        try {
            // Analisar a quantidade, lidando com possíveis problemas de formato numérico
            quantidadeVendidaInt = Integer.parseInt(quantidadeVendida.getText());
        } catch (NumberFormatException e) {
            // Exibir uma mensagem de erro se a quantidade não for válida
            JOptionPane.showMessageDialog(this, "Digite uma quantidade válida para o produto.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Recuperar informações do cliente
        String clienteNome = buscarNomeClientePorCpf(cpfCliente.getText());
        Cliente cliente = buscarClientePorCpf(cpfCliente.getText());

        // Aplicar um desconto de 20% se o cliente for VIP
        if (cliente != null && cliente.isVip()) {
            valorProdutoDouble *= 0.8;
        }

        // Calcular o valor total
        double valorTotal = valorProdutoDouble * quantidadeVendidaInt;

        // Adicionar a compra à tabela
        tableModel.addRow(
                new Object[] { clienteNome + (cliente != null && cliente.isVip() ? " VIP" : ""), produto, idProdutoText,
                        formatarMoeda(valorProdutoDouble), quantidadeVendidaInt, formatarMoeda(valorTotal) });

        // Adicionar o valor total ao total da compra
        valorTotalCompra += valorTotal;

        // Limpar os campos de entrada
        limparCampos();
    }

    // Método para calcular o valor total da compra
    private double calcularValorTotalCompra() {
        double total = 0.0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            // Analisar o valor total da tabela, lidando com possíveis problemas de formato numérico
            String valorTotalStr = tableModel.getValueAt(i, 5).toString();
            total += Double.parseDouble(valorTotalStr.replace("R$ ", "").replace(",", "."));
        }
        return total;
    }

    // Método para verificar se o cliente é VIP
    private boolean clienteEhVip() {
        Cliente cliente = buscarClientePorCpf(cpfCliente.getText());
        return cliente != null && cliente.isVip();
    }

    // Método para limpar os campos de entrada
    private void limparCampos() {
        nomeProduto.setText("");
        idProduto.setText("");
        valorProduto.setText("");
        dataCompra.setText("");
        quantidadeVendida.setText("");
        cpfCliente.setText("");
    }

    // Método para finalizar a compra
    private void finalizarCompra() {
        // Calcular o valor total da compra
        valorTotalCompra = calcularValorTotalCompra();

        // Aplicar um desconto de 20% se o cliente for VIP
        if (clienteEhVip()) {
            valorTotalCompra *= 0.8;
        }

        // Limpar os campos de entrada
        limparCampos();

        // Exibir opções de pagamento
        exibirOpcoesPagamento();
    }

    // Método para exibir opções de pagamento
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

    // Método para processar o pagamento
    private void processarPagamento(String formaPagamento) {
        JOptionPane.showMessageDialog(this, "Pagamento de R$ " + formatarMoeda(valorTotalCompra) +
                " realizado com sucesso por " + formaPagamento, "Compra Finalizada", JOptionPane.INFORMATION_MESSAGE);

        // Zerar o valor total da compra após o pagamento ser processado
        valorTotalCompra = 0.0;

        // Limpar a tabela de compras
        tableModel.setRowCount(0);
    }

    // Método para formatar um valor como moeda
    private String formatarMoeda(double valor) {
        DecimalFormat formatoMoeda = new DecimalFormat("#,##0.00");
        return "R$ " + formatoMoeda.format(valor);
    }

    // Método para buscar o nome do cliente pelo CPF
    private String buscarNomeClientePorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente.getNome();
            }
        }
        return "";
    }

    // Método para buscar um cliente pelo CPF
    private Cliente buscarClientePorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    // Método para listar clientes cadastrados
    private void listarClientesCadastrados() {
        StringBuilder clientesInfo = new StringBuilder("Clientes Cadastrados:\n");

        for (Cliente cliente : clientes) {
            clientesInfo.append("CPF: ").append(cliente.getCpf()).append(", Nome: ").append(cliente.getNome())
                    .append(", VIP: ").append(cliente.isVip() ? "Sim" : "Não").append("\n");
        }

        JOptionPane.showMessageDialog(this, clientesInfo.toString(), "Lista de Clientes",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Método para cadastrar um novo cliente
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

    // Método para editar um produto na tabela
    private void editarProdutoNaTabela() {
        // Implemente a lógica para editar os valores na tabela com base na linha
        // selecionada
        int selectedRow = tabelaCompras.getSelectedRow();
        if (selectedRow != -1) {
            // Exemplo: nomeProduto.setText(tableModel.getValueAt(selectedRow,
            // 1).toString());
            // idProduto.setText(tableModel.getValueAt(selectedRow, 2).toString());
            // ... (outros campos)
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para editar.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Método para apagar um produto da tabela
    private void apagarProdutoDaTabela() {
        // Implemente a lógica para remover a linha selecionada da tabela
        int selectedRow = tabelaCompras.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para apagar.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}





