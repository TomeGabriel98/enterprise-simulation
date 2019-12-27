package br.gov.sp.fatec.compra;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.armazem.Armazem;
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.produto.Produto;
import br.gov.sp.fatec.view.View;

@Entity
@Table(name = "compra")
public class Compra {

	@JsonView(View.Compra.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compra_id")
    private Integer id;

	@JsonView(View.Compra.class)
    @ManyToOne
	private Produto produto;

	@JsonView(View.Compra.class)
    @Column(name = "compra_quantidade")
    private Integer quantidade;
	
	@JsonView(View.Compra.class)
	@Column
	private String tipo;

	@JsonView(View.Compra.class)
    @Column(name = "compra_valor")
	private Double valor;

	@JsonView(View.Compra.class)
    @Column(name = "compra_data")
    private Date data;
    
	@ManyToOne
	@JsonView(View.Compra.class)
    private Empresa empresa;
    
	@JsonView(View.Compra.class)
    @ManyToOne
    private Armazem armazem;
    /*// Aqui
    @JoinColumn(name="produto_id", referencedColumnName = "produto_id")
    @ManyToMany
    private List<Produto> produto_id;*/

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	/*public List<Produto> getProduto_id() {
		return produto_id;
	}

	public void setProduto_id(List<Produto> produto_id) {
		this.produto_id = produto_id;
	}*/

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Armazem getArmazem() {
		return armazem;
	}

	public void setArmazem(Armazem armazem) {
		this.armazem = armazem;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
