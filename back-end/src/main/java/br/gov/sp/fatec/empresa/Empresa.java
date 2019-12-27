package br.gov.sp.fatec.empresa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import br.gov.sp.fatec.compra.Compra;
import br.gov.sp.fatec.venda.Venda;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.armazem.Armazem;
import br.gov.sp.fatec.autorizacao.Autorizacao;
import br.gov.sp.fatec.caixa.CaixaMovimentacao;
import br.gov.sp.fatec.equipe.Equipe;
import br.gov.sp.fatec.localizacao.Localizacao;
import br.gov.sp.fatec.mercado.Mercado;
import br.gov.sp.fatec.servicoTerceiro.ServicoTerceiro;
import br.gov.sp.fatec.turno.Turno;
import br.gov.sp.fatec.view.View;
import br.gov.sp.fatec.erb.ERB;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Transactional
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "empresa")
public class Empresa implements UserDetails{

	private static final long serialVersionUID = 1L;

	@JsonView(View.UsuarioResumo.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "empresa_id")
	private Integer id;

	@JsonView(View.UsuarioResumo.class)
	@Column(name = "empresa_nome")
	private String nome;

	@JsonView(View.UsuarioResumo.class)
	@Column(name = "empresa_email")
	private String email;

	@Column(name = "empresa_senha")
	private String senha;

	@JsonView(View.UsuarioResumo.class)
	@JsonFormat(pattern="dd-MM-yyyy")
	@Column(name = "empresa_data_criacao")
	private Date dataDeCriacao;

	@JsonView(View.UsuarioResumo.class)
	@Column(name = "regiao")
	private String regiao;

	@JsonView(View.UsuarioResumo.class)
	@Column(name = "empresa_logo")
	private String logo;
	//private String urlLogo;

	@JsonView(View.UsuarioResumo.class)
	@Column
	private Double caixa;
	
	@JsonView(View.UsuarioResumo.class)
	@Column
	private Integer numero_turno;
	
	@JsonView(View.UsuarioResumo.class)
	@Column
	private Double emprestimo;
	
	@JsonView(View.UsuarioResumo.class)
	@Column
	private Boolean turno_travado;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Mercado> tipoMercado;

	@OneToMany(fetch = FetchType.LAZY)
	private List<ERB> erb;

	@OneToMany(fetch = FetchType.LAZY)
	private List<ServicoTerceiro> servicoTerceiro;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Compra> compras;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Venda> vendas;
	
	@JsonView(View.UsuarioResumo.class)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "empresa_autorizacao", 
	joinColumns = { @JoinColumn(name = "empresa_id") }, 
	inverseJoinColumns = { @JoinColumn(name = "autorizacao_id") })
	private List<Autorizacao> autorizacoes;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Armazem> armazens;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Equipe> equipes;

	
	@OneToMany(fetch = FetchType.LAZY)
	private List<CaixaMovimentacao> caixas;

	
	//@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
	//private List<Turno> adminTurno;


	public List<ServicoTerceiro> getServicoTerceiro() {
		return servicoTerceiro;
	}

	public void setServicoTerceiro(List<ServicoTerceiro> servicoTerceiro) {
		this.servicoTerceiro = servicoTerceiro;
	}

//	public List<Turno> getAdminTurno1() {
//		return adminTurno;
//	}
//
//	public void setAdminTurno(List<Turno> adminTurno) {
//		this.adminTurno = adminTurno;
//	}

	public List<CaixaMovimentacao> getCaixas() {
		return caixas;
	}

	public void setCaixas(List<CaixaMovimentacao> caixas) {
		this.caixas = caixas;
	}

	@JsonIgnore
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "ACESSO_USUARIOS")
	private Set<Integer> funcoes;

	public Empresa() {
	}

	public Empresa(Integer id, 
			String nome, 
			String email, 
			String senha, 
			Date dataDeCriacao,
			Double caixa,
			List<Localizacao> localizacao_id, 
			List<ServicoTerceiro> servicoTerceiros, 
			Set<Integer> funcoes, int tipoUsuario) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dataDeCriacao = dataDeCriacao;
		this.caixa = caixa;
		//this.dbFile = urlLogo;
		this.funcoes = funcoes;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDataDeCriacao() {
		return dataDeCriacao;
	}

	public void setDataDeCriacao(Date dataDeCriacao) {
		this.dataDeCriacao = dataDeCriacao;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public void setCaixa(Double caixa) {
		this.caixa = caixa;
	}

	public Double getCaixa() {
		return caixa;
	}

	public Double getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(Double emprestimo) {
		this.emprestimo = emprestimo;
	}

	public List<Equipe> getEquipes() {
		return equipes;
	}

	public void setEquipes(List<Equipe> equipes) {
		this.equipes = equipes;
	}

	public List<Armazem> getArmazem() {
		return armazens;
	}

	public void setArmazem(List<Armazem> armazens) {
		this.armazens = armazens;
	}

	public List<Autorizacao> getAutorizacoes() {
		return autorizacoes;
	}

	public void setAutorizacoes(List<Autorizacao> lista) {
		this.autorizacoes = lista;
	}

	public Set<Integer> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(Set<Integer> funcoes) {
		this.funcoes = funcoes;
	}

	public Integer getNumero_turno() {
		return numero_turno;
	}

	public void setNumero_turno(Integer numero_turno) {
		this.numero_turno = numero_turno;
	}

	public Boolean getTurno_travado() {
		return turno_travado;
	}

	public void setTurno_travado(Boolean turno_travado) {
		this.turno_travado = turno_travado;
	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}
	
	public void setTipoMercado(List<Mercado> tipoMercado) {
		this.tipoMercado = tipoMercado;
	}
	
	public List<Mercado> getTipoMercado() {
		return tipoMercado;
	}
	
	public void setERB(List<ERB> erb) {
		this.erb = erb;
	}
	
	public List<ERB> getERB() {
		return erb;
	}
	
	public void setServico(List<ServicoTerceiro> servicoTerceiro) {
		this.servicoTerceiro = servicoTerceiro;
	}
	
	public List<ServicoTerceiro> getServico(){
		return this.servicoTerceiro;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return senha;
	}

	@Override
	@JsonIgnore
	public String getUsername() {
		return email;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {		
		return this.autorizacoes;
	}

	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}
}
