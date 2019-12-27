package br.gov.sp.fatec.armazemLevel;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import br.gov.sp.fatec.armazem.Armazem;
import br.gov.sp.fatec.armazem.ArmazemRepository;
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.estoque.Estoque;
import br.gov.sp.fatec.estoque.EstoqueRepository;
import br.gov.sp.fatec.produto.Produto;
import br.gov.sp.fatec.projetofatecb.models.*;
import br.gov.sp.fatec.tipo.TipoOperacaoCaixa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArmazemLevelServiceImpl implements ArmazemLevelService {

	@Autowired
	private ArmazemLevelRepository armazemLevelRepository;


	@Override
	public ArmazemLevel salvar(ArmazemLevel armazemLevel) throws Exception {
		return armazemLevelRepository.save(armazemLevel);
	}

	@Override
	public List<ArmazemLevel> buscaArmazemLevel(int id){
		List<ArmazemLevel> armazem = armazemLevelRepository.findByEmpresaId(id);
		return armazem;
	}



}
