package br.gov.sp.fatec.servicoTerceiro;

import br.gov.sp.fatec.empresa.Empresa;

import java.util.ArrayList;
import java.util.Optional;

public interface ServicoTerceiroService {
    public ServicoTerceiro salvar(String regiao, String tipoServico, Empresa empresa, Integer quantidade) throws Exception;
    public ServicoTerceiro excluir(ServicoTerceiro servicoTerceiro);
    public Object editar(String regiao, String tipoServico, Empresa empresa, Integer quantidade) throws Exception;
    public Optional<ServicoTerceiro> buscarPorId(Integer id);
    //public ArrayList<ServicoTerceiro> buscarPorIdEmpresa(Integer id);
}