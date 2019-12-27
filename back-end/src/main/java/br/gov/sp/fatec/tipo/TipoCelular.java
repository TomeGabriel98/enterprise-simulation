package br.gov.sp.fatec.tipo;

public enum TipoCelular {
	
	Iphone("Iphone 11 plus", 4504.15),
	Samsung("Samsung Galaxy Note 10", 5399.10),
	Motorola("Motorola One Vision", 1599.00),
	Xiaomi("Xiaomi Mi9", 2369.90);
	
	private String nome;
	
	private Double valor;
	
	public String getNome() {
		return nome;
	}
	
	public Double getValor() {
		return this.valor;
	}
	
	TipoCelular(String nome, Double valor) {
		this.nome = nome;
		this.valor = valor;
	}
	

}
