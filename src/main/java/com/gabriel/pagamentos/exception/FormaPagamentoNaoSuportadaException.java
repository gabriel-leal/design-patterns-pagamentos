package com.gabriel.pagamentos.exception;

public class FormaPagamentoNaoSuportadaException extends RuntimeException {

    public FormaPagamentoNaoSuportadaException(String message) {
        super(message);
    }
}
