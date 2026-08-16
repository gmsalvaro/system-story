package system.loja.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import system.loja.model.Produto;
import system.loja.repositories.ProdutoRepository;
import org.springframework.transaction.annotation.Transactional;
import system.loja.exceptions.produto.ProdutoNotFoundException;
import system.loja.exceptions.produto.ProdutoValidationException;
import system.loja.exceptions.produto.EstoqueInsuficienteException;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvarProduto(Produto produto) {
        if (produto.getCodigo() == null)
            throw new ProdutoValidationException("Código do produto não pode ser nulo");
        if (produto.getNome() == null)
            throw new ProdutoValidationException("Nome do produto não pode ser nulo");
        if (produto.getPrecoCusto() == 0)
            throw new ProdutoValidationException("Preço de custo do produto não pode ser zero");
        if (produto.getPrecoVenda() == 0)
            throw new ProdutoValidationException("Preço de venda do produto não pode ser zero");
        if (produto.getEstoqueAtual() == 0)
            throw new ProdutoValidationException("Estoque atual do produto não pode ser zero");
        if (produtoRepository.findByCodigo(produto.getCodigo()).isPresent())
            throw new ProdutoValidationException("Produto com código " + produto.getCodigo() + " já cadastrado");
        if (produtoRepository.findByNome(produto.getNome()).isPresent())
            throw new ProdutoValidationException("Produto com nome " + produto.getNome() + " já cadastrado");
        return produtoRepository.save(produto);
    }

    @Transactional
    public Produto atualizarProduto(Produto produto) {
        if (produto.getId() == null)
            throw new ProdutoValidationException("ID do produto não pode ser nulo");
        return produtoRepository.findById(produto.getId())
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));
    }

    public Produto buscarPorId(Long id) {
        if (id == null)
            throw new ProdutoValidationException("ID do produto não pode ser nulo");
        return produtoRepository.findById(id).orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));
    }

    public List<Produto> buscarTodos() {
        return produtoRepository.findAll();
    }

    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNome(nome).stream().toList();
    }

    @Transactional
    public void deleteByCodigo(String codigo) {
        if (codigo == null)
            throw new ProdutoValidationException("Código do produto não pode ser nulo");
        if (produtoRepository.findByCodigo(codigo).isEmpty())
            throw new ProdutoNotFoundException("Produto com código " + codigo + " não encontrado");
        produtoRepository.deleteByCodigo(codigo);
    }

    @Transactional
    public void deleteById(Long id) {
        if (id == null)
            throw new ProdutoValidationException("ID do produto não pode ser nulo");
        if (produtoRepository.findById(id).isEmpty())
            throw new ProdutoNotFoundException("Produto com ID " + id + " não encontrado");
        produtoRepository.deleteById(id);
    }

    @Transactional
    public Produto diminuirEstoque(Long id, int quantidade) {
        if (id == null)
            throw new ProdutoValidationException("ID do produto não pode ser nulo");
        if (produtoRepository.findById(id).isEmpty())
            throw new ProdutoNotFoundException("Produto com ID " + id + " não encontrado");
        if (quantidade <= 0)
            throw new ProdutoValidationException("Quantidade deve ser maior que zero");
        Produto produto = produtoRepository.findById(id).get();
        int estoqueAtual = produto.getEstoqueAtual();
        if (quantidade > estoqueAtual) {
            throw new EstoqueInsuficienteException("Estoque insuficiente");
        }
        produto.setEstoqueAtual(estoqueAtual - quantidade);
        return produtoRepository.save(produto);
    }

    @Transactional
    public Produto aumentarEstoque(Long id, int quantidade) {
        if (id == null)
            throw new ProdutoValidationException("ID do produto não pode ser nulo");
        if (produtoRepository.findById(id).isEmpty())
            throw new ProdutoNotFoundException("Produto com ID " + id + " não encontrado");
        if (quantidade <= 0)
            throw new ProdutoValidationException("Quantidade deve ser maior que zero");
        Produto produto = produtoRepository.findById(id).get();
        int estoqueAtual = produto.getEstoqueAtual();
        produto.setEstoqueAtual(estoqueAtual + quantidade);
        return produtoRepository.save(produto);
    }
}
