package br.gov.sp.fatec.service;

import br.gov.sp.fatec.equipe.EquipeServiceImpl;
import br.gov.sp.fatec.models.RelatorioCaixa;
import br.gov.sp.fatec.models.RelatorioEquipe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.gov.sp.fatec.caixa.CaixaServiceImpl;
import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.equipe.EquipeServiceImpl;
import br.gov.sp.fatec.localizacao.Localizacao;
import br.gov.sp.fatec.localizacao.LocalizacaoServiceImpl;
import br.gov.sp.fatec.caixa.*;
import br.gov.sp.fatec.equipe.*;

public class RelatorioService {

    @Autowired
    private CaixaServiceImpl caixaService;

    public RelatorioCaixa getRelatorioCaixaPorEmpresa(Empresa empresa){

        List<CaixaMovimentacao> listaCaixaMovimentacao = caixaService.buscaTodasCaixasPorEmpresaId(empresa.getId());

        RelatorioCaixa relatorioCaixa = RelatorioCaixa.Factory.criarPuro();
        relatorioCaixa = RelatorioCaixa.Factory.editar(relatorioCaixa, listaCaixaMovimentacao, empresa);
        
        return relatorioCaixa;
    }

    @Autowired
    private LocalizacaoServiceImpl localizacaoService;

    //public List<Localizacao> getRelatorioLocalizacao(Empresa empresa){

      //  return empresa.getLocalizacao();

    //}

    @Autowired
    private EquipeServiceImpl equipeService;

    public List<Equipe> getListaEquipe(Empresa empresa) {
		return null;
    	
    }

}