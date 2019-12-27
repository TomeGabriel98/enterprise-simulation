package br.gov.sp.fatec.tipo;

public enum TipoMercado {

	Premium(1, "Premium"),
	Intermediario(2, "Intermediário"),
	Entrada(3, "Entrada");
	
	Integer id;
	String nome;
	
	public Integer getId(){
		return this.id;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	private TipoMercado(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
}
