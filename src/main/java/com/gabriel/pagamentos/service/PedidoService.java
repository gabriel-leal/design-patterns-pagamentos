package com.gabriel.pagamentos.service;

import com.gabriel.pagamentos.exception.PedidoInvalidoException;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    public void validarPedido(Long pedidoId) {
        if (pedidoId == null || pedidoId <= 0) {
            throw new PedidoInvalidoException("Pedido inválido.");
        }

        System.out.println("Pedido " + pedidoId + " validado.");
    }

    public void atualizarComoPago(Long pedidoId) {
        System.out.println("Pedido " + pedidoId + " atualizado para PAGO.");
    }
}
