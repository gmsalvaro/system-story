package system.loja.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import jakarta.persistence.Column;

@Entity
@Table(name = "loja")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class Loja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cnpj;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String endereco;

    @OneToMany(mappedBy = "loja")
    private List<Cliente> clientes;
}
