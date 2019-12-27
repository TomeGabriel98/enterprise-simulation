package br.gov.sp.fatec.venda;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.armazem.Armazem;
import br.gov.sp.fatec.armazem.ArmazemService;
import br.gov.sp.fatec.caixa.CaixaMovimentacao;
import br.gov.sp.fatec.caixa.CaixaService;
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.equipe.Equipe;
import br.gov.sp.fatec.erb.ERB;
import br.gov.sp.fatec.erb.ERBService;
import br.gov.sp.fatec.estoque.Estoque;
import br.gov.sp.fatec.estoque.EstoqueService;
import br.gov.sp.fatec.produto.Produto;
import br.gov.sp.fatec.produto.ProdutoService;
import br.gov.sp.fatec.tipo.TipoOperacaoCaixa;
import br.gov.sp.fatec.turno.Turno;

@Service
public class VendaServiceImpl implements VendaService {
	
	@Autowired
	private ERBService erbServiceImpl;
	
	@Autowired
	private ArmazemService armazemServiceImpl;
	
	@Autowired
	private EstoqueService estoqueServiceImpl;
	
	@Autowired
	private ProdutoService produtoServiceImpl;
	
	@Autowired
	private CaixaService caixaServiceImpl;

	@Autowired
	private VendaRepository vendaRepository;

	@Override
	public Venda salvar(Empresa empresa, String produto, Integer quantidade, Double valor){
		Venda venda = new Venda();
		List<Venda> listaVenda = empresa.getVendas();

		Venda vendaCadastrada = buscarVendaAndEmpresaId(produto, empresa);

		if(vendaCadastrada == null){
			venda.setProduto(produto);
			venda.setQuantidade(quantidade);
			venda.setValor(valor);
			venda.setData(new Date(System.currentTimeMillis()));

			listaVenda.add(venda);
			empresa.setVendas(listaVenda);

			return vendaRepository.save(venda);
		} else{
			vendaCadastrada.setQuantidade(vendaCadastrada.getQuantidade() + quantidade);
			vendaCadastrada.setValor(vendaCadastrada.getValor() + quantidade);
			vendaCadastrada.setData(new Date(System.currentTimeMillis()));

			return vendaRepository.save(vendaCadastrada);
		}
	}
	
	private Produto atualizarQtdProduto(Produto produto, Integer quantidade) {
		//produto.setQuantidade(quantidade);
		
		return produtoServiceImpl.editar(produto);
		
	}
	
	private CaixaMovimentacao atualizarCaixa(Double valor) throws Exception {
		CaixaMovimentacao caixaMovimentacao = new CaixaMovimentacao();
		
		caixaMovimentacao.setTipoOperacao(TipoOperacaoCaixa.Venda.getNome());
		caixaMovimentacao.setData(new Date(System.currentTimeMillis()));
		caixaMovimentacao.setValor(valor);
		caixaMovimentacao.setAtivo(false);

		return caixaServiceImpl.salvar(caixaMovimentacao);
	}
	
	private HashMap<String, Double> mediaPrecoProduto(Turno turno) {
		
		HashMap<String, Double> precosMercado = new HashMap<String, Double>();
		HashMap<String, Integer> product_qtd = new HashMap<String, Integer>();
		
		Double product_price = 0.0;
		Integer product_count = 0;

		for (Empresa empresa : turno.getEmpresas()) {
			
			List<Armazem> armazens = empresa.getArmazem();
			
			for (Armazem armazem : armazens) {
				Estoque estoque = estoqueServiceImpl.buscaEstoque(armazem.getId()).get(0);
				List<Produto> produtos = estoque.getListaProduto();
				
				for (Produto produto : produtos) {
					
					if (precosMercado.get(produto.getNome()) != null) {
						product_price = precosMercado.get(produto.getNome());
					} 
					
					if (product_qtd.get(produto.getNome()) != null) {
						product_count = product_qtd.get(produto.getNome());
					} 
					
					precosMercado.put(produto.getNome(), produto.getValorVenda() + product_price);
					product_qtd.put(produto.getNome(), 1 + product_count);
					
					product_price = 0.0;
					product_count = 0;
				}
			}
		}
		
		for (String productName : precosMercado.keySet()) {
			precosMercado.put(productName, ( precosMercado.get(productName) /  product_qtd.get(productName)));
		}
		
		
		return precosMercado;
	}
	
	public CaixaMovimentacao gerarVendas(Turno turno) throws Exception {
		
		GeradorVendas geradorVendas = new GeradorVendas();
		

		HashMap<String, Double> precosMercado = this.mediaPrecoProduto(turno);

		for (Empresa empresa : turno.getEmpresas()) {
			
			Integer idEmpresa = empresa.getId();
			List<Equipe> equipes = empresa.getEquipes();
			List<ERB> erbs = empresa.getERB();
			List<Armazem> armazens = empresa.getArmazem();
			
			Integer qtdArmazem = armazens.size();
			
			Double valorVenda = 0.0;
			
			for (Armazem armazem : armazens) {
				
				Estoque estoque = estoqueServiceImpl.buscaEstoque(armazem.getId()).get(0);
				
				List<Produto> produtos = estoque.getListaProduto();
				
				int qtdProdutosEstoqueTotal = 0;
				
				for (Produto produto : produtos) {
					//qtdProdutosEstoqueTotal += produto.getQuantidade();
				}

				for (Produto produto : produtos) {
					//int qtdProdutosEstoque = produto.getQuantidade();
					Double precoVendaProduto = produto.getValorVenda();
					
					//Integer qtdVendas = geradorVendas.gerarVendas(idEmpresa, equipes, erbs, qtdArmazem, qtdProdutosEstoque,
							//qtdProdutosEstoqueTotal, precosMercado.get(produto.getNome()), precoVendaProduto);
					
					//this.atualizarQtdProduto(produto, produto.getQuantidade()-qtdVendas);
					
					//valorVenda = ( qtdVendas * precoVendaProduto ) + valorVenda;
					
				}

			}
			
			return this.atualizarCaixa(valorVenda);
			
		}
		
		return null;
	}

	public Venda buscarVendaAndEmpresaId(String produto, Empresa empresa){
		for(Venda v: empresa.getVendas()){
			if(v.getProduto().equalsIgnoreCase(produto))
				return v;
		}

		return null;
	}
}
