package br.gov.sp.fatec.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.empresa.EmpresaRepository;
import br.gov.sp.fatec.projetofatecb.models.Usuario;
import br.gov.sp.fatec.repository.UsuarioRepository;

@Transactional
@Service("segurancaService")
@CrossOrigin
public class SegurancaServiceImpl implements UserDetailsService {

	@Autowired
	private EmpresaRepository usuarioRepo;

	public void setUsuarioRepo(EmpresaRepository usuarioRepo) {
		this.usuarioRepo = usuarioRepo;
	}

	@CrossOrigin
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Empresa empresa = usuarioRepo.findByEmail(username);
		if(empresa == null) {
			throw new UsernameNotFoundException(username);
		}
		return empresa;
	}

}
