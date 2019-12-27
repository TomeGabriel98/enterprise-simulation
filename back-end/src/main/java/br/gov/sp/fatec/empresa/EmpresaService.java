package br.gov.sp.fatec.empresa;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EmpresaService {
	public Empresa salvar(Empresa empresa);
	public Empresa excluir(Empresa empresa);
	public Empresa editar(Empresa empresa);
	public Optional<Empresa> buscarPorId(Integer id);
	public boolean isPodeContratar(Empresa empresa, double valorEntrada);
	public Double getCaixa(Integer empresaId);
	public Double getEmprestimos(Integer empresaId);
	public Double getCustos(Integer empresaId);
	public Integer getEstoqueErbs(Integer empresaId);
	public Integer getEstoqueEquipes(Integer empresaId);
	public Integer getEstoqueServicos(Integer empresaId);
	public Collection<Empresa> getEmpresas();
}
