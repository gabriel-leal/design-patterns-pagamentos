package com.gabriel.pagamentos.service;

import com.gabriel.pagamentos.dto.PagamentoResponse;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {

    public void enviar(PagamentoResponse response) {
        System.out.println(
                "Notificação enviada: pagamento do pedido "
                        + response.pedidoId()
                        + " foi "
                        + response.status()
        );
    }
}
