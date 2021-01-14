package com.yemen.oshopping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yemen.oshopping.ui.AddCategoryFragment
import com.yemen.oshopping.ui.AddUserFragment
import com.yemen.oshopping.ui.ProductDetailsFragment
import com.yemen.oshopping.ui.ShowProductFragment
import kotlinx.android.synthetic.main.activity_main_screen.*

class MainScreen : AppCompatActivity(),Home_Fragment.Callbacks, AdminScreen.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        supportActionBar?.hide()



        val fragment = Home_Fragment()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
        title = resources.getString(R.string.add_category)
        //loadFragment(Category_Fragment())
        navigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_Home -> {
                    title = resources.getString(R.string.Home)
                    loadFragment(Home_Fragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_Category -> {
                    title = resources.getString(R.string.Category)
                    loadFragment(Category_Fragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_Cart -> {
                    title = resources.getString(R.string.Cart)
                   // loadFragment(Cart_Fragment())
                    loadFragment(AddUserFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_Purchases -> {
                    title = resources.getString(R.string.Purchases)
                    //loadFragment(Purchases_Fragment())
                    loadFragment(AddCategoryFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_Profile -> {
                    title = resources.getString(R.string.Profile)
                    loadFragment(Profile_Fragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false

        }

    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

    override fun onProductSelected(product_id: Int) {
        //remove the double slash below to show the product details
        val fragment = ProductDetailsFragment.newInstance(product_id)
        supportFragmentManager
            .beginTransaction()
           .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCategorySelected(categoryId: Int?) {
        val fragment = AddCategoryFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}