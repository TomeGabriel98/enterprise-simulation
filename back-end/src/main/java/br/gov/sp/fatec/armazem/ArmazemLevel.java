package br.gov.sp.fatec.armazem;

import javax.persistence.*;

@Table(name = "armazem_level")
@Entity
public class ArmazemLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "armazem_level_id")
    private Integer id;

    @Column(name = "armazem_level_custo")
    private double custo;

    @Column(name = "armazem_level_capacidade")
    private double capacidade;

    @Column(name = "armazem_level_lotes")
    private String lotes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getCusto() {
		return custo;
	}

	public void setCusto(double custo) {
		this.custo = custo;
	}

	public double getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(double capacidade) {
		this.capacidade = capacidade;
	}

	public String getLotes() {
		return lotes;
	}

	public void setLotes(String lotes) {
		this.lotes = lotes;
	}
    
    
}
