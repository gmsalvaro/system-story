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
    @Column(unique = true, nullable = false)
    private String codigo;
    @Column(unique = true, nullable = false)
    private String nome;
    @Column(name = "preco_custo", nullable = false)
    private double precoCusto;
    @Column(name = "preco_venda", nullable = false)
    private double precoVenda;
    @Column(name = "estoque_atual", nullable = false)
    private int estoqueAtual;
    @Column(name = "estoque_minimo")
    private int estoqueMinimo;
}
