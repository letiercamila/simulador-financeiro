package br.gov.caixa.simuladorfinanceiro.service;

import br.gov.caixa.simuladorfinanceiro.dto.request.SimulacaoRequestDto;
import br.gov.caixa.simuladorfinanceiro.dto.response.MemoriaCalculoDto;
import br.gov.caixa.simuladorfinanceiro.dto.response.SimulacaoResponseDto;
import br.gov.caixa.simuladorfinanceiro.entity.simulacao.MemoriaCalculoEntity;
import br.gov.caixa.simuladorfinanceiro.entity.simulacao.SimulacaoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SimulacaoService {

    @Inject
    EntityManager em;

    @Transactional
    public SimulacaoResponseDto simularEPersistir(SimulacaoRequestDto request) {
        BigDecimal valorInicial = request.getValorInicial().setScale(2, RoundingMode.HALF_UP);
        BigDecimal taxaPercentual = request.getTaxaJurosMensal();
        Integer prazo = request.getPrazoMeses();

        BigDecimal taxaDecimal = taxaPercentual.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        BigDecimal saldoAtual = valorInicial;
        BigDecimal totalJuros = BigDecimal.ZERO;

        SimulacaoEntity simulacao = new SimulacaoEntity();
        simulacao.setValorFinanciamento(valorInicial);
        simulacao.setTaxaJurosMensal(taxaPercentual);
        simulacao.setPrazoMeses(prazo);

        List<MemoriaCalculoEntity> memoria = new ArrayList<>();

        for (int mes = 1; mes <= prazo; mes++) {
            BigDecimal saldoInicialMes = saldoAtual.setScale(2, RoundingMode.HALF_UP);
            BigDecimal jurosMes = saldoInicialMes.multiply(taxaDecimal).setScale(2, RoundingMode.HALF_UP);
            BigDecimal saldoFinalMes = saldoInicialMes.add(jurosMes).setScale(2, RoundingMode.HALF_UP);

            totalJuros = totalJuros.add(jurosMes);

            MemoriaCalculoEntity m = new MemoriaCalculoEntity();
            m.setMes(mes);
            m.setSaldoInicial(saldoInicialMes);
            m.setJuros(jurosMes);
            m.setSaldoFinal(saldoFinalMes);
            m.setSimulacao(simulacao);

            memoria.add(m);

            saldoAtual = saldoFinalMes;
        }

        simulacao.setValorTotal(saldoAtual.setScale(2, RoundingMode.HALF_UP));
        simulacao.setValorTotalJuros(totalJuros.setScale(2, RoundingMode.HALF_UP));
        simulacao.setMemoriaCalculo(memoria);

        em.persist(simulacao);
        return mapToResponse(simulacao);
    }

    public SimulacaoResponseDto mapToResponse(SimulacaoEntity entity) {
        List<MemoriaCalculoDto> memoria = entity.getMemoriaCalculo() == null ? List.of()
                : entity.getMemoriaCalculo().stream().map(m -> {
                    MemoriaCalculoDto dto = new MemoriaCalculoDto();
                    dto.setMes(m.getMes());
                    dto.setSaldoInicial(m.getSaldoInicial());
                    dto.setJuros(m.getJuros());
                    dto.setSaldoFinal(m.getSaldoFinal());
                    return dto;
                }).collect(Collectors.toList());

        return SimulacaoResponseDto.builder()
                .id(entity.getId())
                .valorTotal(entity.getValorTotal())
                .valorTotalJuros(entity.getValorTotalJuros())
                .memoriaCalculo(memoria)
                .build();
    }

    public SimulacaoResponseDto findById(Long id) {
        SimulacaoEntity entity = em.find(SimulacaoEntity.class, id);
        if (entity == null) {
            return null;
        }
        
        if (entity.getMemoriaCalculo() != null) {
            entity.getMemoriaCalculo().size();
        }
        return mapToResponse(entity);
    }
}
