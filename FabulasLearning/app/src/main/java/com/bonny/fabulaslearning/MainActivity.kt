package com.bonny.fabulaslearning

import android.app.AlertDialog
import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bonny.fabulas.MenuFragment
import com.bonny.fabulaslearning.R
//import com.bonny.fabulas.R.id.toolbar

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_menu.view.*

class MainActivity : AppCompatActivity() {

    private var fragment: Fragment? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //  setSupportActionBar(toolbar)
        supportFragmentManager
                .beginTransaction()
                .add(R.id.my_frame, MenuFragment(),"FRAG_MENU")
                .commit()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        //  super.onBackPressed()

        var  menu = supportFragmentManager.findFragmentByTag("FRAG_MENU")
        var  fabula = supportFragmentManager.findFragmentByTag("FRAG_FABULA")
        var  quiz = supportFragmentManager.findFragmentByTag("FRAG_QUIZ")


        if(fabula != null && fabula.isVisible ||
                quiz != null && quiz.isVisible) {


            var alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Alerta") // O Titulo da notificação
            alertDialog.setMessage("Deseja voltar ao menu?")


            alertDialog.setPositiveButton("Sim", { _,_  ->

                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.my_frame, MenuFragment(),"FRAG_MENU")
                        .commit()

            })

            alertDialog.setNegativeButton("Não", { _, _ ->
                //Aqui sera executado a instrução a sua escolha

            })
            alertDialog.show()

        }
        /*
        * Se o usuário decidir voltar no QUiz, ele deverá tbm ser encaminhado para o menu
        * porém, deve ser exibido o dialog perguntando se ele gostaria mesmo de fazer essa operação
        * */
        else
            finish()

    }


}
