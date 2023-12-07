package Model;

public class Vendas {
    private String id;
    private String data;
    private String quantidadeVendida;
    private String valor;
    private int cpf;

    public Vendas(String id, String data, String quantidadeVendida, String valor, int cpf) {
        this.id = id;
        this.data = data;
        this.quantidadeVendida = quantidadeVendida;
        this.valor = valor;
        this.cpf = cpf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(String quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    

}
