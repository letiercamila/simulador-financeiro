package br.gov.caixa.simuladorfinanceiro.facade;

import br.gov.caixa.simuladorfinanceiro.dto.request.SimulacaoRequestDto;
import br.gov.caixa.simuladorfinanceiro.dto.response.SimulacaoResponseDto;
import br.gov.caixa.simuladorfinanceiro.service.SimulacaoService;

public class RealizarSimulacaoFacade {

    private final SimulacaoService simulacaoService;

    public RealizarSimulacaoFacade(SimulacaoService simulacaoService) {
        this.simulacaoService = simulacaoService;
    }

    public SimulacaoResponseDto realizarSimulacao(SimulacaoRequestDto request) {
        return simulacaoService.simularEPersistir(request);
    }
}