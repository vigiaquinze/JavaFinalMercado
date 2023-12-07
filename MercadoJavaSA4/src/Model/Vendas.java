package Model;

public class Vendas {
    private String id;
    private String data;
    private String quantidadeVendida;
    private boolean valor;
    private int cpf;

    public Vendas(String id, String data, String quantidadeVendida, boolean valor, int cpf) {
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

    public boolean isValor() {
        return valor;
    }

    public void setValor(boolean valor) {
        this.valor = valor;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

}
