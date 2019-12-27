package br.gov.sp.fatec.mercado;

import java.util.Date;

import javax.persistence.*;

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.models.RelatorioCaixa;
import br.gov.sp.fatec.models.RelatorioEquipe;


@Entity
@Table(name = "mercado")
public class Mercado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nome")
	private String nome;
	
	@Column
	private boolean opera;

	@JoinColumn(name="empresa_id", referencedColumnName = "empresa_id")
    @ManyToOne
    private Empresa empresa;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public void setOpera(boolean opera) {
		this.opera = opera;
	}
	
	public boolean getOpera(){
		return this.opera;
	}
	


}
