package system.loja.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import system.loja.model.Cliente;
import system.loja.model.FormaPagamento;
import system.loja.model.StatusPagamento;
import system.loja.model.Venda;
import system.loja.model.ItemVenda;
import system.loja.repositories.VendaRepository;
import system.loja.exceptions.venda.VendaNotFoundException;
import system.loja.exceptions.venda.VendaValidationException;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ItemVendaService itemVendaService;

    // O cliente pode ser nullo, anonimo não necessariamente é cliente cadastrado

    @Transactional
    public Venda salvarVenda(Cliente cliente, List<ItemVenda> itemVendas, FormaPagamento formaPagamento, StatusPagamento statusPagamento){
        if (itemVendas == null || itemVendas.isEmpty()) 
            throw new VendaValidationException("Itens da venda não podem ser vazios");
        if (formaPagamento == null)
            throw new VendaValidationException("Forma de pagamento da venda não pode ser nula");
        if (statusPagamento == null)
            throw new VendaValidationException("Status de pagamento da venda não pode ser nulo");

        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setDataHora(LocalDateTime.now());
        venda.setFormaPagamento(formaPagamento);

        // Pendente apenas para clientes cadastrados
        if(statusPagamento == StatusPagamento.PENDENTE){
            if(cliente == null) 
                throw new VendaValidationException("Cliente da venda não pode ser nulo quando o pagamento for pendente");
            venda.setStatusPagamento(StatusPagamento.PENDENTE);
            venda.setDataPagamento(null);
        } else {
            venda.setStatusPagamento(StatusPagamento.PAGO);
            venda.setDataPagamento(LocalDateTime.now());
        }

        // Salvar a venda primeiro para que ela tenha um ID (necessário para os itens)
        Venda vendaSalva = vendaRepository.save(venda);

        double valorTotal = 0;
        // Processar os itens, salvar no banco e debitar estoque
        for (ItemVenda item : itemVendas) {
            ItemVenda itemSalvo = itemVendaService.salvarItemVenda(vendaSalva, item.getProduto(), item.getQuantidade());
            valorTotal += itemSalvo.getPrecoUnitario() * itemSalvo.getQuantidade();
        }

        // Atualizar o valor total na venda
        vendaSalva.setValorTotal(valorTotal);
        vendaSalva.setItens(itemVendaService.buscarPorVenda(vendaSalva.getId()));
        return vendaRepository.save(vendaSalva);
    }
}
