package com.gabriel.organizador.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.gabriel.organizador.R
import com.gabriel.organizador.database.AppDatabase
import com.gabriel.organizador.database.preferences.dataStore
import com.gabriel.organizador.database.preferences.usuarioLogadoPreferences
import com.gabriel.organizador.databinding.ActivityListaProdutosActivityBinding
import com.gabriel.organizador.extensions.vaiPara
import com.gabriel.organizador.ui.adapter.ListaProdutosAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ListaProdutosActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityListaProdutosActivityBinding.inflate(layoutInflater)
    }

    private val adapter = ListaProdutosAdapter(
        context = this
    )


    private val usuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    private val produtosDao by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
        lifecycleScope.launch() {
            launch {
                produtosDao.buscaTodos().collect { produto ->
                    adapter.atualiza(produto)
                }
            }
            dataStore.data.collect{preferences ->
                lifecycleScope.launch {
                    preferences[usuarioLogadoPreferences]?.let {usuarioId->
                        usuarioDao.buscaPorId(usuarioId).collect{
                            Log.i("ListaProduto", "onCreate: $it")
                        }
                    }?:vaiParaLogin()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produtos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_lista_produtos_sair_do_app->{
                lifecycleScope.launch {
                    dataStore.edit { preferences->
                        preferences.remove(usuarioLogadoPreferences)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun vaiParaLogin() {
        vaiPara(LoginActivity::class.java)
        finish()
    }


    private fun configuraFab() {
        val fab = binding.extendedFab
        fab.setOnClickListener {
            val intent = Intent(this, FormularioProdutoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItemListener = {
            val intent =
                Intent(this, DetalhesProdutosActivity::class.java).apply {
                    putExtra(
                        CHAVE_PRODUTO_ID,
                        it.id
                    )
                }
            startActivity(intent)
        }
        adapter.quandoClicaEmEditar = {
            Log.i("PopUp", "configuraRecyclerView: popUp editar")
        }
        adapter.quandoClicaEmRemover = {
            Log.i("PopUp", "configuraRecyclerView: popUp editar")
        }

    }
}