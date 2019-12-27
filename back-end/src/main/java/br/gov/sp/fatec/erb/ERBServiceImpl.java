package br.gov.sp.fatec.erb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.gov.sp.fatec.empresa.EmpresaServiceImpl;
import br.gov.sp.fatec.equipe.Equipe;
import br.gov.sp.fatec.mercado.Mercado;

import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.node.ObjectNode;

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.tipo.TipoERB;
import br.gov.sp.fatec.tipo.TipoMercado;

@Service
public class ERBServiceImpl implements ERBService {

    @Autowired
    private ERBRespository erbRepository;

	@Autowired
	private EmpresaServiceImpl empresaService;
	@CrossOrigin
    @Override
    public ERB salvar(Empresa empresa, String regiao, Integer quantidade) throws Exception{
		ERB erb = new ERB();
		erb.setRegiao(regiao);
		erb.setQuantidade(0);
		TipoERB tipoERB = null;
		String produto = "ERB";

		switch (regiao.toLowerCase()) {
			case "nordeste": {
				tipoERB = TipoERB.Nordeste;
				break;
			}
			case "norte": {
				tipoERB = TipoERB.Norte;
				break;
			}
			case "sudeste": {
				tipoERB = TipoERB.Sudeste;
				break;
			}
		}

		ERB erbCadastrado = buscarERBRegiaoAndEmpresaId(regiao, empresa);

		//Quando a multiplicação estoura a memória, o número fica negativo. Por isso a verificação rs
		if ((tipoERB.getValor()*quantidade) < 0){
			throw new Exception("Quantidade muito alta! Memória estourada");
		}

		if(erbCadastrado != null){
			if (empresaService.retiraCaixa(empresa, (tipoERB.getValor() * quantidade), produto, quantidade, "saida")){
				erbCadastrado.setQuantidade(quantidade + erbCadastrado.getQuantidade());

				return erbRepository.save(erbCadastrado);
			}else{
				throw new Exception("Dinheiro insuficiente para a compra");
			}
		}else{
			if (empresaService.retiraCaixa(empresa, (tipoERB.getValor() * quantidade), produto, quantidade, "saida")){
				erb.setQuantidade(quantidade);

				return erbRepository.save(erb);
			}else{
				throw new Exception("Dinheiro insuficiente para a compra");
			}
		}
    }
    @CrossOrigin
    @Override
    public ERB editar(Empresa empresa, String regiao, Integer quantidade) throws Exception {
		TipoERB tipoERB = null;
		String produto = "ERB";

		switch (regiao.toLowerCase()) {
			case "nordeste": {
				tipoERB = TipoERB.Nordeste;
				break;
			}
			case "norte": {
				tipoERB = TipoERB.Norte;
				break;
			}
			case "sudeste": {
				tipoERB = TipoERB.Sudeste;
				break;
			}
		}

		ERB erb = buscarERBRegiaoAndEmpresaId(regiao, empresa);

		if (erb == null){
			throw new Exception("Nao existe erb comprado por essa empresa");
		}
        if ((erb.getQuantidade() - quantidade) >= 0) {
            empresaService.aumentaCaixa(empresa, (tipoERB.getVenda() * quantidade), produto, quantidade, "entrada");
            erb.setQuantidade(erb.getQuantidade() - quantidade);
			return erbRepository.save(erb);
        }
        else{
            throw new Exception("Tentativa de vender uma quantidade maior do que existente em caixa");
        }
		//if(erb.getQuantidade() <= 0){
		//	erbRepository.delete(erb);
		//}
    }

	public ERB buscarERBRegiaoAndEmpresaId(String regiao, Empresa empresa){
    	List<ERB> erbs = empresa.getERB();

		for (ERB erb: erbs) {
			if (erb.getRegiao().equals(regiao)){
				return erb;
			}
		}

		return null;
	}


    @Override
    public Optional<ERB> findByRegiao(String regiao) {
        return erbRepository.findByRegiao(regiao);
    }

	@Override
	public Optional<ERB> findById(Integer id) {
		return erbRepository.findById(id);
	}

	private String montaObservacaoERB(TipoERB tipoERB){
		StringBuilder str = new StringBuilder();
		str.append("ERB custa: ");
		str.append(tipoERB.getValor());
		str.append(", aparelho atendidos: ");
		str.append(tipoERB.getCliente());
		return str.toString();
	}
	
	public ArrayList<ERB> criaERBLista() {
		return new ArrayList<ERB>();
	}
}
