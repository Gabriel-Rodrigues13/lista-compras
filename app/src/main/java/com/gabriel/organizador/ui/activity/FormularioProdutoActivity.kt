package com.gabriel.organizador.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.gabriel.organizador.R
import com.gabriel.organizador.dao.ProdutosDao
import com.gabriel.organizador.model.Produto
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity(R.layout.activity_formulario_produto) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuraBotaoSalvar()


    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = findViewById<Button>(R.id.botao_salvar)
        val dao = ProdutosDao()
        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
            dao.adiciona(produtoNovo)
            finish()
        }
    }
    
    private fun criaProduto(): Produto {
        val campoNome = findViewById<EditText>(R.id.nome)
        val nome = campoNome.text.toString()
        val campoDescricao = findViewById<EditText>(R.id.descricao)
        val descricao = campoDescricao.text.toString()
        val campoValor = findViewById<EditText>(R.id.valor)
        val valorTexto = campoValor.text.toString()
        val valor = if (valorTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorTexto)
        }

        return Produto(
            nome = nome,
            descricao = descricao,
            valor = valor
        )
    }
}