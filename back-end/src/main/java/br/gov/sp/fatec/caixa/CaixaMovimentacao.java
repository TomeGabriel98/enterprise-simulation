package br.gov.sp.fatec.caixa;

import java.util.Date;

import javax.persistence.*;

import br.gov.sp.fatec.empresa.Empresa;


@Entity
@Table(name = "caixa_movimentacao")
public class CaixaMovimentacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "caixa_id")
	private Integer id;

	@Column(name = "caixa_produto")
	private String produto;

	@Column(name = "caixa_quantidade")
	private Integer quantidade;

	@Column(name = "caixa_valor")
	private double valor;

	@Column(name = "caixa_data")
	private Date dataHora;

	@Column (name =  "tipoOperacao")
	private String tipoOperacao;

	@Column (name = "ativo")
	private boolean ativo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Date getData() {
		return dataHora;
	}

	public void setAtivo(boolean ativo){
		this.ativo = ativo;
	}

	public boolean isAtivo(){
		return ativo;
	}

	public void setData(Date dataHora) {
		this.dataHora = dataHora;
	}

	public String getTipoOperacao(){
		return this.tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao){
		this.tipoOperacao = tipoOperacao;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
}
