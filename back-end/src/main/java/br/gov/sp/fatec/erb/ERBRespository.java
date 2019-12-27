package br.gov.sp.fatec.erb;

import java.util.List;
import java.util.Optional;

import org.hibernate.dialect.Ingres9Dialect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ERBRespository extends JpaRepository<ERB, Integer> {

    public Optional<ERB> findById(Integer id);
    public Optional<ERB> findByRegiao(String regiao);
}
