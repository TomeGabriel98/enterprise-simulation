package br.gov.sp.fatec.turno;

import java.util.List;

import br.gov.sp.fatec.empresa.Empresa;


public interface TurnoService {
	
	public Turno salvar(Turno usuario);

	public Turno salvarTodos(List<Turno> usuario);

	public void liberarTodos(List<Turno> listaTurno);

	public Turno editar(Turno turno);
	
	public List<Turno> travarTodos(Turno turno);
	
	public Turno novoTurno(String nome);
	
	public List<Turno> mudarTurno(Turno turno);

	public List<Turno> listarTodos();

	Turno buscaPorId(Long id);

	List<Turno> deleteTurno(Turno turno);


}
