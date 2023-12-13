package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GerenciamentoVenda {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Inicializa a Tela de Registro de Vendas
                TelaRegistroVendas registroVendas = new TelaRegistroVendas();
                registroVendas.setVisible(true);
            }
        });
    }
}

class TelaRegistroVendas extends JFrame {
    private JTextField codigoBarrasField;
    private JButton adicionarProdutoBtn, removerProdutoBtn, concluirCompraBtn;
    private JList<String> listaProdutos;
    private DefaultListModel<String> listaModel;
    private List<Produto> produtos;

    public TelaRegistroVendas() {
        setTitle("Registro de Vendas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        codigoBarrasField = new JTextField(15);
        adicionarProdutoBtn = new JButton("Adicionar Produto");
        removerProdutoBtn = new JButton("Remover Produto");
        concluirCompraBtn = new JButton("Concluir Compra");

        listaModel = new DefaultListModel<>();
        listaProdutos = new JList<>(listaModel);

        produtos = new ArrayList<>();

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Código de Barras:"));
        panel.add(codigoBarrasField);
        panel.add(adicionarProdutoBtn);
        panel.add(removerProdutoBtn);
        panel.add(new JScrollPane(listaProdutos));
        panel.add(concluirCompraBtn);

        add(panel);

        adicionarProdutoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarProduto();
            }
        });

        removerProdutoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerProduto();
            }
        });

        concluirCompraBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                concluirCompra();
            }
        });
    }

    private void adicionarProduto() {
        // Implemente a lógica para adicionar produtos à lista
        // a partir do código de barras.
        // Pode ser necessário interagir com uma classe de gerenciamento de produtos.

        // Exemplo:
        String codigoBarras = codigoBarrasField.getText();
        Produto produto = buscarProdutoPorCodigoBarras(codigoBarras);

        if (produto != null) {
            listaModel.addElement(produto.getNome());
            produtos.add(produto);
        } else {
            JOptionPane.showMessageDialog(this, "Produto não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        // Limpar o campo após adicionar o produto
        codigoBarrasField.setText("");
    }

    private void removerProduto() {
        // Implemente a lógica para remover produtos da lista.
        // Pode ser necessário interagir com uma classe de gerenciamento de produtos.

        // Exemplo:
        int selectedIndex = listaProdutos.getSelectedIndex();
        if (selectedIndex != -1) {
            listaModel.remove(selectedIndex);
            produtos.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para remover.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void concluirCompra() {
        // Implemente a lógica para abrir a Tela de Conclusão da Compra
        TelaConclusaoCompra telaConclusaoCompra = new TelaConclusaoCompra(produtos);
        telaConclusaoCompra.setVisible(true);

        // Fecha a Tela de Registro de Vendas
        dispose();
    }

    // Método fictício para buscar um produto pelo código de barras
    private Produto buscarProdutoPorCodigoBarras(String codigoBarras) {
        // Implemente a lógica de busca real aqui
        // Aqui, estamos usando uma lista fictícia de produtos para fins de exemplo.
        for (Produto produto : getListaProdutos()) {
            if (produto.getCodigoBarras().equals(codigoBarras)) {
                return produto;
            }
        }
        return null;
    }

    // Método fictício para obter uma lista fictícia de produtos
    private List<Produto> getListaProdutos() {
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto("123456", "Arroz", 2.5));
        produtos.add(new Produto("789012", "Feijão", 4.0));
        // Adicione mais produtos conforme necessário
        return produtos;
    }
}

class TelaConclusaoCompra extends JFrame {
    private JList<String> listaFinalProdutos;
    private DefaultListModel<String> listaFinalModel;
    private List<Produto> produtos;

    public TelaConclusaoCompra(List<Produto> produtos) {
        setTitle("Conclusão da Compra");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.produtos = produtos;

        listaFinalModel = new DefaultListModel<>();
        listaFinalProdutos = new JList<>(listaFinalModel);

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(new JScrollPane(listaFinalProdutos));
        panel.add(new JButton("Finalizar Compra"));

        add(panel);

        exibirListaProdutos();
    }

    private void exibirListaProdutos() {
        // Adiciona os produtos à lista final
        for (Produto produto : produtos) {
            listaFinalModel.addElement(produto.getNome() + " - R$ " + produto.getPreco());
        }
    }
}

class Produto {
    private String codigoBarras;
    private String nome;
    private double preco;

    public Produto(String codigoBarras, String nome, double preco) {
        this.codigoBarras = codigoBarras;
        this.nome = nome;
        this.preco = preco;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }
}
