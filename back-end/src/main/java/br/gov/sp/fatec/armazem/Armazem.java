package br.gov.sp.fatec.armazem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.armazemLevel.ArmazemLevel;
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.localizacao.Localizacao;
import br.gov.sp.fatec.view.View;

@Table(name = "armazem")
@Entity
public class Armazem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "armazem_id")
    private Integer id;

//    @JoinColumn(name="armazem_level_id", referencedColumnName = "armazem_level_id")
//    @OneToOne
//    private ArmazemLevel armazem_level_id;

    @JsonView(View.UsuarioResumo.class)
	@Column(name = "armazem_regiao")
	private String regiao;
	
	@Column(name = "armazem_quantidade")
	private Integer quantidade;
	
	@Column
	private Integer quantidade_em_estque;
	
	@JsonView(View.Armazem.class)
	@OneToOne
	private Empresa empresa;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public ArmazemLevel getArmazem_level_id() {
//		return armazem_level_id;
//	}
//
//	public void setArmazem_level_id(ArmazemLevel armazem_level_id) {
//		this.armazem_level_id = armazem_level_id;
//	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getQuantidade_em_estque() {
		return quantidade_em_estque;
	}

	public void setQuantidade_em_estque(Integer quantidade_em_estque) {
		this.quantidade_em_estque = quantidade_em_estque;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
    

}
