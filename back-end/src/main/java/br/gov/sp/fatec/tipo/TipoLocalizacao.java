package br.gov.sp.fatec.tipo;

public enum TipoLocalizacao {

	Norte(1, "Norte", 75000000),
	Nordeste(2, "Nordeste", 80000000),
	SulSudeste(3, "Sul/Sudeste", 120000000);
	
	private int id;
	private String nome;
	private int valor;
	
	public int getId() {
		return this.id;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public int getValor() {
		return this.valor;
	}
	
	private TipoLocalizacao(int id, String nome, int valor) {
		this.id = id;
		this.nome = nome;
		this.valor = valor;
	}
	
}
