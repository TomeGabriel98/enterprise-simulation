package br.gov.sp.fatec.mercado;

import java.util.List;
import java.util.Optional;

import br.gov.sp.fatec.servicoTerceiro.ServicoTerceiro;

public interface MercadoService {

    public Mercado salvar(Mercado tipoMercado) throws Exception;    
	public List<Mercado> buscaTipoMercado(int id);
	Optional<Mercado> buscarPorId(Integer id);

}
