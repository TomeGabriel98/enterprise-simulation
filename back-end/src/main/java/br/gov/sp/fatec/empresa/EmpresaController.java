package br.gov.sp.fatec.empresa;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import br.gov.sp.fatec.compra.Compra;
import br.gov.sp.fatec.venda.Venda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.gov.sp.fatec.armazem.Armazem;
import br.gov.sp.fatec.armazem.ArmazemServiceImpl;
import br.gov.sp.fatec.autorizacao.Autorizacao;
import br.gov.sp.fatec.autorizacao.AutorizacaoServiceImpl;
import br.gov.sp.fatec.caixa.CaixaMovimentacao;
import br.gov.sp.fatec.caixa.CaixaServiceImpl;
import br.gov.sp.fatec.equipe.Equipe;
import br.gov.sp.fatec.erb.ERB;
import br.gov.sp.fatec.erb.ERBServiceImpl;
import br.gov.sp.fatec.mercado.Mercado;
import br.gov.sp.fatec.mercado.MercadoServiceImpl;
import br.gov.sp.fatec.servicoTerceiro.ServicoTerceiro;
import br.gov.sp.fatec.servicoTerceiro.ServicoTerceiroServiceImpl;
import br.gov.sp.fatec.tipo.TipoLocalizacao;
import br.gov.sp.fatec.turno.Turno;
import br.gov.sp.fatec.turno.TurnoServiceImpl;
import br.gov.sp.fatec.view.View;

@RestController
@CrossOrigin
@RequestMapping(value="/empresa")
public class EmpresaController {
	
	@Autowired
	private EmpresaServiceImpl empresaService;
	
	@Autowired
	private CaixaServiceImpl caixaService;
	
	@Autowired
	private MercadoServiceImpl tipoMercadoService;

	@Autowired
	private AutorizacaoServiceImpl autorizacaoService;
	
	@Autowired
	private ERBServiceImpl erbService;
	
	@Autowired
	private ServicoTerceiroServiceImpl servicoService;
	
	@Autowired
	private TurnoServiceImpl turnoService;
	
	@Autowired
	private ArmazemServiceImpl armazemService;
	
	@CrossOrigin
	@RequestMapping(value="/novoAdmin", method=RequestMethod.POST)
	public ResponseEntity<Empresa> salvarAdmin(@RequestBody ObjectNode json){
		Empresa empresa = new Empresa();
		String nome = json.get("nome").asText();
		String email = json.get("email").asText();
		String senha = json.get("senha").asText();
		String arquivo = json.get("logo").asText();
		Date date = new Date(System.currentTimeMillis());
		
		empresa.setNome(nome);
		empresa.setSenha(md5(senha));
		empresa.setEmail(email);
		empresa.setDataDeCriacao(date);
		empresa.setLogo(arquivo);
		
		Autorizacao a = autorizacaoService.buscarPorNome("ROLE_ADMIN");
		List<Autorizacao> listaAut = new ArrayList<Autorizacao>();
		listaAut.add(a);
		empresa.setAutorizacoes(listaAut);
		
		empresaService.salvar(empresa);
		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<Empresa>(empresa, responseHeaders, HttpStatus.CREATED);
	}

	//@PreAuthorize("ROLE_ADMIN")
	@CrossOrigin
	@RequestMapping(value = "/novoUsuario", method = RequestMethod.POST)
	public ResponseEntity<Empresa> salvar(@RequestBody ObjectNode json) {
		
		String nome = json.get("nome").asText();
		String email = json.get("email").asText();
		String senha = json.get("senha").asText();
		String arquivo = json.get("logo").asText();
		String idAutorizacao = json.get("autorizacao").asText();
		Long turma = Long.parseLong(json.get("turma").asText());
		Date date = new Date(System.currentTimeMillis());
		Empresa empresa = new Empresa();

		ArrayList<Mercado> listaMercado = tipoMercadoService.criaMercadoLista();
		ArrayList<ERB> listaERB = erbService.criaERBLista();
		ArrayList<ServicoTerceiro> listaServicoTerceiros = servicoService.criaServico();
		empresa.setTipoMercado(listaMercado);
		empresa.setNome(nome);
		empresa.setSenha(md5(senha));
		empresa.setEmail(email);
		empresa.setDataDeCriacao(date);
		empresa.setERB(listaERB);
		empresa.setServico(listaServicoTerceiros);
		Double caixa = 1000000000.00;
		empresa.setCaixa(caixa);

		Autorizacao a = autorizacaoService.buscarPorId(Long.parseLong(idAutorizacao));
		a = new Autorizacao();
		a.setNome("ROLE_USUARIO");
		autorizacaoService.salvar(a);
		
		List<Autorizacao> listaAut = new ArrayList<Autorizacao>();
		listaAut.add(a);
		empresa.setAutorizacoes(listaAut);

		empresa.setLogo(arquivo);
		List<Equipe> equipes = new ArrayList<Equipe>();
		List<CaixaMovimentacao> caixas = new ArrayList<CaixaMovimentacao>();
		empresa.setCaixas(caixas);
		empresa.setERB(listaERB);
		empresa.setServico(listaServicoTerceiros);
		
		empresa.setEquipes(equipes);
		empresa.setEmprestimo(0.0);
		empresaService.salvar(empresa);
		
		Turno t = turnoService.buscaPorId(turma);
		turnoService.addTurnoEmpresa(t, empresa);
		
		Empresa newEmpresa = empresaService.buscarPorId(empresa.getId()).get();
		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<Empresa>(newEmpresa, responseHeaders, HttpStatus.CREATED);
	}

	@CrossOrigin
	@RequestMapping(value = "/addFoto", method = RequestMethod.POST)
	ResponseEntity<String> receiveData(MultipartFile foto) throws IOException{
		System.out.println(foto.getOriginalFilename());
		System.out.println(foto.toString());
		return ResponseEntity.ok("Deu certo!");
	}
	@CrossOrigin
	@RequestMapping(value="/atualizaFoto/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Empresa> atualizaFoto(@PathVariable int id, @RequestBody ObjectNode json){
		Empresa e = empresaService.buscarPorId(id).get();
		String logo = json.get("logo").asText();
		e.setLogo(logo);
		empresaService.salvar(e);
		return new ResponseEntity<Empresa>(e, HttpStatus.OK);
	}
	
	
	@CrossOrigin
	@RequestMapping(value="/getById/{id}", method=RequestMethod.GET)
	public ResponseEntity<Empresa> getempresaById(@PathVariable("id") String id){
		Optional<Empresa> empresa = empresaService.buscarPorId(Integer.parseInt(id));
		if(empresa == null) {
			return new ResponseEntity<Empresa>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Empresa>(empresa.get(), HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value="/editar", method=RequestMethod.PUT)
	public ResponseEntity<Empresa> editarEmpresa(@RequestBody Empresa e){
		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<Empresa>(e, responseHeaders, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value="/editarSenha{id}", method=RequestMethod.PUT)
	public ResponseEntity<Empresa> editarSenhaEmpresa(@PathVariable("id") Integer id){
		Empresa e = empresaService.buscarPorId(id).get();
		e.setSenha(md5(e.getSenha()));
		empresaService.salvar(e);
		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<Empresa>(e, responseHeaders, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/deleteEmpresa/{id}/", method = RequestMethod.DELETE)
	public Boolean deleteEmpresa(@PathVariable Integer id){
		Empresa empresa = empresaService.buscarPorId(id).get();
		empresaService.excluir(empresa);
		return true;
	}

	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@CrossOrigin
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public ResponseEntity<Collection<Empresa>> getAll() {
		return new ResponseEntity<Collection<Empresa>>(empresaService.todosUsuarios(), HttpStatus.OK);
	}
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@JsonView(View.UsuarioResumo.class)
	@CrossOrigin
	@RequestMapping(value = "/getAllAdmin", method = RequestMethod.GET)
	public ResponseEntity<Collection<Empresa>> getAllAdmin() {
		return new ResponseEntity<Collection<Empresa>>(empresaService.todosAdmins(), HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/setRegiao", method = RequestMethod.PUT)
	public ResponseEntity<Empresa> setRegiao(@RequestBody ObjectNode json){
		TipoLocalizacao tipoLocalizacao = null;
		String regiao = json.get("regiao").asText();
		switch(regiao) {
			case "1": tipoLocalizacao = TipoLocalizacao.Norte;break;
			case "2": tipoLocalizacao = TipoLocalizacao.Nordeste;break;
			case "3": tipoLocalizacao = TipoLocalizacao.SulSudeste;break;
		}
		int idEmpresa = json.get("id_empresa").asInt();
		Empresa empresa = empresaService.buscarPorId(idEmpresa).get();
		if(empresa.getRegiao()==null) {
			
			List<CaixaMovimentacao> caixas = empresa.getCaixas();
			CaixaMovimentacao mov = new CaixaMovimentacao();
			mov.setData(new Date());
			mov.setTipoOperacao("Aquisiçao Franquia");
			mov.setValor(tipoLocalizacao.getValor());
			caixaService.salvar(mov);
			
			caixas.add(mov);
			empresa.setCaixas(caixas);
			empresaService.salvar(empresa);
		}
				
		return new ResponseEntity<Empresa>(empresa, HttpStatus.OK);
	}

	@CrossOrigin
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/dashboard/admin/getAll")
	public ResponseEntity<Collection<Empresa>> getEmpresa(){
		return new ResponseEntity<Collection<Empresa>>(empresaService.getEmpresas(), HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/dashboard/faturamento/{idEmpresa}/")
	public ResponseEntity<Double> dashboardEmpresaFaturamento(@PathVariable Integer idEmpresa){
		return new ResponseEntity<Double>(empresaService.getFaturamento(idEmpresa), HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/dashboard/caixa/{idEmpresa}/")
	public ResponseEntity<Double> dashboardEmpresaCaixa(@PathVariable Integer idEmpresa){
		return new ResponseEntity<Double>(empresaService.getCaixa(idEmpresa), HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/dashboard/emprestimo/{idEmpresa}/")
	public ResponseEntity<Double> dashboardEmpresaEmprestimo(@PathVariable Integer idEmpresa){
		return new ResponseEntity<Double>(empresaService.getEmprestimos(idEmpresa), HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/dashboard/custo/{idEmpresa}/")
	public ResponseEntity<Double> dashboardEmpresaCusto(@PathVariable Integer idEmpresa){
		return new ResponseEntity<Double>(empresaService.getCustos(idEmpresa), HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/dashboard/estoqueERB/{idEmpresa}/")
	public ResponseEntity<Integer> dashboardEmpresaEstoqueERB(@PathVariable Integer idEmpresa){
		return new ResponseEntity<Integer>(empresaService.getEstoqueErbs(idEmpresa), HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/dashboard/estoqueEquipe/{idEmpresa}/")
	public ResponseEntity<Integer> dashboardEmpresaEstoqueEquipes(@PathVariable Integer idEmpresa){
		return new ResponseEntity<Integer>(empresaService.getEstoqueEquipes(idEmpresa), HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/dashboard/estoqueServico/{idEmpresa}/")
	public ResponseEntity<Integer> dashboardEmpresaEstoqueServicos(@PathVariable Integer idEmpresa){
		return new ResponseEntity<Integer>(empresaService.getEstoqueServicos(idEmpresa), HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/dashboard/compras/{idEmpresa}/")
	public ResponseEntity<Collection<Compra>> dashboardEmpresaCompras(@PathVariable Integer idEmpresa){
		return new ResponseEntity<Collection<Compra>>(empresaService.getCompras(idEmpresa), HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/dashboard/vendas/{idEmpresa}/")
	public ResponseEntity<Collection<Venda>> dashboardEmpresaVendas(@PathVariable Integer idEmpresa){
		return new ResponseEntity<Collection<Venda>>(empresaService.getVendas(idEmpresa), HttpStatus.OK);
	}
	
	private String md5(String senha) {
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));

			StringBuilder hexString = new StringBuilder();
			hexString.append("{MD5}");
			for (byte b : messageDigest) {
				hexString.append(String.format("%02x", 0xFF & b));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException exception) {
			exception.printStackTrace();
			// Unexpected - do nothing
		} catch (UnsupportedEncodingException exception) {
			exception.printStackTrace();
			// Unexpected - do nothing			
		}
		return senha;
	}


}



