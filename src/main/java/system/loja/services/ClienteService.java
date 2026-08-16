package system.loja.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import system.loja.model.Cliente;
import system.loja.repositories.ClienteRepository;
import system.loja.exceptions.cliente.ClienteNotFoundException;
import system.loja.exceptions.cliente.ClienteValidationException;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvarCliente(Cliente cliente) {
        if (cliente.getNome() == null)
            throw new ClienteValidationException("Nome do cliente não pode ser nulo");
        if (cliente.getTelefone() == null)
            throw new ClienteValidationException("Telefone do cliente não pode ser nulo");
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente atualizarCliente(Cliente cliente) {
        if (cliente.getId() == null)
            throw new ClienteValidationException("ID do cliente não pode ser nulo");
        if (cliente.getNome() == null)
            throw new ClienteValidationException("Nome do cliente não pode ser nulo");
        if (cliente.getTelefone() == null)
            throw new ClienteValidationException("Telefone do cliente não pode ser nulo");
        return clienteRepository.save(cliente);
    }

    public Cliente buscarPorId(Long id) {
        if (id == null)
            throw new ClienteValidationException("ID do cliente não pode ser nulo");
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado"));
    }

    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorNome(String nome) {
        if (nome == null)
            throw new ClienteValidationException("Nome do cliente não pode ser nulo");
        return clienteRepository.findByNome(nome)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado"));
    }

    public Cliente buscarPorTelefone(String telefone) {
        if (telefone == null)
            throw new ClienteValidationException("Telefone do cliente não pode ser nulo");
        return clienteRepository.findByTelefone(telefone)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado"));
    }

}
