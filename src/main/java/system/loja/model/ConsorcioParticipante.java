package system.loja.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.time.LocalDate;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "consorcio_participante")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ConsorcioParticipante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "consorcio_id")
    private Consorcio consorcio;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @Column(name = "numero_cota")
    private int numeroCota;
    @Column(name = "foi_contemplado")
    private boolean contemplado;
    @Column(name = "data_contemplacao")
    private LocalDate dataContemplacao;
    @OneToMany(mappedBy = "participante")
    private List<ConsorcioPagamento> pagamentos;
}
