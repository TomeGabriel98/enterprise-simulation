package br.gov.sp.fatec.armazem;

import java.util.List;
import java.util.Optional;

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.estoque.Estoque;

public interface ArmazemService {

    public Armazem salvar(Empresa empresa, String regiao, Integer quantidade) throws Exception;
    public Armazem editar(Empresa empresa, Integer idArmazem, String regiao, Integer quantidade) throws Exception;
    public Optional<Armazem> buscarPorId(Integer id);
	void salvar(Armazem armazem);
	List<Armazem> buscaTodos();

}
