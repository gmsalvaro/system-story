package system.loja.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import system.loja.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {

}
