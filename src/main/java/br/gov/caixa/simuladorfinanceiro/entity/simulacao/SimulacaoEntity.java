    package br.gov.caixa.simuladorfinanceiro.entity.simulacao;

    import jakarta.persistence.*;
    import lombok.AccessLevel;
    import lombok.EqualsAndHashCode;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import lombok.ToString;

    import java.math.BigDecimal;
    import java.util.List;

    @Entity
    @Table(name = "simulacao")
    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    @ToString(exclude = "memoriaCalculo")
    public class SimulacaoEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @EqualsAndHashCode.Include
        private Long id;

        private BigDecimal valorFinanciamento;
        private BigDecimal taxaJurosMensal;
        private Integer prazoMeses;
        private BigDecimal valorTotal;
        private BigDecimal valorTotalJuros;

        @OneToMany(mappedBy = "simulacao", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<MemoriaCalculoEntity> memoriaCalculo;
    }