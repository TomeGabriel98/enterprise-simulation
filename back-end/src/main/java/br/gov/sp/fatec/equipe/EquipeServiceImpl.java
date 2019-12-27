package br.gov.sp.fatec.equipe;

import br.gov.sp.fatec.MensagemErro;
import br.gov.sp.fatec.caixa.CaixaMovimentacao;
import br.gov.sp.fatec.caixa.CaixaRepository;
import br.gov.sp.fatec.caixa.CaixaServiceImpl;
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.empresa.EmpresaRepository;
import br.gov.sp.fatec.empresa.EmpresaService;
import br.gov.sp.fatec.empresa.EmpresaServiceImpl;
import br.gov.sp.fatec.erb.ERB;
import br.gov.sp.fatec.tipo.TipoEquipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EquipeServiceImpl implements EquipeService {
	
	@Autowired
	private EquipeRepository equipeRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private CaixaRepository caixaRepository;

	@Autowired
	private EmpresaServiceImpl empresaService;
	
	@Autowired
	private CaixaServiceImpl caixaService;

	@Override
	public Equipe findByTipoAndRegiao(String equipeTipo, String regiao){
		return equipeRepository.findByTipoAndRegiao(equipeTipo, regiao);
	}

	@Override
	public Equipe salvar(String regiao, Empresa empresa, String tipo, Integer quantidade) throws Exception{
		Equipe equipe = new Equipe();
		equipe.setRegiao(regiao);
		equipe.setQuantidade(0);
		TipoEquipe tipoEquipe = null;
		String produto = "Equipe";

		switch (tipo.toLowerCase()) {
			case "logistica": {
				tipoEquipe = TipoEquipe.Logistica;
				break;
			}
			case "comercial": {
				tipoEquipe = TipoEquipe.Comercial;
				break;
			}
			case "infraestrutura": {
				tipoEquipe = TipoEquipe.Infraestrutura;
				break;
			}
		}

		equipe.setTipo(tipoEquipe.getNome());

		Equipe equipeCadastrada = buscarEquipeRegiaoAndEmpresaIdAndTipoEquipe(regiao, empresa, tipoEquipe.getNome());

		//Quando a multiplicação estoura a memória, o número fica negativo. Por isso a verificação rs
		if ((tipoEquipe.getContratacao()*quantidade) < 0){
			throw new Exception("Quantidade muito alta! Memória estourada");
		}
		if (equipeCadastrada != null){
			if(empresaService.retiraCaixa(empresa, tipoEquipe.getContratacao()*quantidade, produto, quantidade, "saida")){
				equipeCadastrada.setSalarioMensal(equipeCadastrada.getSalarioMensal()+tipoEquipe.getSalario());
				equipeCadastrada.setQuantidade(equipeCadastrada.getQuantidade()+quantidade);
				equipeCadastrada.setContratacao(tipoEquipe.getContratacao());

				return equipeRepository.save(equipeCadastrada);
			}else{
				throw new Exception("Dinheiro insuficiente para a compra");
			}
		}else{
			if(empresaService.retiraCaixa(empresa, tipoEquipe.getContratacao()*quantidade, produto, quantidade, "saida")){
				equipe.setSalarioMensal(tipoEquipe.getSalario());
				equipe.setQuantidade(quantidade);
				equipe.setContratacao(tipoEquipe.getContratacao());

				return equipeRepository.save(equipe);
			}else{
				throw new Exception("Dinheiro insuficiente para a compra");
			}
		}
	}
	@Override
	public Object editar(String regiao, Integer id, String tipo, Integer quantidade, Empresa empresa) throws Exception{
		Equipe e = equipeRepository.findById(id).get();
		TipoEquipe tipoEquipe = getTipo(tipo);
		Integer quantAtual = e.getQuantidade();
		if(quantidade>quantAtual) {
			return new MensagemErro("Quantidade insuficiente");
		}else {
			e.setQuantidade(quantAtual - quantidade);
			equipeRepository.save(e);
			return caixaService.movimentaCaixa(empresa, (double) (tipoEquipe.getDesligamento() * quantidade), "saida", true);
		}
		
		//return empresa;
		
	}

//	@Override
//	public void editar(String regiao, Integer id, String tipo, Integer quantidade, Empresa empresa) throws Exception{
//		TipoEquipe tipoEquipe = null;
//

//		Equipe equipe = equipeRepository.findById(id).get();
//		//Equipe equipe = buscarEquipeRegiaoAndEmpresaIdAndTipoEquipe(regiao, empresa, tipoEquipe.getNome());
//
//		if (equipe == null){
//			throw new Exception("Nao existe essa equipe contratada pela empresa");
//		}
//		if(equipe.getQuantidade() - quantidade >= 0){
//			empresaService.aumentaCaixa(empresa, (tipoEquipe.getDesligamento() * quantidade), "saida");
//			equipe.setQuantidade(equipe.getQuantidade() - quantidade);
//
//			equipeRepository.save(equipe);
//		}else{
//			throw new Exception("Tentativa de demitir quantidade de equipe maior do que a existente");
//		}
//
//	}

	public Equipe buscarEquipeRegiaoAndEmpresaIdAndTipoEquipe(String regiao, Empresa empresa, String tipoEquipe){
		List<Equipe> equipes = empresa.getEquipes();

		for (Equipe equipe: equipes) {
			if (equipe.getRegiao().equals(regiao) && equipe.getTipo().equals(tipoEquipe)){
				return equipe;
			}
		}

		return null;
	}

	@Override
	public Equipe excluir(Equipe equipe) {
		equipeRepository.delete(equipe);
		return equipe;
	}
/*
	public CaixaMovimentacao treinamento(List<Equipe> equipes, Empresa empresa){
		CaixaMovimentacao caixa = new CaixaMovimentacao();
		double valorTreino;
		List<CaixaMovimentacao> listaCaixa = caixaRepository.findByEmpresaId(empresa.getId());

		for(Equipe equipe : equipes){
			if(equipe.getTipo().equals("Logística")){
				valorTreino = 7200;

			} else if(equipe.getTipo().equals("Comercial")){
				valorTreino = 7656;

			} else{
				valorTreino = 7104;
			}

			if(isPodeContratar(listaCaixa, valorTreino)){
				caixa.setValor(caixa.getValor() - valorTreino);
				caixa.setData(new Date(System.currentTimeMillis()));
				caixa.setEmpresa_id(empresa);
				caixaRepository.save(caixa);
			} else{
				return null;
			}

		}
		return caixa;
	}*/

	private String montaObservacao(TipoEquipe tipoEquipe){
		StringBuilder str = new StringBuilder();
		str.append("A equipe ");
		str.append(tipoEquipe.getNome());
		str.append(" custa ");
		str.append(tipoEquipe.getContratacao());
		str.append(" valor de contratação com ");
		str.append(tipoEquipe.getSalario());
		str.append(" valor de salário, e o desligamento é ");
		str.append(tipoEquipe.getDesligamento());
		str.append(" com valor de treinamento em ");
		str.append(tipoEquipe.getTreinamento());
		return str.toString();
	}

	@Override
	public Optional<Equipe> buscarPorId(Integer id) {
		return equipeRepository.findById(id);
	}

//	@Override
//	public ArrayList<Equipe> buscarPorIdEmpresaTipo(Integer idEmpresa, TipoEquipe logistica){
//		ArrayList<Equipe> listaEquipe = equipeRepository.findByEmpresaIdAndTipo(idEmpresa, logistica.getNome());
//		return listaEquipe != null ? listaEquipe : new ArrayList<Equipe>();
//	}

	@Override
	public List<Equipe> buscarTodos(){
		return equipeRepository.findAll();
	}

	public TipoEquipe getTipo(String tipo) {
		TipoEquipe tipoEquipe = null;
		switch (tipo.toLowerCase()) {
		case "logistica": {
			tipoEquipe = TipoEquipe.Logistica;
			break;
		}
		case "comercial": {
			tipoEquipe = TipoEquipe.Comercial;
			break;
		}
		case "infraestrutura": {
			tipoEquipe = TipoEquipe.Infraestrutura;
			break;
		}
	}

		return tipoEquipe;
	}

}