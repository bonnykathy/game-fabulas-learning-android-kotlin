package com.bonny.fabulaslearning

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat.animate
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_fabula.*
import java.io.IOException



/**
 * A simple [Fragment] subclass.
 *
 */
 class FabulaFragment() : Fragment() {

    private var btnNext: Button? = null
    private var btnBefore: Button? = null
    private var txtViewTitle:TextView?=null
    private var fabulaName:String? = null
    private var idSong:Int = 0
    private var title:String? = null
    private var list_fabulas:ArrayList<String>?= null
    internal var handler: Handler?= null
    private var countImage = 0
    private var indexNarrative = 0
    private var media: MediaPlayer?= null
    private var actionButtonQuiz:ActionPressedQuizButton? = null
    private var action:ActionPressButton? = null
    private var myImageView:ImageView?=null

    fun initialize(name:String, idSong:Int, title:String){
        this.fabulaName = name
        this.idSong = idSong
        this.title = title
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val  view = inflater.inflate(R.layout.fragment_fabula, container, false)

        list_fabulas = ArrayList()
        handler = Handler()

        txtViewTitle = view!!.findViewById<TextView>(R.id.txtViewTitle)
        txtViewTitle!!.text = title

        myImageView = view!!.findViewById<ImageView>(R.id.myImageView)

        btnNext = view!!.findViewById<Button>(R.id.btnNext)
        btnBefore = view!!.findViewById<Button>(R.id.btnBefore)

         action = ActionPressButton()
        actionButtonQuiz = ActionPressedQuizButton()

        btnNext!!.setOnClickListener(action)
        btnBefore!!.setOnClickListener(action)

        loadListFabulas()
        btnBefore!!.isEnabled = false


        Log.i("MY_LOGS","OnCreateView()")

        loadImage(0)
        return view
    }

     override fun onStart() {
        super.onStart()

         media = MediaPlayer.create(context,idSong)
        media!!.isLooping= true
        media!!.start()

         Log.i("MY_LOGS","OnStart()")

    }





    private fun loadListFabulas(){

        val assets = activity!!.assets
        list_fabulas!!.clear()

        try {
            var paths = assets.list(fabulaName)

            for (path in paths)
                list_fabulas!!.add(path.replace(".PNG", ""))
        }
        catch(ioe: IOException){
            Log.e("MY_LOGS","ERRO AO LER IMAGENS")
        }
    }

    private fun loadImage(index:Int){

        if(countImage == list_fabulas!!.size - 1){
            btnNext!!.text = "QUIZ"

            btnNext!!.setOnClickListener(actionButtonQuiz)

        }else{

            btnNext!!.setOnClickListener(action)

            btnNext!!.isEnabled  = true
            btnNext!!.text = "Seguinte"
        }

        btnBefore!!.isEnabled = if (countImage == 0) false else true

        val assets = activity!!.assets
        var nameImg = list_fabulas!!.get(index)


        var stream = assets!!.open(fabulaName+ "/" + nameImg+".PNG")
        var img = Drawable.createFromStream(stream, nameImg)
        myImageView!!.setImageDrawable(img)



    }

    inner class ActionPressedQuizButton : View.OnClickListener{
        override fun onClick(v: View?) {

            val questions = QuizFragment()

            if (fabulaName == "leon")
                questions.initialyQuestionary(R.array.questionary_leon, R.array.answers_questionary_leon)
            else if (fabulaName == "fox")
                questions.initialyQuestionary(R.array.questionary_fox, R.array.answers_questionary_fox)
            else if(fabulaName == "mousetrap")
                questions.initialyQuestionary(R.array.questionary_mousetrap, R.array.answers_questionary_mousetrap)
            else
                questions.initialyQuestionary(R.array.questionary_travelers, R.array.answers_questionary_travlers)
            activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.my_frame, questions,"FRAG_QUIZ")
                    .commit()
            media!!.stop()

        }
    }

    inner class ActionPressButton : View.OnClickListener{

        override fun onClick(v: View?) {

            btnBefore!!.isEnabled = false
            btnNext!!.isEnabled = false
            when(v!!.id){

                R.id.btnNext ->{
                    handler!!.postDelayed(Runnable { animate(true) }, 1000)
                    indexNarrative++
                }
                R.id.btnBefore ->{
                    animate(false)
                    indexNarrative--
                }
            }
        }

        private fun animate(animateOut:Boolean){

            val centerX = (myImageView!!.left + myImageView!!.right) / 2
            val centerY = (myImageView!!.top + myImageView!!.bottom) / 2

            val r = Math.max(myImageView!!.width, myImageView!!.height)

            val animator: Animator

            if (animateOut) {
                animator = ViewAnimationUtils.createCircularReveal(myImageView, centerX, centerY, r.toFloat(), 0f)
                animator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        loadImage(++countImage)
                        btnBefore!!.isEnabled = true
                        btnNext!!.isEnabled = true
                    }
                })
            } else {
                animator = ViewAnimationUtils.createCircularReveal(myImageView, centerX, centerY, 0f, r.toFloat())
                loadImage(--countImage)
            }
            animator.duration = 500
            animator.start()
        }

    }

    override fun onPause() {
        super.onPause()
        media!!.stop()

    }
}
