package Control;

import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import Connection.EstoqueDAO;

public class EstoqueControl {
    private String idProduto;
    private String nomeProduto;
    private String quantidade;
    private boolean perecivel;
    public EstoqueControl(String idProduto, String nomeProduto, String quantidade, boolean perecivel) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.perecivel = perecivel;
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
    public boolean isPerecivel() {
        return perecivel;
    }
    public void setPerecivel(boolean perecivel) {
        this.perecivel = perecivel;
    }
    
}
