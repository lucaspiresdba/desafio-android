package com.picpay.desafio.android.iu.activity

import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.isToHide
import com.picpay.desafio.android.iu.view.UserListAdapter
import com.picpay.desafio.android.viewmodel.MainActivityViewModel
import com.picpay.desafio.android.viewmodel.ResultState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainActivityViewModel by viewModel()
    private val usersAdapter by lazy { UserListAdapter() }
    private val progressBar: ProgressBar by lazy { findViewById<ProgressBar>(R.id.user_list_progress_bar) }
    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        configureRecyclerView()
        configureObservable()
        viewModel.retrieveData()
    }

    private fun configureRecyclerView() {
        with(recyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = usersAdapter
        }
    }

    private fun configureObservable() {

        viewModel.observableResult.observe(this, Observer {
            when (it) {
                is ResultState.Success -> usersAdapter.users = it.list
                else -> showErrorMessage()
            }
        })

        viewModel.isLoading.observe(this, Observer {
            progressBar.isToHide(it)
        })
    }

    private fun showErrorMessage() =
        Toast.makeText(this, "Ocorreu um erro", Toast.LENGTH_LONG).show()
}
