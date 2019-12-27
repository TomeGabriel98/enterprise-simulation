package br.gov.sp.fatec.armazemLevel;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArmazemLevelRepository extends JpaRepository<ArmazemLevel, Integer> {
	 public Optional<ArmazemLevel> findById(Integer id);
	 
	 public List<ArmazemLevel> findByEmpresaId(int id);

}
