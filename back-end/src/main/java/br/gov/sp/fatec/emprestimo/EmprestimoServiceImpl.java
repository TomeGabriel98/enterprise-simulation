package br.gov.sp.fatec.emprestimo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.caixa.CaixaMovimentacao;
import br.gov.sp.fatec.caixa.CaixaServiceImpl;
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.empresa.EmpresaRepository;
import br.gov.sp.fatec.empresa.EmpresaServiceImpl;

@Service
@Transactional
public class EmprestimoServiceImpl implements EmprestimoService{
	
	@Autowired
	private EmprestimoRepository emprestimoRepo;
	
	@Autowired
	private EmpresaServiceImpl empresaService;
	
	@Autowired
	private CaixaServiceImpl caixaService;
	
	private final double JUROS = 0.0259;
	private final double JUROS_BNDS = 0.013;
	
	@Override
	public Emprestimo buscaPorId(long id) {
		Emprestimo e = emprestimoRepo.findById(id).get();
		return e;
	}
	
	@Override
	public Emprestimo salvar(Empresa empresa, double v, int rodada) {
		Emprestimo emprestimo = new Emprestimo();
		double valor = v*Math.pow((1+JUROS),12-rodada);
		BigDecimal bd = new BigDecimal(valor).setScale(2, RoundingMode.DOWN);
		double valorTotal = bd.doubleValue();
		emprestimo.setEmpresa(empresa);
		emprestimo.setValor_total(valorTotal);
		emprestimo.setRodada(rodada);
		emprestimo.setAdquirido(v);
		emprestimo.setStatus("A Pagar");
		emprestimo.setValor_restante(valorTotal);
		emprestimoRepo.save(emprestimo);
		
		CaixaMovimentacao caixaMov = new CaixaMovimentacao();
		caixaMov.setAtivo(true);
		caixaMov.setData(new Date());
		caixaMov.setTipoOperacao("Empréstimo");
		caixaMov.setValor(v);
		caixaService.salvar(caixaMov);
		
		double caixaAtual = empresa.getCaixa();
		double novoCaixa = caixaAtual+v;
		empresa.setCaixa(novoCaixa);
		
		List<CaixaMovimentacao> caixas = empresa.getCaixas();
		caixas.add(caixaMov);
		
		empresaService.salvar(empresa);		
		atualizaEmprestimo(empresa);
		return emprestimo;
	}
	
	@Override
	public Emprestimo salvarBNDS(Empresa empresa, double v, int rodada) {
		Emprestimo emprestimo = new Emprestimo();
		double valor = v*Math.pow((1+JUROS_BNDS),12-rodada);
		BigDecimal bd = new BigDecimal(valor).setScale(2, RoundingMode.DOWN);
		double valorTotal = bd.doubleValue();
		emprestimo.setEmpresa(empresa);
		emprestimo.setValor_total(valorTotal);
		emprestimo.setRodada(rodada);
		emprestimo.setAdquirido(v);
		emprestimo.setStatus("A Pagar");
		emprestimo.setValor_restante(valorTotal);
		emprestimoRepo.save(emprestimo);
		
		CaixaMovimentacao caixaMov = new CaixaMovimentacao();
		caixaMov.setAtivo(true);
		caixaMov.setData(new Date());
		caixaMov.setTipoOperacao("(+) Empréstimo");
		caixaMov.setValor(v);
		caixaService.salvar(caixaMov);
				
		List<CaixaMovimentacao> caixas = empresa.getCaixas();
		caixas.add(caixaMov);
		
		empresaService.salvar(empresa);		
		atualizaEmprestimo(empresa);
		return emprestimo;
	}
	
	@Override
	public List<Emprestimo> listaTodos(Empresa empresa){
		List<Emprestimo> emprestimo = emprestimoRepo.findAllByEmpresa_Id(empresa.getId());
		return emprestimo;
	}
	
	@Override
	public void atualizaEmprestimo(Empresa empresa) {
		double valorTotal = 0;
		for(Emprestimo emprestimo : emprestimoRepo.findAllByEmpresa_Id(empresa.getId())){
			valorTotal += emprestimo.getValor_restante();
		}
		empresa.setEmprestimo(valorTotal);
		empresaService.salvar(empresa);
	}
	
	@Override
	public List<Emprestimo> pagamento(Empresa empresa, Emprestimo e, double valor){
		double valorAtual = e.getValor_restante();
		double novoRestante = valorAtual - valor;
		e.setValor_pago(e.getValor_pago() + valor);
		e.setValor_restante(novoRestante);
		emprestimoRepo.save(e);
		
		CaixaMovimentacao caixaMov = new CaixaMovimentacao();
		caixaMov.setAtivo(true);
		caixaMov.setData(new Date());
		caixaMov.setTipoOperacao("(-) Pagamento");
		caixaMov.setValor(valor);
		caixaService.salvar(caixaMov);
				
		List<CaixaMovimentacao> caixas = empresa.getCaixas();
		caixas.add(caixaMov);
		
		double caixaEmpresa = empresa.getCaixa();
		empresa.setCaixa(caixaEmpresa - valor);
		empresaService.salvar(empresa);
		atualizaEmprestimo(empresa);
		List<Emprestimo> emprestimo = emprestimoRepo.findAllByEmpresa_Id(empresa.getId());
		return emprestimo;
	}

}
