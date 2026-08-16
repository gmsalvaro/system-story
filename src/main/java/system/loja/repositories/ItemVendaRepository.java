package system.loja.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import system.loja.model.ItemVenda;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
    List<ItemVenda> findByVendaId(Long id);

    void deleteByVendaId(Long id);

}
