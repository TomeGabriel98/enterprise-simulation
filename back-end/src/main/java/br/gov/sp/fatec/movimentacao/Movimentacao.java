package br.gov.sp.fatec.movimentacao;

import java.sql.Date;

import javax.persistence.*;

import br.gov.sp.fatec.estoque.Estoque;

@Entity
@Table(name = "movimentacao")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movimentacao_id")
    private Integer id;

    @Column(name = "movimentacao_tipo_operacao")
    private String operacao;

    @Column(name = "movimentacao_quantidade")
    private Integer quantidade;

    @Column(name = "movimentacao_data_hora")
    private Date data;

    @JoinColumn(name="estoque_id", referencedColumnName = "estoque_id")
    @OneToOne
    private Estoque estoque;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
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

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }
}
