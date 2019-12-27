package br.gov.sp.fatec.localizacao;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.produto.ProdutoView;

import javax.persistence.*;
import java.util.List;

@Entity(name = "localizacao")
@Table(name = "localizacao")
public class Localizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "localizacao_id")
	@JsonView({LocalizacaoView.LocalizacaoResumida.class, ProdutoView.ProdutoCompleto.class})
    private Integer id;

    @Column(name = "localizacao_nome")
	@JsonView({LocalizacaoView.LocalizacaoResumida.class, ProdutoView.ProdutoCompleto.class})
    private String nome;
	
	@Column(name = "opera")
	private boolean opera;
	
	@Column(name = "matriz")
	private boolean matriz;

	public Localizacao(String nome) {
		this.nome = nome;
	}

	public Localizacao () {}

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
	
	public void setOpera(boolean opera) {
		this.opera = opera;
	}
	
	public boolean getOpera() {
		return this.opera;
	}
	
	public void setMatriz(boolean matriz) {
		this.matriz = matriz;
	}
	
	public boolean getMatriz() {
		return this.matriz;
	}
}
