package system.loja.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.List;
import java.time.LocalDate;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@Table(name = "consorcio")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Consorcio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(name = "quantidade_meses")
    private int quantidadeMeses;
    @Column(name = "valor_parcela")
    private double valorParcela;
    @Column(name = "valor_premio")
    private double valorPremio;
    @Column(name = "data_pagamento")
    private LocalDate dataPagamento; // se é todo dia 12 do mes ou algo assim
    @Enumerated(EnumType.STRING)
    @Column(name = "status_consorcio")
    private StatusConsorcio statusConsorcio;
    @OneToMany(mappedBy = "consorcio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConsorcioParticipante> participantes;
}
