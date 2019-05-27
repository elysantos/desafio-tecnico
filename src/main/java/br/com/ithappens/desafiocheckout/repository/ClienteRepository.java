package br.com.ithappens.desafiocheckout.repository;

import br.com.ithappens.desafiocheckout.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCpfAndNome(String cpf, String nome);
}
