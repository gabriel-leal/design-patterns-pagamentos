package com.gabriel.pagamentos.dto;

import com.gabriel.pagamentos.enums.FormaPagamento;
import com.gabriel.pagamentos.enums.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoResponse(
        Long pedidoId,
        BigDecimal valor,
        FormaPagamento formaPagamento,
        StatusPagamento status,
        String mensagem,
        LocalDateTime processadoEm
) {
}
