package Model;

public class Estoque {
    private String idProduto;
    private String nomeProduto;
    private String quantidade;
    private String valorUnitario;
    public Estoque(String idProduto, String nomeProduto, String quantidade, String valorUnitario) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }
    public String getIdProduto() {
        return idProduto;
    }
    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }
    public String getNomeProduto() {
        return nomeProduto;
    }
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    public String getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }
    public String getValorUnitario() {
        return valorUnitario;
    }
    public void setValorUnitario(String valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
}
