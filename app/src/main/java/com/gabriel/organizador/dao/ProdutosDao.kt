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
            valor = BigDecimal(10.00),
            imagem = "https://images.pexels.com/photos/1093038/pexels-photo-1093038.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
            )
        )
    }
}