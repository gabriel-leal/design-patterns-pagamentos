package com.gabriel.pagamentos.factory;

import com.gabriel.pagamentos.enums.FormaPagamento;
import com.gabriel.pagamentos.strategy.PagamentoCreditoStrategy;
import com.gabriel.pagamentos.strategy.PagamentoDebitoStrategy;
import com.gabriel.pagamentos.strategy.PagamentoPixStrategy;
import com.gabriel.pagamentos.strategy.PagamentoStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PagamentoStrategyFactoryTest {

    @Test
    void deveRetornarStrategyPix() {
        List<PagamentoStrategy> strategies = List.of(
                new PagamentoPixStrategy(),
                new PagamentoCreditoStrategy(),
                new PagamentoDebitoStrategy()
        );

        PagamentoStrategyFactory factory =
                new PagamentoStrategyFactory(strategies);

        PagamentoStrategy strategy =
                factory.obter(FormaPagamento.PIX);

        assertInstanceOf(PagamentoPixStrategy.class, strategy);
    }
}
