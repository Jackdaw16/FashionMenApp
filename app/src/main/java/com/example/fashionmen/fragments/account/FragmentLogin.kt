package com.example.fashionmen.fragments.account


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.fashionmen.AuthDataSingleton
import com.example.fashionmen.ServicesFragment
import com.example.fashionmen.R
import com.example.fashionmen.activities.Main.MainActivity
import com.example.fashionmen.models.User
import com.example.fashionmen.models.viewmodels.LoggedUser
import com.example.fashionmen.models.viewmodels.LoginViewModel
import com.example.fashionmen.models.viewmodels.RegisterViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback

/**
 * A simple [Fragment] subclass.
 */
class FragmentLogin : ServicesFragment() {
    /*Wigets*/

    /*Login*/
    private lateinit var usernameLogin: TextInputEditText
    private lateinit var passLogin: TextInputEditText
    private lateinit var registerButton: Button
    private lateinit var enterButtom: Button
    private lateinit var loginProgressBar: ProgressBar

    /*Register*/
    private lateinit var panelRegistro: LinearLayout
    private lateinit var tituloLogin: TextView
    private lateinit var tituloSecundario:TextView
    private lateinit var nifField: TextInputEditText
    private lateinit var nameField: TextInputEditText
    private lateinit var lastNameField: TextInputEditText
    private lateinit var emailField: TextInputEditText
    private lateinit var passField: TextInputEditText
    private lateinit var directionField: TextInputEditText
    private lateinit var sendButton: Button
    private lateinit var closeButton: Button
    private lateinit var pushProgres: ProgressBar
    private lateinit var succesfulTask: ImageView

    /*Animation*/
    private lateinit var bottomToUpAnimation: Animation
    private lateinit var upToBottomAnimation: Animation
    private lateinit var fadeIn: Animation
    private lateinit var fadeOut: Animation

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Login */
        tituloLogin = view.findViewById(R.id.tituloLogin)
        usernameLogin = view.findViewById(R.id.username_login)
        passLogin = view.findViewById(R.id.pass_login)
        loginProgressBar = view.findViewById(R.id.login_progress)
        enterButtom = view.findViewById(R.id.enter)
        enterButtom.setOnClickListener {
            if (checkFieldFillLogin())
                signIn()
        }

        /* Registro */
        panelRegistro = view.findViewById(R.id.panelRegistro)
        tituloSecundario = view.findViewById(R.id.tituloSecundario)
        nifField = view.findViewById(R.id.nif_field)
        nameField = view.findViewById(R.id.username_field)
        lastNameField = view.findViewById(R.id.lastname_field)
        emailField = view.findViewById(R.id.email_field)
        passField = view.findViewById(R.id.pass_field)
        directionField = view.findViewById(R.id.direction_field)
        pushProgres = view.findViewById(R.id.push_progress)
        succesfulTask = view.findViewById(R.id.succesful)

        sendButton = view.findViewById(R.id.boton_enviar)
        sendButton.setOnClickListener {
            if (checkFieldFill())
                signUp()
        }

        closeButton = view.findViewById(R.id.boton_cerrar)
        closeButton.setOnClickListener{ onCloseRegisterPanel() }

        registerButton = view.findViewById(R.id.botonRegistrar)
        registerButton.setOnClickListener{ openRegisterPanel() }

        bottomToUpAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_bottom_to_up)
        upToBottomAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_up_to_bottom)
        fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
    }

    private fun openRegisterPanel(){
        panelRegistro.visibility = View.VISIBLE
        panelRegistro.startAnimation(bottomToUpAnimation)

        tituloSecundario.visibility = View.VISIBLE
        tituloSecundario.startAnimation(fadeIn)

        tituloLogin.visibility = View.INVISIBLE
        tituloLogin.startAnimation(fadeOut)
    }

    private fun onCloseRegisterPanel(){
        panelRegistro.startAnimation(upToBottomAnimation)
        panelRegistro.visibility = View.INVISIBLE

        tituloSecundario.startAnimation(fadeOut)
        tituloSecundario.visibility = View.INVISIBLE

        tituloLogin.visibility = View.VISIBLE
        tituloLogin.startAnimation(fadeIn)
    }

    private fun signIn() {

        enterButtom.startAnimation(fadeOut)
        enterButtom.visibility = View.INVISIBLE
        loginProgressBar.visibility = View.VISIBLE
        loginProgressBar.startAnimation(fadeIn)

        val userNameText = usernameLogin.text.toString()
        val passText = passLogin.text.toString()

        val loginCall = userService.login(LoginViewModel(userNameText, passText))

        /*
        * Login request
        *  */
        loginCall.enqueue(object : Callback<LoggedUser> {
            override fun onFailure(call: Call<LoggedUser>?, t: Throwable?) {
                Log.v("retrofit", t!!.message)
            }

            override fun onResponse(call: Call<LoggedUser>,
                                    response: retrofit2.Response<LoggedUser>
            ) {
                if(response.code() == 200) {
                    AuthDataSingleton.loggedUser = response.body()!!
                    Log.d("retrofit", Gson().toJson(response.body()))
                    val fragmentAccount = FragmentAccount()
                    (activity as MainActivity).changeFragment(fragmentAccount, "")
                } else {
                    Log.d("retrofit", Gson().toJson(response.message()))
                    loginProgressBar.startAnimation(fadeOut)
                    loginProgressBar.visibility = View.INVISIBLE
                    enterButtom.startAnimation(fadeIn)
                    enterButtom.visibility = View.VISIBLE
                }
            }
        })
    }


    private fun signUp(){
        sendButton.startAnimation(fadeOut)
        sendButton.visibility = View.INVISIBLE
        pushProgres.visibility = View.VISIBLE
        pushProgres.startAnimation(fadeIn)

        val nifText = nifField.text.toString()
        val userNameText = nameField.text.toString()
        val nameText = lastNameField.text.toString()
        val emailText = emailField.text.toString()
        val passwordText = passField.text.toString()
        val directionText = directionField.text.toString()

        val registerCall = userService.register(RegisterViewModel(
            nifText, userNameText, passwordText, nameText, emailText, directionText
        ))

        /*
        * Register request
        * */
        registerCall.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.v("retrofit1", t!!.message)
            }

            override fun onResponse(call: Call<User>, response: retrofit2.Response<User>) {
                if (response.code() == 201){
                    pushProgres.startAnimation(fadeOut)
                    pushProgres.visibility = View.INVISIBLE
                    succesfulTask.visibility = View.VISIBLE
                    succesfulTask.startAnimation(fadeIn)
                } else {
                    Log.d("retrofit2", Gson().toJson(response.message()))
                }
            }

        })
    }

    private fun checkFieldFill(): Boolean{
        if(nifField.text!!.isEmpty()){
            errorEditText(nifField)

            return false
        } else if (nameField.text!!.isEmpty()) {
            errorEditText(nameField)

            return false
        } else if (emailField.text!!.isEmpty()) {
            errorEditText(emailField)

            return false
        } else if (passField.text!!.isEmpty()) {
            errorEditText(passField)

            return false
        } else if (directionField.text!!.isEmpty()) {
            errorEditText(directionField)

            return false
        }

        return true
    }

    private fun checkFieldFillLogin(): Boolean {
        if (usernameLogin.text!!.isEmpty()){
            errorEditText(usernameLogin)

            return false
        } else if (passLogin.text!!.isEmpty()){
            errorEditText(passLogin)
            return false
        }
        return true
    }

    private fun errorEditText(editText: TextInputEditText) {
        editText.error = getString(R.string.error)
    }
}
