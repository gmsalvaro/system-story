package system.loja.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import system.loja.model.Cliente;
import system.loja.model.Consorcio;
import system.loja.model.ConsorcioParticipante;
import system.loja.repositories.ConsorcioParticipanteRepository;
import system.loja.exceptions.participante.ParticipanteNotFoundException;
import system.loja.exceptions.participante.ParticipanteValidationException;

@Service
@RequiredArgsConstructor
public class ConsorcioParticipanteService {

    private final ConsorcioParticipanteRepository participanteRepository;
    private final ClienteService clienteService;
    private final ConsorcioService consorcioService;

    @Transactional
    public ConsorcioParticipante salvarParticipante(Long consorcioId, Long clienteId, int numeroCota) {
        if (consorcioId == null || clienteId == null)
            throw new ParticipanteValidationException("Consórcio e Cliente são obrigatórios");
        if (numeroCota <= 0)
            throw new ParticipanteValidationException("Número da cota deve ser válido (maior que zero)");

        Consorcio consorcio = consorcioService.buscarPorId(consorcioId);
        Cliente cliente = clienteService.buscarPorId(clienteId);

        ConsorcioParticipante participante = new ConsorcioParticipante();
        participante.setConsorcio(consorcio);
        participante.setCliente(cliente);
        participante.setNumeroCota(numeroCota);
        participante.setContemplado(false);

        return participanteRepository.save(participante);
    }

    public ConsorcioParticipante buscarPorId(Long id) {
        if (id == null)
            throw new ParticipanteValidationException("ID do participante não pode ser nulo");
        return participanteRepository.findById(id)
                .orElseThrow(() -> new ParticipanteNotFoundException("Participante não encontrado"));
    }

    public List<ConsorcioParticipante> buscarTodos() {
        return participanteRepository.findAll();
    }

    @Transactional
    public ConsorcioParticipante contemplarParticipante(ConsorcioParticipante participante) {
        if (participante.isContemplado()) {
            throw new ParticipanteValidationException("Participante já foi contemplado");
        }

        participante.setContemplado(true);
        participante.setDataContemplacao(LocalDate.now());

        return participanteRepository.save(participante);
    }
}
