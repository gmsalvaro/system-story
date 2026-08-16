package system.loja.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import system.loja.model.Consorcio;
import system.loja.model.StatusConsorcio;
import system.loja.repositories.ConsorcioRepository;
import system.loja.exceptions.consorcio.ConsorcioNotFoundException;
import system.loja.exceptions.consorcio.ConsorcioValidationException;

@Service
@RequiredArgsConstructor
public class ConsorcioService {

    private final ConsorcioRepository consorcioRepository;

    @Transactional
    public Consorcio salvarConsorcio(Consorcio consorcio) {
        if (consorcio.getNome() == null || consorcio.getNome().isBlank())
            throw new ConsorcioValidationException("Nome do consórcio não pode ser vazio");
        if (consorcio.getQuantidadeMeses() <= 0)
            throw new ConsorcioValidationException("A quantidade de meses deve ser maior que zero");
        if (consorcio.getValorPremio() <= 0)
            throw new ConsorcioValidationException("O valor do prêmio deve ser maior que zero");

        if (consorcio.getStatusConsorcio() == null) {
            consorcio.setStatusConsorcio(StatusConsorcio.ATIVO);
        }

        // Calcula a parcela automaticamente caso não venha
        if (consorcio.getValorParcela() <= 0) {
            consorcio.setValorParcela(consorcio.getValorPremio() / consorcio.getQuantidadeMeses());
        }

        return consorcioRepository.save(consorcio);
    }

    public Consorcio buscarPorId(Long id) {
        if (id == null)
            throw new ConsorcioValidationException("ID do consórcio não pode ser nulo");
        return consorcioRepository.findById(id)
                .orElseThrow(() -> new ConsorcioNotFoundException("Consórcio não encontrado"));
    }

    public List<Consorcio> buscarTodos() {
        return consorcioRepository.findAll();
    }

    @Transactional
    public void cancelarConsorcio(Long id) {
        Consorcio consorcio = buscarPorId(id);
        consorcio.setStatusConsorcio(StatusConsorcio.CANCELADO);
        consorcioRepository.save(consorcio);
    }
}
