package br.gov.sp.fatec.armazem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import br.gov.sp.fatec.armazem.Armazem;
import br.gov.sp.fatec.armazem.ArmazemRepository;
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.empresa.EmpresaServiceImpl;
import br.gov.sp.fatec.estoque.Estoque;
import br.gov.sp.fatec.estoque.EstoqueRepository;
import br.gov.sp.fatec.produto.Produto;
import br.gov.sp.fatec.projetofatecb.models.*;
import br.gov.sp.fatec.servicoTerceiro.ServicoTerceiro;
import br.gov.sp.fatec.tipo.TipoArmazem;
import br.gov.sp.fatec.tipo.TipoOperacaoCaixa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArmazemServiceImpl implements ArmazemService {

	@Autowired
	private ArmazemRepository armazemRepository;

	@Autowired
	private EmpresaServiceImpl empresaService;

	@Override
	public Armazem salvar(Empresa empresa, String regiao, Integer quantidade) throws Exception {
		regiao = regiao.toLowerCase();
		Armazem armazemCadastrado = buscarArmazemByRegiaoAndEmpresaId(regiao, empresa);

		TipoArmazem tipoArmazem = null;
		String produto = "Armazem";

		switch (regiao) {
			case "nordeste": {
				tipoArmazem = TipoArmazem.Nordeste;
				break;
			}
			case "norte": {
				tipoArmazem = TipoArmazem.Norte;
				break;
			}
			case "sudeste": {
				tipoArmazem = TipoArmazem.Sudeste;
				break;
			}
		}

		//Quando a multiplicação estoura a memória, o número fica negativo. Por isso a verificação rs
		if ((tipoArmazem.getValor()*quantidade) < 0){
			throw new Exception("Quantidade muito alta! Memória estourada");
		}

		if(armazemCadastrado != null){
			if (empresaService.retiraCaixa(empresa, (tipoArmazem.getValor() * quantidade), produto, quantidade, "saida")){
				armazemCadastrado.setQuantidade(armazemCadastrado.getQuantidade() + quantidade);
				return armazemRepository.save(armazemCadastrado);
			}
			else{
				throw new Exception("Dinheiro insuficiente para a compra");
			}

		}
		else{
			Armazem armazem = new Armazem();
			if (empresaService.retiraCaixa(empresa, (tipoArmazem.getValor() * quantidade), produto, quantidade, "saida")){
				armazem.setQuantidade(quantidade);
				armazem.setRegiao(regiao);
				return armazemRepository.save(armazem);
			}
			else{
				throw new Exception("Dinheiro insuficiente para a compra");
			}
		}
	}

	@Override
	public void salvar(Armazem armazem) {
		armazemRepository.save(armazem);
	}
	@Override
	public Armazem editar(Empresa empresa, Integer idArmazem, String regiao, Integer quantidade) throws Exception {
		TipoArmazem tipoArmazem = null;
		String produto = "Armazem";

		switch (regiao.toLowerCase()) {
			case "nordeste": {
				tipoArmazem = TipoArmazem.Nordeste;
				break;
			}
			case "norte": {
				tipoArmazem = TipoArmazem.Norte;
				break;
			}
			case "sudeste": {
				tipoArmazem = TipoArmazem.Sudeste;
				break;
			}
		}

		Armazem armazem = buscarArmazemByRegiaoAndEmpresaId(regiao, empresa);

		if ((armazem.getQuantidade() - quantidade) >= 0) {
			empresaService.aumentaCaixa(empresa, (tipoArmazem.getVenda() * quantidade), produto, quantidade, "entrada");
			armazem.setQuantidade(armazem.getQuantidade() - quantidade);
			armazemRepository.save(armazem);
		}
		else{
			throw new Exception("Tentativa de vender uma quantidade maior do que existente em caixa");
		}

		return armazem;
	}

	public Armazem buscarArmazemByRegiaoAndEmpresaId(String regiao, Empresa empresa){
		List<Armazem> armazems = empresa.getArmazem();

		for (Armazem armazem: armazems) {
			if (armazem.getRegiao().equals(regiao)){
				return armazem;
			}
		}

		return null;
	}
	
	public List<Armazem> buscaPorIdEmpresa(int id){
		return armazemRepository.findByEmpresa_Id(id);
	}
	
	@Override
	public List<Armazem> buscaTodos(){
		return armazemRepository.findAll();
	}

	@Override
	public Optional<Armazem> buscarPorId(Integer id) { return armazemRepository.findById(id); }

}
