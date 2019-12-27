package br.gov.sp.fatec.tipo;

public enum TipoOperacaoEstoque {

    Entrada(1,"Entrada"),
    Saida(2, "Saida");

    int tipoOperacao;
    String nome;

    public int getTipoOperacao(){
        return this.tipoOperacao;
    }

    public String getNome(){
        return this.nome;
    }

    TipoOperacaoEstoque(int tipoOperacao, String nome){
        this.tipoOperacao = tipoOperacao;
        this.nome = nome;
    }

}
