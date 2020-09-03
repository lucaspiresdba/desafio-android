package com.picpay.desafio.android.iu.activity

import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.isToHide
import com.picpay.desafio.android.iu.view.ContactListAdapter
import com.picpay.desafio.android.viewmodel.ContactViewModel
import com.picpay.desafio.android.viewmodel.ResultState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: ContactViewModel by viewModel()
    private val usersAdapter by lazy { ContactListAdapter() }
    private val progressBar: ProgressBar by lazy { findViewById<ProgressBar>(R.id.user_list_progress_bar) }
    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val buttonReload: Button by lazy { findViewById<Button>(R.id.button_try_again) }
    private val feedbackMessage: TextView by lazy { findViewById<TextView>(R.id.text_feedback) }
    private val container: ConstraintLayout by lazy { findViewById<ConstraintLayout>(R.id.container) }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        configureRecyclerView()
        configureObservable()
        configureView()
        viewModel.retrieveData()
    }

    private fun configureView() {
        buttonReload.setOnClickListener { viewModel.retrieveData() }
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
                is ResultState.Success -> usersAdapter.contacts = it.list
                is ResultState.EmptyList -> showFeedbackUser(getString(R.string.no_contacts))
                is ResultState.OnError -> showFeedbackUser(getString(R.string.error))
            }
        })

        viewModel.isLoading.observe(this, Observer {
            progressBar.isToHide(it)
        })

        viewModel.feedbackUser.observe(this, Observer {
            feedbackMessage.isToHide(it)
            buttonReload.isToHide(it)
            container.isToHide(!it)
        })
    }

    private fun showFeedbackUser(feedback: String) {
        feedbackMessage.text = feedback
    }
}
