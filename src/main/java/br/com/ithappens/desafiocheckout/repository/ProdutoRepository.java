package br.com.ithappens.desafiocheckout.repository;

import br.com.ithappens.desafiocheckout.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {

    Optional<Produto> findByDescricao(String descricao);
}
