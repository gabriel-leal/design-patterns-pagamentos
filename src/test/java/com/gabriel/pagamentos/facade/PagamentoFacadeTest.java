package com.gabriel.pagamentos.facade;

import com.gabriel.pagamentos.dto.PagamentoRequest;
import com.gabriel.pagamentos.dto.PagamentoResponse;
import com.gabriel.pagamentos.enums.FormaPagamento;
import com.gabriel.pagamentos.enums.StatusPagamento;
import com.gabriel.pagamentos.factory.PagamentoStrategyFactory;
import com.gabriel.pagamentos.service.ComprovanteService;
import com.gabriel.pagamentos.service.NotificacaoService;
import com.gabriel.pagamentos.service.PedidoService;
import com.gabriel.pagamentos.strategy.PagamentoPixStrategy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PagamentoFacadeTest {

    @Test
    void deveProcessarPagamentoPix() {

        PedidoService pedidoService = new PedidoService();
        NotificacaoService notificacaoService = new NotificacaoService();
        ComprovanteService comprovanteService = new ComprovanteService();

        PagamentoStrategyFactory factory =
                new PagamentoStrategyFactory(
                        List.of(new PagamentoPixStrategy())
                );

        PagamentoFacade facade = new PagamentoFacade(
                pedidoService,
                factory,
                notificacaoService,
                comprovanteService
        );

        PagamentoRequest request = new PagamentoRequest(
                1L,
                new BigDecimal("150.00"),
                FormaPagamento.PIX
        );

        PagamentoResponse response = facade.processar(request);

        assertEquals(StatusPagamento.APROVADO, response.status());
        assertEquals(FormaPagamento.PIX, response.formaPagamento());
        assertEquals(new BigDecimal("150.00"), response.valor());
    }
}
