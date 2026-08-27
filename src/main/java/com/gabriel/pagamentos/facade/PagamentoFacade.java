package com.gabriel.pagamentos.facade;

import com.gabriel.pagamentos.dto.PagamentoRequest;
import com.gabriel.pagamentos.dto.PagamentoResponse;
import com.gabriel.pagamentos.factory.PagamentoStrategyFactory;
import com.gabriel.pagamentos.service.ComprovanteService;
import com.gabriel.pagamentos.service.NotificacaoService;
import com.gabriel.pagamentos.service.PedidoService;
import com.gabriel.pagamentos.strategy.PagamentoStrategy;
import org.springframework.stereotype.Service;

@Service
public class PagamentoFacade {

    private final PedidoService pedidoService;
    private final PagamentoStrategyFactory strategyFactory;
    private final NotificacaoService notificacaoService;
    private final ComprovanteService comprovanteService;

    public PagamentoFacade(
            PedidoService pedidoService,
            PagamentoStrategyFactory strategyFactory,
            NotificacaoService notificacaoService,
            ComprovanteService comprovanteService
    ) {
        this.pedidoService = pedidoService;
        this.strategyFactory = strategyFactory;
        this.notificacaoService = notificacaoService;
        this.comprovanteService = comprovanteService;
    }

    public PagamentoResponse processar(PagamentoRequest request) {

        pedidoService.validarPedido(request.pedidoId());

        PagamentoStrategy strategy =
                strategyFactory.obter(request.formaPagamento());

        PagamentoResponse response = strategy.pagar(request);

        pedidoService.atualizarComoPago(request.pedidoId());
        comprovanteService.gerar(response);
        notificacaoService.enviar(response);

        return response;
    }
}
