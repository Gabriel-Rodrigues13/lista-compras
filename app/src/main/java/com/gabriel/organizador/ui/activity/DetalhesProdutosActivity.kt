package com.gabriel.organizador.ui.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.room.Database
import com.gabriel.organizador.R
import com.gabriel.organizador.database.AppDatabase
import com.gabriel.organizador.database.dao.ProdutoDao
import com.gabriel.organizador.databinding.ActivityDetalhesProdutoBinding
import com.gabriel.organizador.extensions.formataParaMoedaBrasileira
import com.gabriel.organizador.extensions.tentaCarregarImagem
import com.gabriel.organizador.model.Produto
import com.gabriel.organizador.ui.adapter.ListaProdutosAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "Detalhes menu"

class DetalhesProdutosActivity : AppCompatActivity() {

    private var produtoId: Long = 0L
    private var produto: Produto? = null

    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    private val produtoDao by lazy {
        AppDatabase.instancia(this).produtoDao()

    }
    private val scope = CoroutineScope(IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    override fun onResume() {
        super.onResume()
        buscaProduto()
    }

    private fun buscaProduto() {
        scope.launch {
            produto = produtoDao.buscaPorId(produtoId)
            withContext(Dispatchers.Main){
                produto?.let {
                    preencheDados(it)
                } ?: finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (produto != null) {

            when (item.itemId) {
                R.id.menu_detalhes_produto_editar -> {
                    Log.i(TAG, "onOptionsItemSelected: editar")
                    val intent = Intent(this, FormularioProdutoActivity::class.java).apply {
                        putExtra(CHAVE_PRODUTO_ID, produtoId)
                        startActivity(this)
                    }
                }
                R.id.menu_detalhes_produto_remover -> {
                    scope.launch {
                        produto?.let {
                            produtoDao.delete(it)
                        }
                        finish()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
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
