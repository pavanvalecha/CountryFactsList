package prv.com.countryfacts.view

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import prv.com.countryfacts.R
import prv.com.countryfacts.base.BaseActivity
import timber.log.Timber

class MainActivity : BaseActivity() {

    private lateinit var navigationController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate()")
        setContentView(R.layout.activity_main)
        navigationController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this, navigationController)
    }

    fun setActionBarTitle(title: String?) {
        supportActionBar!!.title = title
    }

}
