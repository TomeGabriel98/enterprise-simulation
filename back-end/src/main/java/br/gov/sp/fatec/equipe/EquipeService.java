package br.gov.sp.fatec.equipe;

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.equipe.Equipe;
import java.util.List;
import java.util.Optional;

public interface EquipeService {
    public Equipe excluir(Equipe equipe);
    public Object editar(String regiao, Integer id, String tipo, Integer quantidade, Empresa empresa) throws Exception;
    public Optional<Equipe> buscarPorId(Integer id);
    public Equipe findByTipoAndRegiao(String equipeTipo, String regiao);
    public List<Equipe> buscarTodos();
	public Equipe salvar(String regiao, Empresa empresa, String tipo, Integer quantidade) throws Exception;
}
