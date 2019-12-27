package br.gov.sp.fatec.tipo;

public enum TipoArmazem {

    Sudeste("Sudeste", 1000000, 700000),
    Nordeste("Nordeste", 1000000, 700000),
    Norte("Norte", 1000000, 700000);

    private String nome;
    private int valor;
    private double venda;

    public String getNome(){
        return this.nome;
    }

    public int getValor(){
        return this.valor;
    }

    public double getVenda() { return this.venda; }

    TipoArmazem(String nome, int valor, double venda){
        this.nome = nome;
        this.valor = valor;
        this.venda = venda;
    }
}
