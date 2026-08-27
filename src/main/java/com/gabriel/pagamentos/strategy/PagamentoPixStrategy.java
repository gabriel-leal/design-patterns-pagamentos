package com.gabriel.pagamentos.strategy;

import com.gabriel.pagamentos.dto.PagamentoRequest;
import com.gabriel.pagamentos.dto.PagamentoResponse;
import com.gabriel.pagamentos.enums.FormaPagamento;
import com.gabriel.pagamentos.enums.StatusPagamento;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PagamentoPixStrategy implements PagamentoStrategy {

    @Override
    public PagamentoResponse pagar(PagamentoRequest request) {
        return new PagamentoResponse(
                request.pedidoId(),
                request.valor(),
                FormaPagamento.PIX,
                StatusPagamento.APROVADO,
                "Pagamento via PIX aprovado com sucesso.",
                LocalDateTime.now()
        );
    }

    @Override
    public FormaPagamento getFormaPagamento() {
        return FormaPagamento.PIX;
    }
}
