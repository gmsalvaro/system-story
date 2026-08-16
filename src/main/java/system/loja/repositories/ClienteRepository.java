package system.loja.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import system.loja.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByNome(String nome);

    Optional<Cliente> findByTelefone(String telefone);

    void deleteByTelefone(String telefone);

    void deleteById(Long id);

}
