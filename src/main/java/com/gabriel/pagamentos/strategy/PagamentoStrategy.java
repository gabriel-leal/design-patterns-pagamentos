package com.gabriel.pagamentos.strategy;

import com.gabriel.pagamentos.dto.PagamentoRequest;
import com.gabriel.pagamentos.dto.PagamentoResponse;
import com.gabriel.pagamentos.enums.FormaPagamento;

public interface PagamentoStrategy {

    PagamentoResponse pagar(PagamentoRequest request);

    FormaPagamento getFormaPagamento();
}
