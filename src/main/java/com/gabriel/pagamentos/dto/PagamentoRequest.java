package com.gabriel.pagamentos.dto;

import com.gabriel.pagamentos.enums.FormaPagamento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PagamentoRequest(

        @NotNull
        @Positive
        Long pedidoId,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal valor,

        @NotNull
        FormaPagamento formaPagamento
) {
}
