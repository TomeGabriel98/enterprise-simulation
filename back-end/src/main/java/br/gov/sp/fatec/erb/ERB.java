package br.gov.sp.fatec.erb;

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.equipe.EquipeView;
import br.gov.sp.fatec.localizacao.Localizacao;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
@Table(name = "erb")
public class ERB {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "erb_id")
    private Integer id;

    @Column(name = "erb_quantidade")
    //@JsonView(EquipeView.ERBResumido.class)
    private Integer quantidade;

    @Column(name = "erb_observacao")
    //@JsonView(EquipeView.ERBResumido.class)
    private String observacao;

    @Column(name = "regiao")
    private String regiao;

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
}