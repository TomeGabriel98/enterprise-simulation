package br.gov.sp.fatec.empresa;

import java.util.*;

import br.gov.sp.fatec.caixa.CaixaMovimentacao;
import br.gov.sp.fatec.caixa.CaixaRepository;
import br.gov.sp.fatec.compra.Compra;
import br.gov.sp.fatec.compra.CompraRepository;
import br.gov.sp.fatec.compra.CompraServiceImpl;
import br.gov.sp.fatec.equipe.Equipe;
import br.gov.sp.fatec.erb.ERB;
import br.gov.sp.fatec.servicoTerceiro.ServicoTerceiro;

import br.gov.sp.fatec.venda.Venda;
import br.gov.sp.fatec.venda.VendaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmpresaServiceImpl implements EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private CaixaRepository caixaRepository;

	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private CompraServiceImpl compraService;

	@Autowired
	private VendaServiceImpl vendaService;

	@Override
	public Empresa salvar(Empresa empresa) {

//		Caixa caixa = new Caixa();
//
//		caixa.setValor(1000000000);
//		caixa.setEmpresa_id(empresa);
//
//		caixaRepository.save(caixa);

		return empresaRepository.save(empresa);
	}

	@Override
	public Empresa excluir(Empresa empresa) {
		empresaRepository.delete(empresa);
		return empresa;
	}
	
	@Override
	public Empresa editar(Empresa empresa) {
		if(empresaRepository.findById(empresa.getId()) != null){
			return empresaRepository.save(empresa);
		}
		return null;
	}

	@Override
	public Optional<Empresa> buscarPorId(Integer id) {
		return empresaRepository.findById(id);
	}

//	public Empresa buscaPorTurno(Long turnoId) {
//		Empresa empresa = empresaRepository.findEmpresaByAdminTurnoId(turnoId);
//		return empresa;
//	}


	public Collection<Empresa> todos() {
		Collection<Empresa> empresas = empresaRepository.findAll();
		return empresas;
	}
	
	public Collection<Empresa> todosUsuarios(){
		Collection<Empresa> empresas = empresaRepository.findByAutorizacoes_Nome("ROLE_USUARIO");
		return empresas;
	}
	public Collection<Empresa> todosAdmins(){
		Collection<Empresa> empresas = empresaRepository.findByAutorizacoes_Nome("ROLE_ADMIN");
		return empresas;
	}

	@Override
	public boolean isPodeContratar(Empresa empresa, double valorEntrada){
		return valorEntrada <= empresa.getCaixa();
	}

	public boolean retiraCaixa(Empresa empresa, double valor, String produto, Integer quantidade, String tipoOperacao){
		CaixaMovimentacao caixa = new CaixaMovimentacao();
		List<CaixaMovimentacao> listaCaixa = empresa.getCaixas();

		if(isPodeContratar(empresa, valor)){
			caixa.setValor(valor);
			caixa.setData(new Date(System.currentTimeMillis()));
			caixa.setTipoOperacao(tipoOperacao);
			caixa.setAtivo(true);
			caixa.setQuantidade(quantidade);
			caixa.setProduto(produto);
			caixaRepository.save(caixa);

			compraService.salvar(empresa, produto, quantidade, valor);
			
			listaCaixa.add(caixa);
			empresa.setCaixa(empresa.getCaixa()-valor);
			empresa.setCaixas(listaCaixa);
			empresaRepository.save(empresa);
			return true;
		} else{
			return false;
		}

	}

	public void aumentaCaixa(Empresa empresa, double valor, String produto, Integer quantidade, String tipoOperacao){
		CaixaMovimentacao caixa = new CaixaMovimentacao();
		List<CaixaMovimentacao> listaCaixa = empresa.getCaixas();

		caixa.setValor(caixa.getValor() + valor);
		caixa.setData(new Date(System.currentTimeMillis()));
		caixa.setTipoOperacao(tipoOperacao);
		caixa.setAtivo(true);
		caixa.setQuantidade(quantidade);
		caixa.setProduto(produto);
		caixaRepository.save(caixa);

		vendaService.salvar(empresa, produto, quantidade, valor);

		listaCaixa.add(caixa);
		empresa.setCaixa(empresa.getCaixa() + valor);
		empresa.setCaixas(listaCaixa);
		empresaRepository.save(empresa);
	}

	public Double getFaturamento(Integer empresaId){
		Empresa empresa = empresaRepository.findById(empresaId).get();
		Double valorVendas = 0.00;

		for(Venda v: empresa.getVendas())
			valorVendas += v.getValor();

		return valorVendas;
	}

	@Override
	public Double getCaixa(Integer empresaId){
		Empresa empresa = empresaRepository.findById(empresaId).get();

		return empresa.getCaixa();
	}

    @Override
	public Double getEmprestimos(Integer empresaId){
		Empresa empresa = empresaRepository.findById(empresaId).get();
		Double emprestimo = 0.00;

		for(CaixaMovimentacao emprestimos: empresa.getCaixas()){
			if(emprestimos.getTipoOperacao().equalsIgnoreCase("emprestimo"))
				emprestimo += emprestimos.getValor();
		}

		return emprestimo;
	}

    @Override
	public Double getCustos(Integer empresaId){
		Empresa empresa = empresaRepository.findById(empresaId).get();
		Double custos = 0.00;

		for(Equipe equipes: empresa.getEquipes()){
			custos += equipes.getSalarioMensal();
		}

		return custos;
	}

	public List<Compra> getCompras(Integer empresaId){
		Empresa empresa = empresaRepository.findById(empresaId).get();
		List<Compra> compras = empresa.getCompras();

		return compras;
	}


	public List<Venda> getVendas(Integer empresaId){
		Empresa empresa = empresaRepository.findById(empresaId).get();
		List<Venda> vendas = empresa.getVendas();

		return vendas;
	}

    @Override
	public Integer getEstoqueErbs(Integer empresaId){
		Empresa empresa = empresaRepository.findById(empresaId).get();
		Integer quantidadeERB = 0;

		for(ERB erbs: empresa.getERB()){
			quantidadeERB += erbs.getQuantidade();
		}

		return quantidadeERB;
	}

    @Override
	public Integer getEstoqueEquipes(Integer empresaId){
		Empresa empresa = empresaRepository.findById(empresaId).get();
		Integer quantidadeEquipes = 0;

		for(Equipe equipes: empresa.getEquipes()){
			quantidadeEquipes += equipes.getQuantidade();
		}

		return quantidadeEquipes;
	}

    @Override
	public Integer getEstoqueServicos(Integer empresaId){
		Empresa empresa = empresaRepository.findById(empresaId).get();
		Integer quantidadeServicosTerceiros = 0;

		for(ServicoTerceiro servicos: empresa.getServicoTerceiro()){
			quantidadeServicosTerceiros += servicos.getQuantidade();
		}

		return quantidadeServicosTerceiros;
	}

	@Override
	public Collection<Empresa> getEmpresas(){
		Collection<Empresa> empresas = empresaRepository.findAll();
    	return empresas;
	}

}
