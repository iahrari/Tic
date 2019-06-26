package ir.imanahrari.tic.ui.view

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager

import com.google.android.material.tabs.TabLayout
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.imanahrari.tic.R
import ir.imanahrari.tic.databinding.MainLayoutBinding
import ir.imanahrari.tic.service.model.DataModel
import ir.imanahrari.tic.service.utilities.*
import ir.imanahrari.tic.ui.adapter.ViewPagerAdapter
import ir.imanahrari.tic.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(){

    lateinit var viewModel: MainViewModel
    lateinit var binding: MainLayoutBinding
    lateinit var absentsFragment: AbsentsListFragment
    private lateinit var classesFragment: ExtraClassesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRtl()

        absentsFragment = AbsentsListFragment()
        classesFragment = ExtraClassesFragment()

        startUIControllers()

        setSupportActionBar(binding.mainToolbar)
        setupTabs(binding.tabs, binding.viewPager)
        setViewModelObservers()
        startLoginIn()
    }

    private fun startUIControllers(){
        binding = DataBindingUtil.setContentView(this, R.layout.main_layout)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private fun setViewModelObservers(){
        viewModel.needDialog.observeForever {
                openSimpleDialog(
                    if(it) R.string.no_internet_title  else R.string.no_data_title,
                    if(it) R.string.no_internet_body  else R.string.no_data,
                    R.string.okay,
                    DialogInterface.OnClickListener{_, _ -> viewModel.setContext(this)}
                )
        }
        viewModel.isHtmlProcessed.observeForever { binding.isHtmlProcessed = it }
        viewModel.dataLive.observeForever { absentsFragment.setData(DataModel(it, this)) }
        viewModel.weekLive.observeForever { binding.week = it }
        viewModel.classDataLive.observeForever { classesFragment.setData(it) }
        viewModel.isOnline.observeForever { binding.mainToolbar.subtitle = if(it) "آنلاین" else "آفلاین" }
        viewModel.isLogInFailed.observeForever { if (it) onLogInFailed() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exit -> deleteUser()
            R.id.info -> openSimpleDialog(
                R.string.info_i,
                R.string.info,
                R.string.okay,
                DialogInterface.OnClickListener { _, _ -> }
            )
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViewPager(v: ViewPager){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(absentsFragment, "غیبت")
        adapter.addFragment(classesFragment, "جبرانی")
        v.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun setupTabs(tabLayout: TabLayout, viewPager: ViewPager){
        setupViewPager(viewPager)
        viewPager.rotationY = 180f
        tabLayout.post { tabLayout.setupWithViewPager(viewPager) }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    private fun onLogInFailed(){
        Toast.makeText(this, "اطلاعات وارد شده درست نیست.", Toast.LENGTH_LONG).show()
        deleteUser()
    }
}