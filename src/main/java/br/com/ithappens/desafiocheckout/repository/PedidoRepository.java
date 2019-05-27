package br.com.ithappens.desafiocheckout.repository;

import br.com.ithappens.desafiocheckout.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
