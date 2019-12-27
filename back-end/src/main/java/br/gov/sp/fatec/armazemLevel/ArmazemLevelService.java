package br.gov.sp.fatec.armazemLevel;

import java.util.List;
import java.util.Optional;

public interface ArmazemLevelService {

    public ArmazemLevel salvar(ArmazemLevel armazemLevel) throws Exception;    
	public List<ArmazemLevel> buscaArmazemLevel(int id);

}
