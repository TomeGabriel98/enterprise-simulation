package br.gov.sp.fatec.compra;

import br.gov.sp.fatec.armazem.Armazem;
import br.gov.sp.fatec.caixa.CaixaMovimentacao;
import br.gov.sp.fatec.caixa.CaixaServiceImpl;
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.empresa.EmpresaRepository;
import br.gov.sp.fatec.produto.Produto;
import br.gov.sp.fatec.produto.ProdutoServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CompraServiceImpl implements CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private EmpresaRepository empresaRepository;
    
    @Autowired
    private ProdutoServiceImpl produtoService;
    
    @Autowired
    private CaixaServiceImpl caixaService;
    
    @Override
    public List<Compra> fazerCompra(Empresa empresa, Produto produto, double valorVenda, int quantidade, Armazem armazem){
    	produto.setValorVenda(valorVenda);
    	produtoService.salvar(produto);
    	Compra compra = new Compra();
    	compra.setData(new Date());
    	compra.setEmpresa(empresa);
    	compra.setProduto(produto);
    	compra.setQuantidade(quantidade);
    	compra.setArmazem(armazem);
    	double valor = produto.getValorCompra();
    	compra.setValor(valor * quantidade);
    	compraRepository.save(compra);
    	
    	List<CaixaMovimentacao> caixas = empresa.getCaixas();
    	CaixaMovimentacao caixa = new CaixaMovimentacao();
    	caixa.setAtivo(false);
    	caixa.setData(new Date());
    	caixa.setTipoOperacao("(-) Compra");
    	caixa.setValor(valor * quantidade);
    	caixaService.salvar(caixa);
    	caixas.add(caixa);
    	empresa.setCaixas(caixas);
    	empresaRepository.save(empresa);
    	List<Compra> compras = compraRepository.findCompraByEmpresa_Id(empresa.getId());
    	return compras;
    }
    
    @Override
    public List<Compra> listarTodosPorEmpresa(Empresa empresa){
    	return compraRepository.findCompraByEmpresa_Id(empresa.getId());
    }

    @Override
    public Compra salvar(Empresa empresa, String produto, Integer quantidade, Double valor){
        Compra compra = new Compra();
        List<Compra> listaCompra = empresa.getCompras();
        Compra compraCadastrada = null;
        List<Compra> compraCadastradaList = compraRepository.findCompraByEmpresa_IdAndTipo(empresa.getId(), produto);
        if(compraCadastradaList.size()>0) {
        	compraCadastrada = compraCadastradaList.get(0);
        }
        
        if(compraCadastrada == null){
            compra.setTipo(produto);
            compra.setQuantidade(quantidade);
            compra.setValor(valor);
            compra.setData(new Date(System.currentTimeMillis()));
            listaCompra.add(compra);
            empresa.setCompras(listaCompra);

            return compraRepository.save(compra);
        } else{
            compraCadastrada.setQuantidade(compraCadastrada.getQuantidade() + quantidade);
            compraCadastrada.setValor(compraCadastrada.getValor() + valor);
            compraCadastrada.setData(new Date(System.currentTimeMillis()));

            return compraRepository.save(compraCadastrada);
        }
    }

}
