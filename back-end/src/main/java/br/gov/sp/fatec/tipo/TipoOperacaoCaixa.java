package br.gov.sp.fatec.tipo;

public enum TipoOperacaoCaixa {

    Pagamento(1,"Pagamento"),
    Emprestimo(2, "Emprestimo"),
    BNDS(3, "BNDS"),
    Venda(4, "Venda");

    int tipoOperacao;
    String nome;

    public int getTipoOperacao(){
        return this.tipoOperacao;
    }

    public String getNome(){
        return this.nome;
    }

    TipoOperacaoCaixa(int tipoOperacao, String nome){
        this.tipoOperacao = tipoOperacao;
        this.nome = nome;
    }

}
