package ro.androidproject.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val toggle=ActionBarDrawerToggle(this,drawer_layout,toolbar,R.string.open,R.string.close)
        toggle.isDrawerIndicatorEnabled=true
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        replacefragment(HomeFragment.newInstance(),true)

        navigation_bar.setNavigationItemSelectedListener(this)

    }



    fun replacefragment(fragment: Fragment, istransition:Boolean){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        if(istransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransition.replace(R.id.frame_layout,fragment).addToBackStack(fragment.javaClass.simpleName)
        fragmentTransition.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.Home)
        {
            val fragment = HomeFragment.newInstance()
            replacefragment(fragment, false)
            Toast.makeText(applicationContext,"Clicked",Toast.LENGTH_SHORT).show()
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        if(item.itemId==R.id.About)
        {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        return true
    }
}