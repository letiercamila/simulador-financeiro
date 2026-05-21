package br.gov.caixa.simuladorfinanceiro.entity.simulacao;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "memoria_calculo")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "simulacao")
public class MemoriaCalculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private Integer mes;
    private BigDecimal saldoInicial;
    private BigDecimal juros;
    private BigDecimal saldoFinal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "simulacao_id")
    private SimulacaoEntity simulacao;
}
