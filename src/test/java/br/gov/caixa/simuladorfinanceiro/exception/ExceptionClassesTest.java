package br.gov.caixa.simuladorfinanceiro.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionClassesTest {

    @Test
    public void internalServerErrorExceptionShouldCarryMessage() {
        InternalServerErrorException ex = new InternalServerErrorException("erro interno");
        assertEquals("erro interno", ex.getMessage());
    }

    @Test
    public void requisicaoInvalidaExceptionShouldCarryMessage() {
        RequisicaoInvalidaException ex = new RequisicaoInvalidaException("requisicao invalida");
        assertEquals("requisicao invalida", ex.getMessage());
    }

    @Test
    public void exceptionsAreRuntimeExceptions() {
        assertThrows(RuntimeException.class, () -> {
            throw new InternalServerErrorException("x");
        });
        assertThrows(RuntimeException.class, () -> {
            throw new RequisicaoInvalidaException("y");
        });
    }
}
