package br.gov.sp.fatec.mercado;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import br.gov.sp.fatec.armazem.Armazem;
import br.gov.sp.fatec.armazem.ArmazemRepository;
import br.gov.sp.fatec.caixa.CaixaMovimentacao;
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.estoque.Estoque;
import br.gov.sp.fatec.estoque.EstoqueRepository;
import br.gov.sp.fatec.localizacao.Localizacao;
import br.gov.sp.fatec.models.RelatorioCaixa;
import br.gov.sp.fatec.produto.Produto;
import br.gov.sp.fatec.projetofatecb.models.*;
import br.gov.sp.fatec.servicoTerceiro.ServicoTerceiro;
import br.gov.sp.fatec.tipo.TipoLocalizacao;
import br.gov.sp.fatec.tipo.TipoMercado;
import br.gov.sp.fatec.tipo.TipoOperacaoCaixa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
	public class MercadoServiceImpl implements MercadoService {
	
		@Autowired
		private MercadoRepository tipoMercadoRepository;


		@Override
		public Mercado salvar(Mercado tipoMercado) throws Exception {
			return tipoMercadoRepository.save(tipoMercado);
		}
		
		@Override
		public List<Mercado> buscaTipoMercado(int id){
			List<Mercado> tipoMercado = tipoMercadoRepository.findByEmpresaId(id);
			return tipoMercado;
		}
		
		@Override
	    public Optional<Mercado> buscarPorId(Integer id) {
	        return tipoMercadoRepository.findById(id);
	    }
		
		public ArrayList<Mercado> criaMercadoLista() {
			
			TipoMercado tipoPremium = TipoMercado.Premium;
			TipoMercado tipoIntermediario = TipoMercado.Intermediario;
			TipoMercado tipoEntrada = TipoMercado.Entrada;
			ArrayList<Mercado> lista = new ArrayList<Mercado>();
			Mercado premium = new Mercado();
			premium.setNome(tipoPremium.getNome());
			tipoMercadoRepository.save(premium);
			Mercado intermediario = new Mercado();
			intermediario.setNome(tipoIntermediario.getNome());
			tipoMercadoRepository.save(intermediario);
			Mercado entrada = new Mercado();
			entrada.setNome(tipoEntrada.getNome());
			tipoMercadoRepository.save(entrada);
			lista.add(premium);
			lista.add(intermediario);
			lista.add(entrada);
			return lista;
		}

}
