package com.gabriel.organizador.ui.activity

import android.os.Build
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

    private var idProduto = 0L
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

    private fun tentaCarregarProduto() {
        //verificação de versão do compilador do SDK
        val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            //método novo para os SDK mais novos
            intent.getParcelableExtra(CHAVE_PRODUTO, Produto::class.java)
        } else {
            //método deprecated  para os SDK mais antigos
            intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)
        }
        userData?.let { produtoCarregado ->
            url = produtoCarregado.imagem
            title = "Alterar Produto"
            idProduto = produtoCarregado.id
            binding.imagemFormulario.tentaCarregarImagem(produtoCarregado.imagem)
            binding.nome.setText(produtoCarregado.nome)
            binding.descricao.setText(produtoCarregado.descricao)
            binding.valor.setText(produtoCarregado.valor.toPlainString())
        }
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.botaoSalvar

        val db = AppDatabase.instancia(this)
        val produtosDao = db.produtoDao()
        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
            if(idProduto > 0){
                produtosDao.atualiza(produtoNovo)
            }else{
            produtosDao.salvar(produtoNovo)
            }
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
            id = idProduto,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }
}