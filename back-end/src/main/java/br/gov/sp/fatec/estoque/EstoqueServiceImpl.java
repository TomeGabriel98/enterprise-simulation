package br.gov.sp.fatec.estoque;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstoqueServiceImpl implements EstoqueService {

	@Autowired
	private EstoqueRepository estoqueRepository;

	@Override
	public Estoque salvar(Estoque estoque) throws Exception {
		return estoqueRepository.save(estoque);
	}

	@Override
	public List<Estoque> buscaEstoque(int id) {
		List<Estoque> estoque = estoqueRepository.findByArmazemId(id);
		return estoque;
	}

	@Override
	public Estoque atualizar(Estoque estoque) throws Exception {
		if (estoqueRepository.existsById(estoque.getId())) {
			//SET VALOR PARA ATUALIZAR
			return estoqueRepository.save(estoque);
			
		}
		throw new Exception("Erro ao atualizar o estoque depois da venda");
	}

}
