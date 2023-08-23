package com.gabriel.organizador.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gabriel.organizador.database.AppDatabase
import com.gabriel.organizador.database.dao.ProdutoDao
import com.gabriel.organizador.databinding.ActivityFormularioProdutoBinding
import com.gabriel.organizador.extensions.tentaCarregarImagem
import com.gabriel.organizador.model.Produto
import com.gabriel.organizador.ui.dialog.FormularioImagemDialog
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private val produtoDao: ProdutoDao by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }
    private val scope = MainScope()
    private var produtoId = 0L
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Cadastrar Produto"
        configuraBotaoSalvar()
        setContentView(binding.root)
        binding.imagemFormulario.setOnClickListener {
            FormularioImagemDialog(this).mostra(url) { imagem ->
                url = imagem
                binding.imagemFormulario.tentaCarregarImagem(url)
            }
        }
        tentaCarregarProduto()
        tentaBuscarProduto()

    }

    private fun tentaBuscarProduto() {
        lifecycleScope.launch {
            produtoDao.buscaPorId(produtoId).collect { produto ->
                produto?.let {
                    title = "Alterar Produto"
                    preencheCampos(it)
                }
            }
        }
    }


    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)

    }


    private fun preencheCampos(produto: Produto) {

        url = produto.imagem
        binding.imagemFormulario.tentaCarregarImagem(produto.imagem)
        binding.nome.setText(produto.nome)
        binding.descricao.setText(produto.descricao)
        binding.valor.setText(produto.valor.toPlainString())
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.botaoSalvar

        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
            lifecycleScope.launch {
                produtoDao.salvar(produtoNovo)
                finish()
            }
        }
    }

    private fun criaProduto(): Produto {
        val nome = binding.nome.getTextValue()
        val descricao = binding.descricao.getTextValue()
        val valor = binding.valor.getBigDecimalValue()

        return Produto(
            id = produtoId,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }

    private fun TextInputEditText.getTextValue() = this.text.toString()

    private fun TextInputEditText.getBigDecimalValue() = when(this.getTextValue().isBlank()){
        true -> BigDecimal.ZERO
        false -> BigDecimal(this.getTextValue())
    }




}

