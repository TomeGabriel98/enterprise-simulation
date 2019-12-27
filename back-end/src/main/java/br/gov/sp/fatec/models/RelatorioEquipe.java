package br.gov.sp.fatec.models;

import java.util.ArrayList;
import java.util.List;

import br.gov.sp.fatec.equipe.Equipe;
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.tipo.TipoEquipe;

public class RelatorioEquipe {

	private List<Equipe> listaEquipe;
	private List<Equipe> listaLogistica;
	private List<Equipe> listaComercial;
	private List<Equipe> listaInfraestrutura;
	private Empresa empresa;

	private RelatorioEquipe(){

	}

	public static class Factory{

		public static RelatorioEquipe criarPuro() {
			return new RelatorioEquipe();
		}

		public static RelatorioEquipe editar(RelatorioEquipe relatorioCaixa,
				List<Equipe> listaEquipe,
				Empresa empresa){

			relatorioCaixa.listaEquipe = listaEquipe;
			relatorioCaixa.empresa = empresa;

			return relatorioCaixa;

		}
	}
}