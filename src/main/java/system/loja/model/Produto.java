package system.loja.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "produto")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private String nome;
    @Column(name = "preco_custo")
    private double precoCusto;
    @Column(name = "preco_venda")
    private double precoVenda;
    @Column(name = "estoque_atual")
    private int estoqueAtual;
    @Column(name = "estoque_minimo")
    private int estoqueMinimo;
}
