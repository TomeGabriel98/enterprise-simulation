package br.gov.sp.fatec.models;

import java.util.ArrayList;
import java.util.List;

import br.gov.sp.fatec.caixa.CaixaMovimentacao;
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.tipo.TipoOperacaoCaixa;

public class RelatorioCaixa {

	private List<CaixaMovimentacao> listaCaixaMovimentacao;
	private Empresa empresa;
	private int total;
	private List<CaixaMovimentacao> listaCaixaNegativo;
	private List<CaixaMovimentacao> listaCaixaPositivo;

	private RelatorioCaixa(){

	}

	public List<CaixaMovimentacao> getListaMovimentacao(){
		return this.listaCaixaMovimentacao;
	}

	public Empresa getEmpresa(){
		return this.empresa;
	}

	public List<CaixaMovimentacao> getListaCaixaNegativo(){
		if(this.listaCaixaMovimentacao != null){
			this.listaCaixaNegativo = new ArrayList<>();
			for (CaixaMovimentacao caixa : listaCaixaMovimentacao) {
				if(!caixa.getTipoOperacao().equals(TipoOperacaoCaixa.Emprestimo) && caixa.isAtivo()){
					this.listaCaixaNegativo.add(caixa);
				}
			}
			return this.listaCaixaNegativo;
		}
		return new ArrayList<>();
	}

	public List<CaixaMovimentacao> getListaCaixaPositivo(){
		if(this.listaCaixaMovimentacao != null){
			this.listaCaixaPositivo = new ArrayList();
			for (CaixaMovimentacao caixa : listaCaixaMovimentacao) {
				if(caixa.getTipoOperacao().equals(TipoOperacaoCaixa.Pagamento) && caixa.isAtivo()){
					this.listaCaixaPositivo.add(caixa);
				}
			}
			return this.listaCaixaPositivo;
		}
		return new ArrayList();
	}

	public static class Factory{

		public static RelatorioCaixa criarPuro(){
			return new RelatorioCaixa();
		}

		public static RelatorioCaixa editar(RelatorioCaixa relatorioCaixa,
				List<CaixaMovimentacao> listaCaixaMovimentacao,
				Empresa empresa){

			relatorioCaixa.listaCaixaMovimentacao = listaCaixaMovimentacao;
			relatorioCaixa.empresa = empresa;

			return relatorioCaixa;

		}
	}
}

