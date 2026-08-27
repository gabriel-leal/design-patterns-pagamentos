package com.gabriel.pagamentos.factory;

import com.gabriel.pagamentos.enums.FormaPagamento;
import com.gabriel.pagamentos.exception.FormaPagamentoNaoSuportadaException;
import com.gabriel.pagamentos.strategy.PagamentoStrategy;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class PagamentoStrategyFactory {

    private final Map<FormaPagamento, PagamentoStrategy> strategies;

    public PagamentoStrategyFactory(List<PagamentoStrategy> strategies) {
        this.strategies = new EnumMap<>(FormaPagamento.class);

        for (PagamentoStrategy strategy : strategies) {
            this.strategies.put(strategy.getFormaPagamento(), strategy);
        }
    }

    public PagamentoStrategy obter(FormaPagamento formaPagamento) {
        PagamentoStrategy strategy = strategies.get(formaPagamento);

        if (strategy == null) {
            throw new FormaPagamentoNaoSuportadaException(
                    "Forma de pagamento não suportada: " + formaPagamento
            );
        }

        return strategy;
    }
}
