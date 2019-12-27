package br.gov.sp.fatec;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.gov.sp.fatec.autorizacao.Autorizacao;
import br.gov.sp.fatec.autorizacao.AutorizacaoServiceImpl;
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.empresa.EmpresaRepository;
import br.gov.sp.fatec.equipe.*;
import br.gov.sp.fatec.turno.TurnoServiceImpl;

@SpringBootApplication
@CrossOrigin
public class SpringRestApplication {
	
	@Autowired
	private AutorizacaoServiceImpl autorizacaoService;
	
	@Autowired
	private TurnoServiceImpl turnoServiceImpl;
	
	@Autowired
	private EmpresaRepository empresaRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringRestApplication.class, args);		
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() throws NoSuchAlgorithmException, UnsupportedEncodingException {
	    System.out.println("hello world, I have just started up");
	    Autorizacao aut1 = new Autorizacao();
	    aut1.setNome("ROLE_ADMIN");
	    Autorizacao aut2 = new Autorizacao();
	    aut2.setNome("ROLE_USER");
	    
	    Autorizacao admin = autorizacaoService.buscarPorNome(aut1.getNome());
	    Autorizacao user = autorizacaoService.buscarPorNome(aut2.getNome());
	    if(admin == null) {
	    	autorizacaoService.salvar(aut1);
	    }
	    if(user == null) {
	    	autorizacaoService.salvar(aut2);
	    }
	    Empresa empresaAdmin = empresaRepository.findByNome("admin");
	    if(empresaAdmin==null) {
	    	empresaAdmin = new Empresa();
	    	
	    	empresaAdmin.setNome("admin");
		    empresaAdmin.setEmail("admin@admin.com");
		    ArrayList<Autorizacao> autorizacoes = new ArrayList<>();
		    autorizacoes.add(autorizacaoService.buscarPorNome("ROLE_ADMIN"));
		    empresaAdmin.setAutorizacoes(autorizacoes);
		    String senha = "admin";
		    
		    MessageDigest algorithm = MessageDigest.getInstance("MD5");
			byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));

			StringBuilder hexString = new StringBuilder();
			hexString.append("{MD5}");
			for (byte b : messageDigest) {
				hexString.append(String.format("%02x", 0xFF & b));
			}
		    
			empresaAdmin.setSenha(hexString.toString());
			empresaRepository.save(empresaAdmin);
	    }
	    
	    
	    
	}
	

}
