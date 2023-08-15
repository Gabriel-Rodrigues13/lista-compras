package com.gabriel.organizador.ui.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.gabriel.organizador.R
import com.gabriel.organizador.databinding.ActivityDetalhesProdutoBinding
import com.gabriel.organizador.extensions.formataParaMoedaBrasileira
import com.gabriel.organizador.extensions.tentaCarregarImagem
import com.gabriel.organizador.model.Produto
import com.gabriel.organizador.ui.adapter.ListaProdutosAdapter

private const val TAG = "Detalhes menu"

class DetalhesProdutosActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_detalhes_produto_editar -> {
                Log.i(TAG, "onOptionsItemSelected: editar")
            }

            R.id.menu_detalhes_produto_remover -> {
                Log.i(TAG, "onOptionsItemSelected: remover")
            }
        }
        return super.onOptionsItemSelected(item)
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
            preencheDados(produtoCarregado)
        } ?: finish()
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
