package Control;

import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import Connection.VendasDAO;

public class VendasControl {
    private String id;
    private String data;
    private String quantidadeVendida;
    public VendasControl(String id, String data, String quantidadeVendida) {
        this.id = id;
        this.data = data;
        this.quantidadeVendida = quantidadeVendida;
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
    
}
