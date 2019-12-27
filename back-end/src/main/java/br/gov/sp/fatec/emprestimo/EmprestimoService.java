package br.gov.sp.fatec.emprestimo;

import java.util.List;

import br.gov.sp.fatec.empresa.Empresa;

public interface EmprestimoService {

	Emprestimo salvar(Empresa empresa, double valor, int rodada);

	List<Emprestimo> listaTodos(Empresa empresa);

	Emprestimo salvarBNDS(Empresa empresa, double v, int rodada);

	List<Emprestimo> pagamento(Empresa empresa, Emprestimo e, double valor);

	Emprestimo buscaPorId(long id);

	void atualizaEmprestimo(Empresa empresa);

}
