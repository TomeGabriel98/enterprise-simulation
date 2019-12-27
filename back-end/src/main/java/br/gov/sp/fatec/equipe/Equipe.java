package br.gov.sp.fatec.equipe;

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.localizacao.Localizacao;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
@Table(name = "equipes")
public class Equipe {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipe_id")
	@JsonView(EquipeView.EquipeResumido.class)
    private Integer id;
	
	@Column(name = "equipe_tipo")
	@JsonView(EquipeView.EquipeResumido.class)
    private String tipo;

    @Column(name = "equipe_quantidade")
	@JsonView(EquipeView.EquipeResumido.class)
    private Integer quantidade;

    @Column(name = "equipe_observacao")
	@JsonView(EquipeView.EquipeResumido.class)
    private String observacao;
    
    @Column(name="equipe_salario_mensal")
    private Integer salarioMensal;
    
    @Column(name="equipe_valor_contratacao")
    private Integer contratacao;

//    @JoinColumn(name="empresa_id", referencedColumnName = "empresa_id")
//    @ManyToOne
//	@JsonView(EquipeView.EquipeResumido.class)
//    private Empresa empresa;

    @Column(name="regiao")
    private String regiao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	
//
//	public Empresa getEmpresa_id() {
//		return empresa;
//	}
//
//	public void setEmpresa_id(Empresa empresa_id) {
//		this.empresa = empresa_id;
//	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}
//
//	public Empresa getEmpresa_id() {
//		return empresa;
//	}
//
//	public void setEmpresa_id(Empresa empresa_id) {
//		this.empresa = empresa_id;
//	}

	public Integer getSalarioMensal() {
		return salarioMensal;
	}

	public void setSalarioMensal(Integer salarioMensal) {
		this.salarioMensal = salarioMensal;
	}

	public Integer getContratacao() {
		return contratacao;
	}

	public void setContratacao(Integer contratacao) {
		this.contratacao = contratacao;
	}

    public Equipe () {}

	public Equipe(Integer id, String tipo, Integer quantidade, String observacao, String regiao) {
		this.id = id;
		this.tipo = tipo;
		this.quantidade = quantidade;
		this.observacao = observacao;
		this.regiao = regiao;
	}
}
