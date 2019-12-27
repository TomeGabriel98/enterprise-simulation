package br.gov.sp.fatec.caixa;

import java.util.List;
import java.util.Optional;

import br.gov.sp.fatec.empresa.Empresa;

public interface CaixaService {

    public CaixaMovimentacao salvar(CaixaMovimentacao caixaMovimentacao) throws Exception;
	//public List<Emprestimo> getListaEmprestimo(Integer idEmprestimo);
    
	public List<CaixaMovimentacao> buscaTodasCaixas();

	Empresa movimentaCaixa(Empresa empresa, Double valor, String tipo,  Boolean ativo);

}
