package br.gov.sp.fatec.erb;

import br.gov.sp.fatec.empresa.Empresa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ERBService {

    public ERB salvar(Empresa empresa, String regiao, Integer quantidade) throws Exception;
    public ERB editar(Empresa empresa, String regiao, Integer quantidade) throws Exception;
    public Optional<ERB> findByRegiao(String regiao);
    public Optional<ERB> findById(Integer id);
    //public ArrayList<ERB> buscarPorIdEmpresa(Integer id);
	//List<ERB> buscaERBs(Integer idEmpresa);
}
