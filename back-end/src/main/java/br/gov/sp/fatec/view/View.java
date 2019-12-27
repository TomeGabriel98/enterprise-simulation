package br.gov.sp.fatec.view;

import br.gov.sp.fatec.produto.ProdutoView.ProdutoResumido;

/**
 * Esta classe define as diferentes visualizacoes disponiveis para serializacoes
 */
public class View {
	
	/**
	 * Visualizacao principal com os principais atributos
	 */
	public static class UsuarioResumo {}
	
	/**
	 * Visualizacao com todos os atributos
	 * Inclui tudos os atributos marcados com Main
	 */
	public static class UsuarioCompleto extends UsuarioResumo {}
	
	/**
	 * Visualizacao alternativa
	 */
	public static class UsuarioResumoAlternativo {}
	
	public static class Emprestimo extends UsuarioResumo {}
	
	public static class Anotacao {}
	
	public static class Armazem extends UsuarioResumo{}
	
	public static class Autorizacao {}

	public static class Pagamento{}

	public static class Movimentacao{}

	public static class Compra extends ProdutoResumido{}
	
	public static class Admin extends UsuarioResumo{}
}
