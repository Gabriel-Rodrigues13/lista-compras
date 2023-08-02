package com.gabriel.organizador.dao

import com.gabriel.organizador.model.Produto
import java.math.BigDecimal

class ProdutosDao {
    fun adiciona(produto : Produto){
        produtos.add(produto)
    }
    fun buscaTodos() : List<Produto>{
        return produtos.toList()
    }

    companion object {
        private val produtos = mutableListOf<Produto>(
            Produto(nome = "Banana",
            descricao = "Banana prata",
            valor = BigDecimal(10.00)
            )
        )
    }
}