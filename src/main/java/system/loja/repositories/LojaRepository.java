package system.loja.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.loja.model.Loja;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Long> {
}
