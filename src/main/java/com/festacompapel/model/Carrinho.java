package com.festacompapel.model;

import java.util.Collection;
import java.util.Objects;

public class Carrinho {

    private int pedidoId;

    private Collection produtos;

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Collection getProdutos() {
        return produtos;
    }

    public void setProdutos(Collection produtos) {
        this.produtos = produtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrinho carrinho = (Carrinho) o;
        return pedidoId == carrinho.pedidoId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pedidoId);
    }
}
