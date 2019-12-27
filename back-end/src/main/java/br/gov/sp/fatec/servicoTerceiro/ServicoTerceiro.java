package br.gov.sp.fatec.servicoTerceiro;

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.localizacao.Localizacao;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "servico_terceiros")
public class ServicoTerceiro {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "servico_terceiros_id")
	@JsonView({ServicoTerceiroView.ServicoTerceiroResumido.class})
    private Integer id;
	
	@Column(name = "servico_terceiros_tipo")
	@JsonView({ServicoTerceiroView.ServicoTerceiroResumido.class})
    private String tipo;
	
	@Column(name = "servico_terceiros_quantidade")
	@JsonView({ServicoTerceiroView.ServicoTerceiroResumido.class})
    private Integer quantidade;
	
	@Column(name = "servico_terceiros_observacao")
	@JsonView({ServicoTerceiroView.ServicoTerceiroResumido.class})
    private String observacao;

	@Column(name = "regiao")
    private String regiao;

	public ServicoTerceiro(Integer id, String tipo, Integer quantidade, String observacao, Empresa empresa, Localizacao localizacao) {
		this.id = id;
		this.tipo = tipo;
		this.quantidade = quantidade;
		this.observacao = observacao;
		this.regiao = regiao;
	}

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

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public ServicoTerceiro () {}
}
