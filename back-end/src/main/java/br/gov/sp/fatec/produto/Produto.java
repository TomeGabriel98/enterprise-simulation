package br.gov.sp.fatec.produto;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity(name = "produto")
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "produto_id")
	@JsonView({ProdutoView.ProdutoResumido.class})
    private Integer id;

    @Column(name = "produto_nome")
	@JsonView({ProdutoView.ProdutoResumido.class})
    private String nome;

    @Column(name = "produto_categoria")
	@JsonView({ProdutoView.ProdutoResumido.class})
    private Integer categoria;
    
    @Column(name = "valor_produto_compra")
	@JsonView({ProdutoView.ProdutoResumido.class})
    private double valorCompra;
    
    @Column(name = "valor_produto_venda")
	@JsonView({ProdutoView.ProdutoResumido.class})
    private double valorVenda;
    
    @Column
    private int quantidade;
    
	public Produto(Integer id, String nome, Integer categoria, double valorCompra, double valorVenda) {
		this.id = id;
		this.nome = nome;
		this.categoria = categoria;
		this.valorCompra = valorCompra;
		this.valorVenda = valorVenda;
	}

	public Produto(String nome, double valorCompra, double valorVenda) {
		super();
		this.nome = nome;
		this.valorCompra = valorCompra;
		this.valorVenda = valorVenda;
	}

	public Produto () {}

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

	public Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}

	public double getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(double valorCompra) {
		this.valorCompra = valorCompra;
	}

	public double getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(double valorVenda) {
		this.valorVenda = valorVenda;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	
}
