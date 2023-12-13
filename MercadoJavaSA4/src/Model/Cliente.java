package Model;

public class Cliente {
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