package br.gov.sp.fatec.emprestimo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.view.View;

@Entity
@Table(name = "emprestimo")
public class Emprestimo {
	
	@JsonView(View.Emprestimo.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@JsonView(View.Emprestimo.class)
    @Column
    private Integer rodada;
	
	@JsonView(View.Emprestimo.class)
    @Column
    private double adquirido;
    
	@JsonView(View.Emprestimo.class)
    @Column
    private double valor_total;
    
	@JsonView(View.Emprestimo.class)
    @Column
    private double valor_pago;
	
	@JsonView(View.Emprestimo.class)
	@Column
	private double valor_restante;

	@JsonView(View.Emprestimo.class)
    @Column
    private String status;
    
	@JsonView(View.Emprestimo.class)
    @OneToOne
    private Empresa empresa;

	public Emprestimo() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getRodada() {
		return rodada;
	}

	public void setRodada(Integer rodada) {
		this.rodada = rodada;
	}

	public double getAdquirido() {
		return adquirido;
	}

	public void setAdquirido(double adquirido) {
		this.adquirido = adquirido;
	}

	public double getValor_total() {
		return valor_total;
	}

	public void setValor_total(double valor_total) {
		this.valor_total = valor_total;
	}

	public double getValor_pago() {
		return valor_pago;
	}

	public void setValor_pago(double valor_pago) {
		this.valor_pago = valor_pago;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public double getValor_restante() {
		return valor_restante;
	}

	public void setValor_restante(double valor_restante) {
		this.valor_restante = valor_restante;
	}
    
    

}
