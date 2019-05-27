package br.com.ithappens.desafiocheckout.repository;

import br.com.ithappens.desafiocheckout.domain.DocumentoFiscal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentoRepository extends JpaRepository<DocumentoFiscal, Long> {
    Optional<DocumentoFiscal> findByChave(String chave);
}
