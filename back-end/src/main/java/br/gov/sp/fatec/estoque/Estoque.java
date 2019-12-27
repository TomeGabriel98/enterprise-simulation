package br.gov.sp.fatec.estoque;

import java.util.List;

import javax.persistence.*;

import br.gov.sp.fatec.armazem.Armazem;
import br.gov.sp.fatec.produto.Produto;

@Entity
@Table(name = "estoque")
public class Estoque {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estoque_id")
    private Integer id;
	
	@Column(name = "quantidade")
	private Integer quantidade;

	@JoinColumn(name="armazem_id", referencedColumnName = "armazem_id")
    @OneToOne
    private Armazem armazem;

    @OneToMany
    private List<Produto> listaProduto;

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

	public Armazem getArmazem_id() {
		return armazem;
	}

	public void setArmazem_id(Armazem armazem_id) {
		this.armazem = armazem_id;
	}

	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}

	
}
