package com.gabriel.organizador.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gabriel.organizador.database.AppDatabase
import com.gabriel.organizador.databinding.ActivityFormularioProdutoBinding
import com.gabriel.organizador.extensions.tentaCarregarImagem
import com.gabriel.organizador.model.Produto
import com.gabriel.organizador.ui.dialog.FormularioImagemDialog
import java.math.BigDecimal
class FormularioProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private val produtosDao by lazy{
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }


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

    }

    override fun onResume() {
        super.onResume()
        produtosDao.buscaPorId(produtoId)?.let {
            tentaBuscarProduto(it)
        }

    }

    private fun tentaBuscarProduto(it: Produto) {
        title = "Alterar Produto"
        preencheCampos(it)
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
//            if(produtoId > 0){
//                produtosDao.atualiza(produtoNovo)
//            }else{
//            produtosDao.salvar(produtoNovo)
//            }
            produtosDao.salvar(produtoNovo)
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
            id = produtoId,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }
}

