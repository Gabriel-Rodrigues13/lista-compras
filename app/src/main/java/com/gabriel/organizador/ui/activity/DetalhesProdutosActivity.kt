package com.gabriel.organizador.ui.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.gabriel.organizador.R
import com.gabriel.organizador.databinding.ActivityDetalhesProdutoBinding
import com.gabriel.organizador.extensions.formataParaMoedaBrasileira
import com.gabriel.organizador.extensions.tentaCarregarImagem
import com.gabriel.organizador.model.Produto
import com.gabriel.organizador.ui.adapter.ListaProdutosAdapter

class DetalhesProdutosActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    private fun tentaCarregarProduto() {
        //verificação de versão do compilador do SDK
        val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            //método novo para os SDK mais novos
            intent.getParcelableExtra(CHAVE_PRODUTO,Produto::class.java)
        } else{
            //método deprecated  para os SDK mais antigos
            intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)
        }
        userData?.let { produtoCarregado ->
            preencheDados(produtoCarregado)
        }?: finish()
    }

    private fun preencheDados(produtoEnviado: Produto) {
        with(binding) {
            activityDetalhesProdutoImagem.tentaCarregarImagem(produtoEnviado.imagem)
            activityDetalhesProdutoNome.text = produtoEnviado.nome
            activityDetalhesProdutoDescricao.text = produtoEnviado.descricao
            activityDetalhesProdutoValor.text = produtoEnviado.valor.formataParaMoedaBrasileira()
        }
    }
}