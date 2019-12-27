package br.gov.sp.fatec.tipo;

public enum TipoServicoTerceiro{

    PostoAtendimento("Posto de Atendimento", 27000, 50000);

    String nome;
    int cliente;
    int custo;

    public String getNome(){
        return this.nome;
    }

    public int getCliente(){
        return this.cliente;
    }

    public int getCusto(){
        return this.custo;
    }

    TipoServicoTerceiro(String nome, int cliente, int custo){
        this.nome = nome;
        this.cliente = cliente;
        this.custo = custo;
    }

}