package br.gov.sp.fatec.projetofatecb.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.gov.sp.fatec.empresa.Empresa;

@Entity
@Table(name = "tipo_mercado")
public class TipoMercado {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_mercado_id")
    private Integer id;
	
	@Column(name = "tipo_mercado_nome")
    private String nome;
	
	@JoinColumn(name="empresa_id", referencedColumnName = "empresa_id")
    @ManyToMany 
    private List<Empresa> empresa_id;

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

	public List<Empresa> getEmpresa_id() {
		return empresa_id;
	}

	public void setEmpresa_id(List<Empresa> empresa_id) {
		this.empresa_id = empresa_id;
	}
	
	

}
