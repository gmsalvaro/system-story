package system.loja.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import system.loja.model.ConsorcioPagamento;
import system.loja.model.ConsorcioParticipante;
import system.loja.model.FormaPagamento;
import system.loja.model.StatusPagamento;
import system.loja.repositories.ConsorcioPagamentoRepository;
import system.loja.exceptions.pagamento.PagamentoNotFoundException;
import system.loja.exceptions.pagamento.PagamentoValidationException;

@Service
@RequiredArgsConstructor
public class ConsorcioPagamentoService {

    private final ConsorcioPagamentoRepository pagamentoRepository;
    private final ConsorcioParticipanteService participanteService;

    @Transactional
    public ConsorcioPagamento gerarBoletoParcela(Long participanteId, int numeroParcela, LocalDate dataVencimento) {
        if (participanteId == null)
            throw new PagamentoValidationException("Participante é obrigatório");
        if (numeroParcela <= 0)
            throw new PagamentoValidationException("O número da parcela deve ser maior que zero");
        if (dataVencimento == null)
            throw new PagamentoValidationException("Data de vencimento é obrigatória");

        ConsorcioParticipante participante = participanteService.buscarPorId(participanteId);

        ConsorcioPagamento pagamento = new ConsorcioPagamento();
        pagamento.setParticipante(participante);
        pagamento.setNumeroParcela(numeroParcela);
        pagamento.setDataVencimento(dataVencimento);
        pagamento.setValorPagamento(participante.getConsorcio().getValorParcela());
        pagamento.setStatusPagamento(StatusPagamento.PENDENTE);

        return pagamentoRepository.save(pagamento);
    }

    @Transactional
    public ConsorcioPagamento realizarPagamento(Long pagamentoId, FormaPagamento formaPagamento) {
        if (pagamentoId == null)
            throw new PagamentoValidationException("ID do pagamento não pode ser nulo");
        if (formaPagamento == null || formaPagamento == FormaPagamento.PENDENTE)
            throw new PagamentoValidationException("Forma de pagamento inválida");

        ConsorcioPagamento pagamento = pagamentoRepository.findById(pagamentoId)
                .orElseThrow(() -> new PagamentoNotFoundException("Boleto/Parcela não encontrado"));

        if (pagamento.getStatusPagamento() == StatusPagamento.PAGO) {
            throw new PagamentoValidationException("Esta parcela já foi paga");
        }

        pagamento.setDataPagamento(LocalDate.now());
        pagamento.setFormaPagamento(formaPagamento);
        pagamento.setStatusPagamento(StatusPagamento.PAGO);

        return pagamentoRepository.save(pagamento);
    }

    public List<ConsorcioPagamento> buscarTodos() {
        return pagamentoRepository.findAll();
    }
}
