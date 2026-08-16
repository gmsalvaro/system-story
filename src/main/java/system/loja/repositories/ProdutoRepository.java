package system.loja.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import system.loja.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByCodigo(String codigo);

    Optional<Produto> findByNome(String nome);

    void deleteByCodigo(String codigo);

    void deleteById(Long id);

}
