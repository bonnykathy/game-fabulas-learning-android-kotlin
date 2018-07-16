package com.bonny.fabulas


import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.bonny.fabulaslearning.FabulaFragment
import com.bonny.fabulaslearning.R
import kotlinx.android.synthetic.main.fragment_menu.*

/**
 * A simple [Fragment] subclass.
 *
 */
class MenuFragment : Fragment() {

    private var btnFox:Button? = null
    private var btnLeon:Button? = null
    private var btnMousetrap:Button? = null
    private var btnMouseTravelrs:Button? = null
    private var btnAbout:Button? = null
    private var btnHowPlay:Button? = null
    private var media:MediaPlayer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_menu, container, false)

        btnLeon = view.findViewById<Button>(R.id.btnLeon)
        btnFox = view.findViewById<Button>(R.id.btnFox)
        btnMousetrap = view.findViewById<Button>(R.id.btnMousetrap)
        btnMouseTravelrs = view.findViewById<Button>(R.id.btnTravelers)
        btnHowPlay = view.findViewById<Button>(R.id.btnHowPlay)
        btnAbout= view.findViewById<Button>(R.id.btnAbout)

        val action = ActionFabulaButtons()
        val actionMenu = ActionButtonsMenu()

        btnFox!!.setOnClickListener(action)
        btnLeon!!.setOnClickListener(action)
        btnMousetrap!!.setOnClickListener(action)
        btnMouseTravelrs!!.setOnClickListener(action)

        btnAbout!!.setOnClickListener(actionMenu)
        btnHowPlay!!.setOnClickListener(actionMenu)

        return view
    }

    private inner class ActionButtonsMenu :View.OnClickListener {

        override fun onClick(v: View) {

            var alertDialog = AlertDialog.Builder(activity)

            if(v.id == R.id.btnHowPlay) {
                alertDialog.setTitle(R.string.how_play)
                alertDialog.setMessage(R.string.how_play_desc)
            }else{
                alertDialog.setTitle(R.string.about) // O Titulo da notificação
                alertDialog.setMessage(R.string.about_desc)
            }

            alertDialog.setPositiveButton("Voltar", { _,_  -> })

            alertDialog.show()
        }
    }

    override fun onStart() {
        super.onStart()

        media = MediaPlayer.create(context,R.raw.song_menu)
        media!!.isLooping= true
        media!!.start()
    }

    private inner class ActionFabulaButtons : View.OnClickListener {

        override fun onClick(v: View) {

            val fabulasFragment = FabulaFragment()

            when (v.id) {

                R.id.btnFox -> fabulasFragment.initialize("fox", R.raw.son_fox, "A rapoza e as Uvas")
                R.id.btnLeon -> fabulasFragment.initialize("leon", R.raw.son_leon, "O Leão e o Rato")
                R.id.btnMousetrap -> fabulasFragment.initialize("mousetrap", R.raw.song_mousetrap, "O Rato e a Ratoeira")
                R.id.btnTravelers -> fabulasFragment.initialize("travelers", R.raw.song_travelers, "Os Viajantes e o Urso")

            }

            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.my_frame, fabulasFragment,"FRAG_FABULA")
            transaction.commit()


        }
    }

    override fun onStop() {
        super.onStop()
        media!!.stop()
    }


}
