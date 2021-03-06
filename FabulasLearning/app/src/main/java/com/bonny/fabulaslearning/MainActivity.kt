package com.bonny.fabulaslearning

import android.app.AlertDialog
import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bonny.fabulas.MenuFragment


class MainActivity : AppCompatActivity() {

    private var fragment: Fragment? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
                .beginTransaction()
                .add(R.id.my_frame, MenuFragment(),"FRAG_MENU")
                .commit()

    }


    override fun onBackPressed() {

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
