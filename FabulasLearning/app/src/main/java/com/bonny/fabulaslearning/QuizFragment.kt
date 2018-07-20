package com.bonny.fabulaslearning


import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.bonny.fabulas.MenuFragment
import kotlinx.android.synthetic.main.fragment_quiz.*


/**
 * A simple [Fragment] subclass.
 *
 */
class QuizFragment : Fragment() {

    private var btnSubmit: Button? = null
    private var txtViewQuestion: TextView? = null
    private var txtViewCount: TextView? = null
    private var group: RadioGroup? = null

    private var questions: Array<String>? = null
    private var answers: Array<String>? = null
    private var indexNextQuestion = -1
    private var indexAnswer = 0
    private var handler: Handler? = null
    private var shakeAnimation: Animation? = null
    private var v: View? = null
    private var media: MediaPlayer? = null

    private var questions_id: Int = 0
    private var anwers_questions_id: Int = 0
    private var count = 0

    fun initialyQuestionary(questions_id: Int, anwers_questions_id: Int) {
        this.questions_id = questions_id
        this.anwers_questions_id = anwers_questions_id
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_quiz, container, false)

        shakeAnimation = AnimationUtils.loadAnimation(activity, R.anim.shake)
        shakeAnimation!!.setRepeatCount(3)

        questions = resources.getStringArray(questions_id)
        answers = resources.getStringArray(anwers_questions_id)

        handler = Handler()

        group = v!!.findViewById<RadioGroup>(R.id.group)
        btnSubmit = v!!.findViewById<Button>(R.id.btnSubmit)
        txtViewQuestion = v!!.findViewById<TextView>(R.id.txtViewQuestion)
        txtViewCount = v!!.findViewById<TextView>(R.id.txtViewCount)


        //ação que inicia a verificação da resposta do usuário
        btnSubmit!!.setOnClickListener(View.OnClickListener {

            for (i in 2 until group!!.getChildCount()) { //inicia em 2 pois a posição 0 é um TextView e 1 é um radioButton invisível

                val rbtn = group!!.getChildAt(i) as RadioButton

                if (rbtn.isChecked) {
                    verifyAnswer(rbtn, Integer.parseInt(answers!![indexAnswer]) + 1, i)
                    break
                }
            }
        })

        nextQuestion()

        return v
    }

    private fun verifyAnswer(rbtn: RadioButton, idRbtn: Int, idAnswer: Int) {
        if (idRbtn == idAnswer) {
            isEnableButtons(false)

            rbtn.setTextColor(resources.getColor(R.color.colorCorrect))
            media = MediaPlayer.create(context, R.raw.question_right)
            media!!.start()


            if (indexAnswer == answers!!.size - 1) {

                btnSubmit!!.isEnabled = false

                var alertDialog = AlertDialog.Builder(activity)
                alertDialog.setTitle("Moral da Historia") // O Titulo da notificação
                alertDialog.setMessage(questions!![indexNextQuestion + 1])


                alertDialog.setPositiveButton("Menu", { _,_  ->

                    activity!!.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.my_frame, MenuFragment(),"FRAG_MENU")
                            .commit()
                })

                alertDialog.show()

            }else {
                indexAnswer++

                     handler!!.postDelayed(Runnable { nextQuestion() }, 2000)

            }


        } else {
            media = MediaPlayer.create(context, R.raw.question_error)
            media!!.start()

            rbtn.setTextColor(resources.getColor(R.color.colorIncorrect))
            rbtn.isEnabled = false
            rbtn.isChecked = false
            rbtn.startAnimation(shakeAnimation)


        }
    }

    private fun nextQuestion() {

        txtViewQuestion!!.text = questions!![++indexNextQuestion]
        txtViewCount!!.setText((++count).toString())


        for (i in 2 until group!!.getChildCount()) { // inicia em 2 pois a posição 0 é um TextView e 1 é um radioButton invisível

            //indexNextQuestion++
            val rbtn = group!!.getChildAt(i) as RadioButton
            rbtn.text = questions!![++indexNextQuestion]
            rbtn.setTextColor(resources.getColor(R.color.colorItem))

            rbtn.isChecked = false
            rbtn.isEnabled = true
        }

        //deixa checada a primeira opção que é o rbtn invisível para evitar erros na interface
        //o botão escolhido anteriormente não consegue ser mais selecionado na pergunta seguinte
        (group!!.getChildAt(1) as RadioButton).isChecked = true
        btnSubmit!!.isEnabled = true

    }


    private fun isEnableButtons(enable:Boolean){

        for (i in 2 until group!!.getChildCount())  // inicia em 2 pois a posição 0 é um TextView e 1 é um radioButton invisível
            (group!!.getChildAt(i) as RadioButton)!!.isEnabled = enable

        btnSubmit!!.isEnabled = false

    }


}
