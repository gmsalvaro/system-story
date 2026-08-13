
package system.loja.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "consorcio_pagamento")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ConsorcioPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "participante_id")
    private ConsorcioParticipante participante;
    @Column(name = "numero_parcela")
    private int numeroParcela;
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;
    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento")
    private FormaPagamento formaPagamento;
    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;
    @Column(name = "valor_pagamento")
    private double valorPagamento;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento")
    private StatusPagamento statusPagamento;
}