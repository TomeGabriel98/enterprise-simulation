package br.gov.sp.fatec.turno;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.gov.sp.fatec.equipe.Equipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.empresa.EmpresaRepository;
import br.gov.sp.fatec.turno.Turno;
import br.gov.sp.fatec.turno.TurnoRepository;
import br.gov.sp.fatec.turno.TurnoService;

@Service
public class TurnoServiceImpl implements TurnoService {
	
	@Autowired
	private TurnoRepository turnoRepo;
	
	@Autowired
	private EmpresaRepository empresaRespository;
	
	
	@Override
	public Turno buscaPorId(Long id) {
		return turnoRepo.findById(id).get();
	}
	
	@Override
	@Transactional
	public Turno salvar(Turno turno) {
		return turnoRepo.save(turno);
	}

	@Override
	public List<Turno> travarTodos(Turno turno){
    	turno.setTurno_bloqueado(true);
		List<Empresa> empresas = turno.getEmpresas();
		for(Empresa empresa : empresas) {
			empresa.setTurno_travado(true);
			empresaRespository.save(empresa);
		}
		turno.setData_fim(new Date());
		turno.setTurno_bloqueado(true);
		turnoRepo.save(turno);
		return turnoRepo.findAll();
	}

	@Override
	@Transactional
	public void liberarTodos(List<Turno> listaTurno){
		for(Turno turno : listaTurno){
			turno.setIndice(turno.getIndice()+1);
			turno.setTurno_bloqueado(false);
			turno.setData_fim(new Date());
			salvar(turno);
		}
	}
	
	@Override
	public List<Turno> deleteTurno(Turno turno) {
		turno.setEmpresas(new ArrayList<>());
		turnoRepo.save(turno);
		turnoRepo.delete(turno);
		return turnoRepo.findAll();
	}
	
	@Override
	public List<Turno> mudarTurno(Turno turno) {
		List<Empresa> empresas = turno.getEmpresas();
		Integer indice = turno.getIndice()+1;
		for(Empresa empresa : empresas) {
			Double valorSalarioMensal = 0.00;

			for(Equipe equipe : empresa.getEquipes()){
				valorSalarioMensal += equipe.getSalarioMensal();
			}

			// Valor do Salário Mensal descontado ao trocar o turno
			empresa.setCaixa(empresa.getCaixa() - valorSalarioMensal);

			empresa.setTurno_travado(false);
			empresa.setNumero_turno(indice);
			empresaRespository.save(empresa);
		}

		turno.setIndice(indice);
		turno.setTurno_bloqueado(false);
		turnoRepo.save(turno);
		return turnoRepo.findAll();
	}

	@Override
	public Turno salvarTodos(List<Turno> usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Turno editar(Turno turno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Turno novoTurno( String nome) {
		Turno turno = new Turno();
		turno.setData_inicio(new Date());
		turno.setIndice(0);
		turno.setNome(nome);
		turnoRepo.save(turno);
		return turno;
	}

	@Override
	public List<Turno> listarTodos() {
		return turnoRepo.findAll();
	}

	public void addTurnoEmpresa(Turno turno, Empresa empresa) {
		List<Empresa> empresas = turno.getEmpresas();
		empresas.add(empresa);
		turnoRepo.save(turno);
		empresa.setNumero_turno(turno.getIndice());
		empresa.setTurno_travado(turno.getTurno_bloqueado());
		empresaRespository.save(empresa);		
	}


}
