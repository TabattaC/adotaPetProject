package com.cupcakeProject.handler;

public class CampoObrigatorioException extends BusinessException{
    public CampoObrigatorioException(String campo) {
        super("O campo %s é obrigatorio", campo);
    }
}
