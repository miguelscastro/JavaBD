package com.msilva.ciccibolos.model.produto;

import java.util.Map;

public class JsonObject {
    public static Produto converterProduto(Map<String, Object> registro) {
        /*
         * As chaves do Map vem em String, Object. A função converte esses pares de
         * String, Object para tipos mais específicos, por exemplo, transformando
         * idSobremesas(Object) pra idSobremesar(Integer)
         * 
         * 
         * Converte Object para tipo primitivo
         */
        return new Produto(
                (Integer) registro.get("cd_produto"),
                (String) registro.get("nm_produto"),
                (String) registro.get("ds_produto"),
                (Integer) registro.get("cd_tipoproduto"));
    }
}
