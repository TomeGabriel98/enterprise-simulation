package br.gov.sp.fatec.servicoTerceiro;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import br.gov.sp.fatec.empresa.EmpresaServiceImpl;
import br.gov.sp.fatec.erb.ERB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.MensagemErro;
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.empresa.EmpresaRepository;
import br.gov.sp.fatec.localizacao.Localizacao;
import br.gov.sp.fatec.localizacao.LocalizacaoRepository;
import br.gov.sp.fatec.tipo.TipoServicoTerceiro;

@Service
public class ServicoTerceiroServiceImpl implements ServicoTerceiroService {

    @Autowired
    private ServicoTerceiroRepository servicoRepository;
    
    @Autowired
    private EmpresaRepository empresaRepository;
    
    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    @Autowired
    private EmpresaServiceImpl empresaService;
    
    @Override
    public ServicoTerceiro salvar(String regiao, String tipoServico, Empresa empresa, Integer quantidade) throws Exception{
        ServicoTerceiro servicoTerceiro = new ServicoTerceiro();
        String produto = "Serviço Terceiro";

		TipoServicoTerceiro tipoServicoTerceiro = null;

		switch (tipoServico.toLowerCase()) {
			case "posto de atendimento": {
				tipoServicoTerceiro = tipoServicoTerceiro = TipoServicoTerceiro.PostoAtendimento;
				break;
			}
		}
		List<ServicoTerceiro> servicos = empresa.getServico();
		ServicoTerceiro servicoTerceiroCadastrado = null;
		for(ServicoTerceiro s : servicos) {
			if(s.getRegiao().equals(regiao) && s.getTipo().equals(tipoServico)){
				servicoTerceiroCadastrado = s;				
			}
		}

		//ServicoTerceiro servicoTerceiroCadastrado = buscarServicoPorRegiaoAndEmpresaIdAndTipoServico(regiao, empresa, tipoServico);
		Double caixa = empresa.getCaixa();
		System.out.printf("Novo caixa: ", caixa);
		if(caixa >= tipoServicoTerceiro.getCusto() * quantidade) {
			Double novoCaixa = caixa - tipoServicoTerceiro.getCusto() * quantidade;
			
			empresa.setCaixa(novoCaixa);
			empresaService.salvar(empresa);
			if (servicoTerceiroCadastrado != null){
				
					servicoTerceiroCadastrado.setTipo(tipoServicoTerceiro.getNome());
					servicoTerceiroCadastrado.setRegiao(regiao);
					servicoTerceiroCadastrado.setQuantidade(servicoTerceiroCadastrado.getQuantidade()+quantidade);

					return servicoRepository.save(servicoTerceiroCadastrado);
				}
			else{
				
					servicoTerceiro.setTipo(tipoServico);
					servicoTerceiro.setRegiao(regiao);
					servicoTerceiro.setQuantidade(quantidade);

					return servicoRepository.save(servicoTerceiro);
				
			}
		}else {
			throw new Exception("Dinheiro insuficiente para a compra");
		}
		

    }

    @Override
    public Object editar(String regiao, String tipoServico, Empresa empresa, Integer quantidade) throws Exception{
		TipoServicoTerceiro tipoServicoTerceiro = null;
		switch (tipoServico.toLowerCase()) {
			case "posto de atendimento": {
				tipoServicoTerceiro = tipoServicoTerceiro = TipoServicoTerceiro.PostoAtendimento;
				break;
			}
		}

		ServicoTerceiro servicoTerceiro = buscarServicoPorRegiaoAndEmpresaIdAndTipoServico(regiao, empresa, tipoServico);

		if (servicoTerceiro == null){
			return new MensagemErro("Nao existe serviço terceiro contratado por essa empresa");
		}
		if (servicoTerceiro.getQuantidade() >= quantidade){
			servicoTerceiro.setQuantidade(servicoTerceiro.getQuantidade() - quantidade);

			servicoRepository.save(servicoTerceiro);
		}else{
			return new MensagemErro("Tentativa de demitir quantidade de serviços terceiros maior do que a existente");
		}
		return empresa;
    }

	@Override
	public ServicoTerceiro excluir(ServicoTerceiro servicoTerceiro) {
		servicoRepository.delete(servicoTerceiro);

		return servicoTerceiro;
	}

    @Override
    public Optional<ServicoTerceiro> buscarPorId(Integer id) {
        return servicoRepository.findById(id);
    }


	public List<ServicoTerceiro> getServicoTerceiro(Empresa empresa){
		List<ServicoTerceiro> servicoTerceiro;
		ServicoTerceiroServiceImpl servTercService = new ServicoTerceiroServiceImpl();
			servicoTerceiro = empresa.getServicoTerceiro();
			for (ServicoTerceiro serv : servicoTerceiro) {
				if(serv == null) {
					serv.setTipo(TipoServicoTerceiro.PostoAtendimento.getNome());
					serv.setQuantidade(0);
					serv.setObservacao(montaObservacaoServicoTerceiro(TipoServicoTerceiro.PostoAtendimento));
					servicoTerceiro.add(serv);
					//servTercService.salvar(serv);
				}
			}
		return servicoTerceiro;
	}

	public ServicoTerceiro buscarServicoPorRegiaoAndEmpresaIdAndTipoServico(String regiao, Empresa empresa, String tipoServico){
		List<ServicoTerceiro> servicos = empresa.getServicoTerceiro();

		for (ServicoTerceiro servico: servicos) {
			if (servico.getRegiao().equals(regiao) && servico.getTipo().equals(tipoServico)){
				return servico;
			}
		}

		return null;
	}
	
	private String montaObservacaoServicoTerceiro(TipoServicoTerceiro tipoServicoTerceiro){
		StringBuilder str = new StringBuilder();
		str.append("O ");
		str.append(tipoServicoTerceiro.getNome());
		str.append(" custa ");
		str.append(tipoServicoTerceiro.getCusto());
		str.append(" e atende a ");
		str.append(tipoServicoTerceiro.getCliente());
		return str.toString();
	}
	
	public ArrayList<ServicoTerceiro> criaServico() {
		return new ArrayList<>();
	}

}