package br.gov.sp.fatec.compra;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Integer> {
	public List<Compra> findCompraByEmpresa_Id(int id);
	public List<Compra> findCompraByEmpresa_IdAndTipo(int id, String tipo);
}
