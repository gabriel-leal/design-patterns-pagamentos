package com.gabriel.pagamentos.strategy;

import com.gabriel.pagamentos.dto.PagamentoRequest;
import com.gabriel.pagamentos.dto.PagamentoResponse;
import com.gabriel.pagamentos.enums.FormaPagamento;
import com.gabriel.pagamentos.enums.StatusPagamento;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PagamentoCreditoStrategy implements PagamentoStrategy {

    @Override
    public PagamentoResponse pagar(PagamentoRequest request) {
        return new PagamentoResponse(
                request.pedidoId(),
                request.valor(),
                FormaPagamento.CREDITO,
                StatusPagamento.APROVADO,
                "Pagamento no cartão de crédito aprovado em 1x.",
                LocalDateTime.now()
        );
    }

    @Override
    public FormaPagamento getFormaPagamento() {
        return FormaPagamento.CREDITO;
    }
}
