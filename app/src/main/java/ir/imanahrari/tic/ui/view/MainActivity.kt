package ir.imanahrari.tic.ui.view

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import ir.imanahrari.tic.R
import ir.imanahrari.tic.service.database.ADatabase
import ir.imanahrari.tic.service.model.DataModel
import ir.imanahrari.tic.service.utilities.deleteUser
import ir.imanahrari.tic.service.utilities.isUserLogin
import ir.imanahrari.tic.service.utilities.setRtl
import ir.imanahrari.tic.service.utilities.startProcess
import ir.imanahrari.tic.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(){
    lateinit var viewModel: MainViewModel
    private lateinit var binding: ir.imanahrari.tic.databinding.MainLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRtl()

        ADatabase.getInstance(this)
        binding = DataBindingUtil.setContentView(this, R.layout.main_layout)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setViewModelObserver()
        startProcess()
    }

    private fun setViewModelObserver(){
        viewModel.isHtmlProcessed.observeForever { binding.isHtmlProcessed = it }
        viewModel.dataLive.observeForever { binding.data = DataModel(it, this) }
        viewModel.weekLive.observeForever { binding.week = it }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exit -> deleteUser(this)

            R.id.info ->
                AlertDialog.Builder(this)
                    .setTitle(R.string.info_i)
                    .setMessage(R.string.info)
                    .setPositiveButton(R.string.okay){ _: DialogInterface, _: Int->}
                    .show()
                    .isShowing

            else -> super.onOptionsItemSelected(item)
        }
    }
}