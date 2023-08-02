package com.gabriel.organizador.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import coil.load
import com.gabriel.organizador.R
import com.gabriel.organizador.dao.ProdutosDao
import com.gabriel.organizador.databinding.ActivityFormularioProdutoBinding
import com.gabriel.organizador.databinding.FormularioImagemBinding
import com.gabriel.organizador.extensions.tentaCarregarImagem
import com.gabriel.organizador.model.Produto
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private var url : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuraBotaoSalvar()
        setContentView(binding.root)
        binding.imagemFormulario.setOnClickListener {

            val bindingImagemDialog = FormularioImagemBinding.inflate(layoutInflater)
            bindingImagemDialog.formularioImagemBotaoCarregar.setOnClickListener {
                url = bindingImagemDialog.formularioImagemUrl.text.toString()
                bindingImagemDialog.formularioImagemImageview.tentaCarregarImagem(url)

            }


            AlertDialog.Builder(this)
                .setView(bindingImagemDialog.root)
                .setPositiveButton("Confirma") { _, _ ->
                    binding.imagemFormulario.load(url)
                }
                .setNegativeButton("Cancela") { _, _ -> }
                .show()
        }
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.botaoSalvar
        val dao = ProdutosDao()
        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
            dao.adiciona(produtoNovo)
            finish()
        }
    }

    private fun criaProduto(): Produto {
        val campoNome = binding.nome
        val nome = campoNome.text.toString()
        val campoDescricao = binding.descricao
        val descricao = campoDescricao.text.toString()
        val campoValor = binding.valor
        val valorTexto = campoValor.text.toString()
        val valor = if (valorTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorTexto)
        }

        return Produto(
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }
}