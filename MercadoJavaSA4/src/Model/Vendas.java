package Model;

public class Vendas {
    private String id;
    private String data;
    private String quantidadeVendida;
    private String valor;

    public Vendas(String id, String data, String quantidadeVendida, String valor) {
        this.id = id;
        this.data = data;
        this.quantidadeVendida = quantidadeVendida;
        this.valor = valor;
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    

}
