package br.gov.sp.fatec.caixa;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import br.gov.sp.fatec.armazem.Armazem;
import br.gov.sp.fatec.armazem.ArmazemRepository;
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.empresa.EmpresaRepository;
import br.gov.sp.fatec.estoque.Estoque;
import br.gov.sp.fatec.estoque.EstoqueRepository;
import br.gov.sp.fatec.produto.Produto;
import br.gov.sp.fatec.projetofatecb.models.*;
import br.gov.sp.fatec.tipo.TipoOperacaoCaixa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaixaServiceImpl implements CaixaService {

	@Autowired
	private CaixaRepository caixaRepository;

	@Autowired
	private ArmazemRepository armazemRepository;

	@Autowired
	private EstoqueRepository estoqueRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;

	@Override
	public CaixaMovimentacao salvar(CaixaMovimentacao caixaMovimentacao){
		return caixaRepository.save(caixaMovimentacao);
	}
	
	@Override
	public Empresa movimentaCaixa(Empresa empresa, Double valor, String tipo, Boolean ativo) {
		List<CaixaMovimentacao> movimentacoes = empresa.getCaixas();
		CaixaMovimentacao caixa = new CaixaMovimentacao();	
		if(empresa.getCaixa()>valor) {
			caixa.setAtivo(ativo);
			caixa.setData(new Date(System.currentTimeMillis()));
			caixa.setTipoOperacao(tipo);
			caixa.setValor(valor + empresa.getCaixa() );
			caixaRepository.save(caixa);
			movimentacoes.add(caixa);
			empresa.setCaixas(movimentacoes);
			empresa.setCaixa(empresa.getCaixa()-valor);
			empresaRepository.save(empresa);
			return empresa;
		}else {
			return null;
		}
		
	}
	
	
	public boolean isPodePagarEmprestimo(List<CaixaMovimentacao> listaCaixa, double valorEntrada){
		double valorSaida = 0.0;
		for(CaixaMovimentacao caixaMovimentacao : listaCaixa){
			valorSaida = valorSaida + caixaMovimentacao.getValor();
		}
		return valorEntrada < valorSaida;
	}

	public boolean isPodePegarEmprestimo(List<Armazem> armazem, double valorEntrada){
		double valorLimite = 0.0;
		for (Armazem arm : armazem) {
			Estoque estoque = estoqueRepository.getEstoqueByArmazemId(arm.getId());
			for (Produto produto : estoque.getListaProduto()) {
				valorLimite = valorLimite + produto.getValorCompra();
			}
		}
		return valorLimite > valorEntrada;
	}

	public CaixaMovimentacao Emprestimo(Empresa empresa, double valorEntrada){
		CaixaMovimentacao caixaMovimentacao = new CaixaMovimentacao();
		List<Armazem> armazem = empresa.getArmazem();
		if(isPodePegarEmprestimo(armazem, valorEntrada)){
			caixaMovimentacao.setValor(valorEntrada);
			caixaMovimentacao.setData(new Date(System.currentTimeMillis()));
			caixaMovimentacao.setTipoOperacao(TipoOperacaoCaixa.Emprestimo.getNome());
			caixaRepository.save(caixaMovimentacao);
		}
		return caixaMovimentacao;
	}

	public CaixaMovimentacao Pagamento(Empresa empresa, double valorEntrada){
		CaixaMovimentacao caixaMovimentacao = new CaixaMovimentacao();
		List<CaixaMovimentacao> listaCaixa = empresa.getCaixas();
		if(isPodePagarEmprestimo(listaCaixa, valorEntrada)){
			caixaMovimentacao.setValor(valorEntrada);
			caixaMovimentacao.setData(new Date(System.currentTimeMillis()));
			caixaMovimentacao.setTipoOperacao(TipoOperacaoCaixa.Pagamento.getNome());
			caixaRepository.save(caixaMovimentacao);
		}
		return caixaMovimentacao;
	}

	@Override
	public List<CaixaMovimentacao> buscaTodasCaixas(){
		List<CaixaMovimentacao> caixaMovimentacaos = caixaRepository.findAll();
		return caixaMovimentacaos;
	}

	public List<CaixaMovimentacao> buscaTodasCaixasPorEmpresaId(int parseInt) {
		Empresa empresa = empresaRepository.findById(parseInt).get();
		List<CaixaMovimentacao> caixaMovimentacaos = empresa.getCaixas();
		return caixaMovimentacaos;
	}


}
