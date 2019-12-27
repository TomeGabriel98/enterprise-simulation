package br.gov.sp.fatec.tipo;

public enum TipoERB{

    Sudeste("Sudeste", 688264, 1307, 481784.80),
    Nordeste("Nordeste", 688264, 1727, 481784.80),
    Norte("Norte", 688264, 2346, 481784.80);

    private String nome;
    private int valor;
    private int cliente;
    private double venda;

    public String getNome(){
        return this.nome;
    }

    public int getValor(){
        return this.valor;
    }

    public int getCliente(){
        return this.cliente;
    }

    public double getVenda() { return this.venda; }

    TipoERB(String nome, int valor, int cliente, double venda){
        this.nome = nome;
        this.valor = valor;
        this.cliente = cliente;
        this.venda = venda;
    }

}