package com.msilva.cicciBolos.model.tipo;

import java.util.Map;

public class JsonObject {

    public static Tipo converterTipo(Map<String, Object> registro) {
        return new Tipo(
                (Integer) registro.get("cd_tipoproduto"),
                (String) registro.get("nm_tipoproduto"));
    }
}
