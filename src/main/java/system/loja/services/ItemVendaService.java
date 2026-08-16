package system.loja.services;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import system.loja.model.ItemVenda;
import system.loja.model.Produto;
import system.loja.model.Venda;
import system.loja.repositories.ItemVendaRepository;
import system.loja.exceptions.itemvenda.ItemVendaNotFoundException;
import system.loja.exceptions.itemvenda.ItemVendaValidationException;
import system.loja.exceptions.venda.VendaNotFoundException;

@Service
@RequiredArgsConstructor
public class ItemVendaService {
    private final ItemVendaRepository itemVendaRepository;
    private final ProdutoService produtoService;

    @Transactional
    public ItemVenda salvarItemVenda(Venda venda, Produto produto, int quantidade) {
        if (venda == null)
            throw new ItemVendaValidationException("Venda do item de venda não pode ser nula");
        if (produto == null)
            throw new ItemVendaValidationException("Produto do item de venda não pode ser nulo");
        if (quantidade <= 0)
            throw new ItemVendaValidationException("Quantidade do item de venda deve ser maior que zero");

        // O produtoService lançará EstoqueInsuficienteException ou ProdutoNotFoundException se algo der errado
        Produto produtoAtualizado = produtoService.diminuirEstoque(produto.getId(), quantidade);
        
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setProduto(produtoAtualizado);
        itemVenda.setQuantidade(quantidade);
        itemVenda.setPrecoUnitario(produtoAtualizado.getPrecoVenda());
        itemVenda.setVenda(venda);
        
        return itemVendaRepository.save(itemVenda);
    }

    @Transactional
    public void deleteByVenda(Long id) {
        if (id == null)
            throw new ItemVendaValidationException("Venda do item de venda não pode ser nula");
        itemVendaRepository.deleteByVendaId(id);
    }

    public List<ItemVenda> buscarTodos() {
        return itemVendaRepository.findAll();
    }

    public List<ItemVenda> buscarPorVenda(Long id) {
        if (id == null)
            throw new ItemVendaValidationException("Venda do item de venda não pode ser nula");
        return itemVendaRepository.findByVendaId(id);
    }

    public double valorTotalVenda(Long idVenda) {
        if (idVenda == null)
            throw new ItemVendaValidationException("Venda do item de venda não pode ser nula");

        List<ItemVenda> itemVendas = itemVendaRepository.findByVendaId(idVenda);
        double valorTotal = 0;
        for (ItemVenda itemVenda : itemVendas) {
            valorTotal += itemVenda.getPrecoUnitario() * itemVenda.getQuantidade();
        }
        return valorTotal;
    }

}
