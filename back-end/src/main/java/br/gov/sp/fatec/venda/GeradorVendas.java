package br.gov.sp.fatec.venda;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import br.gov.sp.fatec.equipe.Equipe;
import br.gov.sp.fatec.erb.ERB;

public class GeradorVendas {
	private static Integer calcularNivelEmpresa(Integer idEmpresa, List<Equipe> equipes, List<ERB> erbs, Integer qtdArmazem, Integer qtdProdutosEstoque, Integer qtdProdutosEstoqueTotal, Double precosVendaMercado, Double precoVendaProduto) {
    	Integer nivelMercado = 1;
    	
    	Integer mediaProdutosEquipe = produtosEquipe(equipes, qtdProdutosEstoqueTotal);
    	
    	Integer mediaArmazemEquipe = armazemProduto(equipes, qtdArmazem);
    	
    	Integer mediaProdutoERB = produtoERB(erbs, qtdProdutosEstoqueTotal);
    	
    	Double mediaPreco = mediaPreco(precosVendaMercado, precoVendaProduto);
    	
    	if (mediaProdutosEquipe < 0) {
    		nivelMercado++;
    	}
    	if (mediaArmazemEquipe < 0) {
    		nivelMercado++;
    	}
    	if (mediaProdutoERB < 0) {
    		nivelMercado++;
    	}
    	if (mediaPreco > 800) {
    		nivelMercado+=2;
    	}
    	
    	System.out.println(nivelMercado);
    	return nivelMercado;
    }
    
    private static Double mediaPreco(Double precosVendaMercado, Double precoVendaProduto) {
    	return Math.abs(precosVendaMercado - precoVendaProduto);
    }
    
    private static Integer produtoERB(List<ERB> erbs, Integer qtdProdutosEstoque) {
    	Integer capacidadeProdutosERB = 0;
    	
    	for (ERB erb : erbs) {	
    		Integer produtoERB = 0;
    		if (erb.getRegiao().toLowerCase().equals("Sudeste")) // Sudeste
    			produtoERB = erb.getQuantidade() * 1307;
    		
    		if (erb.getRegiao().toLowerCase().equals("Nordeste")) // Nordeste
    			produtoERB = erb.getQuantidade() * 1727;
    		
    		if (erb.getRegiao().toLowerCase().equals("Norte")) //Norte
    			produtoERB = erb.getQuantidade() * 2346;
    		
    		capacidadeProdutosERB += produtoERB;
    	}
    	
    	return capacidadeProdutosERB - qtdProdutosEstoque;
    	
    }
    
    private static Integer produtosEquipe(List<Equipe> equipes, Integer qtdProdutosEstoque) {
    	Integer capacidadeProdutosEquipe = 0;
    	
    	for (Equipe equipe : equipes) {	
    		Integer produtoEquipe = 0;
    		if (equipe.getTipo().equals("Logistica"))
    			produtoEquipe = equipe.getQuantidade() * 50000;
    		
    		if (equipe.getTipo().equals("Comercial"))
    			produtoEquipe = equipe.getQuantidade() * 5000;

			capacidadeProdutosEquipe += produtoEquipe;

    	}
    	
		return capacidadeProdutosEquipe - qtdProdutosEstoque;
    	
    }
    
    private static Integer armazemProduto(List<Equipe> equipes, Integer qtdArmazem) {
    	Integer capacidadeArmazemEquipe = 0;
    	
    	for (Equipe equipe : equipes) {	
    		Integer produtoEquipe = 0;
    		if (equipe.getTipo().equals("Logistica"))
    			produtoEquipe = equipe.getQuantidade() * 10;
    		
    		if (equipe.getTipo().equals("Comercial"))
    			produtoEquipe = equipe.getQuantidade() * 1;

			capacidadeArmazemEquipe += produtoEquipe;
    	}
    	
		return capacidadeArmazemEquipe - qtdArmazem;

    	
    }
    
    private Integer definirQtdVendas(Integer nivelEmpresa, Integer qtdProdutosEstoque) {
    	int vendas = ThreadLocalRandom.current().nextInt(0, qtdProdutosEstoque/nivelEmpresa);
    	
    	return vendas;
    }
    
    
    public Integer gerarVendas(Integer idEmpresa, List<Equipe> equipes, List<ERB> erbs, Integer qtdArmazem, Integer qtdProdutosEstoque, Integer qtdProdutosEstoqueTotal, Double precosVendaMercado, Double precoVendaProduto) {
    	Integer nivelEmpresa = calcularNivelEmpresa(idEmpresa, equipes, erbs, qtdArmazem, qtdProdutosEstoque, qtdProdutosEstoqueTotal, precosVendaMercado, precoVendaProduto);
    	Integer vendas = definirQtdVendas(nivelEmpresa, qtdProdutosEstoque);
    	
    	return vendas;
    }
	
}
