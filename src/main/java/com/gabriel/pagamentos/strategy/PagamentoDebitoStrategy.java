package com.gabriel.pagamentos.strategy;

import com.gabriel.pagamentos.dto.PagamentoRequest;
import com.gabriel.pagamentos.dto.PagamentoResponse;
import com.gabriel.pagamentos.enums.FormaPagamento;
import com.gabriel.pagamentos.enums.StatusPagamento;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PagamentoDebitoStrategy implements PagamentoStrategy {

    @Override
    public PagamentoResponse pagar(PagamentoRequest request) {
        return new PagamentoResponse(
                request.pedidoId(),
                request.valor(),
                FormaPagamento.DEBITO,
                StatusPagamento.APROVADO,
                "Pagamento no cartão de débito aprovado.",
                LocalDateTime.now()
        );
    }

    @Override
    public FormaPagamento getFormaPagamento() {
        return FormaPagamento.DEBITO;
    }
}
