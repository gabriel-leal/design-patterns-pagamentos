package com.gabriel.pagamentos.service;

import com.gabriel.pagamentos.dto.PagamentoResponse;
import org.springframework.stereotype.Service;

@Service
public class ComprovanteService {

    public void gerar(PagamentoResponse response) {
        System.out.println(
                "Comprovante gerado para o pedido " + response.pedidoId()
        );
    }
}
