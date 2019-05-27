package br.com.ithappens.desafiocheckout.repository;

import br.com.ithappens.desafiocheckout.domain.ItemFiscal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemFiscal, Long> {
}
