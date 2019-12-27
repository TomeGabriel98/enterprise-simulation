package br.gov.sp.fatec.turno;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.gov.sp.fatec.empresa.Empresa;

@Entity
@Table(name = "turno")
public class Turno {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "turno_nome")
    private String nome;

    @Column(name = "turno_indice")
    private Integer indice;

    @JsonFormat(pattern="dd-MM-yyyy")
    @Column(name = "turno_data_inicio")
    private Date data_inicio;

    @Column(name = "turno_data_fim")
    private Date data_fim;
	
	@Column(name = "turno_bloqueado")
	private boolean turno_bloqueado;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Empresa> empresas;

	public Long getId() {
		return id;
	}

	public void setId(Long i) {
		this.id = i;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Empresa> getEmpresas() {
		return this.empresas;
	}

	public void setEmpresas(List<Empresa> empresa) {
		this.empresas = empresa;
	}
	
	public Integer getIndice() {
		return indice;
	}

	public void setIndice(Integer indice) {
		this.indice = indice;
	}

	public Date getData_inicio() {
		return data_inicio;
	}

	public void setData_inicio(Date data_inicio) {
		this.data_inicio = data_inicio;
	}

	public Date getData_fim() {
		return data_fim;
	}

	public void setData_fim(Date data_fim) {
		this.data_fim = data_fim;
	}

	public boolean getTurno_bloqueado() {
		return turno_bloqueado;
	}

	public void setTurno_bloqueado(boolean turno_bloqueado) {
		this.turno_bloqueado = turno_bloqueado;
	}
    
    
}
