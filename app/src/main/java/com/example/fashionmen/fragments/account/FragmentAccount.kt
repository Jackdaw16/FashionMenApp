package com.example.fashionmen.fragments.account

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.fashionmen.AppConfiguration
import com.example.fashionmen.AuthDataSingleton
import com.example.fashionmen.models.User

import com.example.fashionmen.R
import com.example.fashionmen.dialog.DialogAboutUs
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import org.json.JSONObject

class FragmentAccount : Fragment() {
    private lateinit var mainLayout: LinearLayout
    private lateinit var settingLayout: LinearLayout
    private lateinit var changeInfoUserUI: LinearLayout

    /*Profile things*/
    private lateinit var nombreUsuario: TextView
    private lateinit var btnSetting: Button
    private lateinit var btnAboutUs: Button
    private lateinit var btnOut: Button

    /*Settings buttons*/
    private lateinit var btnChangeUserName: Button
    private lateinit var btnChangeUserPass: Button
    private lateinit var btnChangeUserDirection: Button
    private lateinit var btnClose: Button

    /*Dinamic UI change user information*/
    private lateinit var layoutOldField: TextInputLayout
    private lateinit var oldFiled: TextInputEditText
    private lateinit var newField: TextInputEditText
    private lateinit var btnSudmit: Button
    private lateinit var btnCancel: Button

    /*Data utils*/
    private lateinit var user: User
    private lateinit var configuration: AppConfiguration

    private var param: String = String()

    private lateinit var fadeIn: Animation
    private lateinit var fadeOut: Animation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cuenta, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configuration = AppConfiguration()
        obtenerCargarUsuario(context!!)

        mainLayout = view.findViewById(R.id.main_layout)
        settingLayout = view.findViewById(R.id.layout_settings)
        changeInfoUserUI = view.findViewById(R.id.dinamicUI)

        nombreUsuario = view.findViewById(R.id.nombre_usuario_profile)
        nombreUsuario.text = user.nombreCompleto

        mainOptionAssign(view)
        settingsOptionAssign(view)

        layoutOldField = view.findViewById(R.id.layoutOldField)
        oldFiled = view.findViewById(R.id.oldField)
        newField = view.findViewById(R.id.newField)
        btnSudmit = view.findViewById(R.id.sudmit)
        btnSudmit.setOnClickListener { validateInformation(param) }
        btnCancel = view.findViewById(R.id.cancel)
        btnCancel.setOnClickListener { showAndHiddeSettingsOptions() }

        fadeIn = AnimationUtils.loadAnimation(context!!, R.anim.fade_in)
        fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
    }

    private fun obtenerCargarUsuario(context: Context) {
        user = AuthDataSingleton.loggedUser.user
    }

    private fun showAndHiddeSettings(){
        if (settingLayout.visibility == View.INVISIBLE){
            mainLayout.visibility = View.INVISIBLE
            mainLayout.startAnimation(fadeOut)

            settingLayout.visibility = View.VISIBLE
            settingLayout.startAnimation(fadeIn)

        } else if (settingLayout.visibility == View.VISIBLE) {
            settingLayout.visibility = View.INVISIBLE
            settingLayout.startAnimation(fadeOut)

            mainLayout.visibility = View.VISIBLE
            mainLayout.startAnimation(fadeIn)
        }
    }

    private fun showAndHiddeSettingsOptions(){
        if (settingLayout.visibility == View.INVISIBLE){
            changeInfoUserUI.visibility = View.INVISIBLE
            changeInfoUserUI.startAnimation(fadeOut)

            oldFiled.text!!.clear()
            newField.text!!.clear()

            settingLayout.visibility = View.VISIBLE
            settingLayout.startAnimation(fadeIn)

        } else if (settingLayout.visibility == View.VISIBLE) {
            settingLayout.visibility = View.INVISIBLE
            settingLayout.startAnimation(fadeOut)

            changeInfoUserUI.visibility = View.VISIBLE
            changeInfoUserUI.startAnimation(fadeIn)
        }
    }

    private fun fillFields(titleOldField: String, titleNewField: String, isHidden: Boolean, isPass: Boolean){
        if (isHidden){
            param = "direccion"
            layoutOldField.visibility = View.GONE
            newField.hint = titleNewField
        } else {
            layoutOldField.visibility = View.VISIBLE
            oldFiled.hint = titleOldField
            newField.hint = titleNewField

            if (isPass){
                oldFiled.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                newField.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                param = "passwd"
            } else {
                oldFiled.inputType = InputType.TYPE_CLASS_TEXT
                newField.inputType = InputType.TYPE_CLASS_TEXT
                param = "nombre_usuario"
            }

        }
    }

    private fun mainOptionAssign(view: View){
        btnSetting = view.findViewById(R.id.btn_settings)
        btnSetting.setOnClickListener { showAndHiddeSettings() }

        btnAboutUs = view.findViewById(R.id.btn_aboutUs)
        btnAboutUs.setOnClickListener {
            val dialogoSobreNosotros = DialogAboutUs()
            dialogoSobreNosotros.show(fragmentManager!!, null)
        }

        btnClose = view.findViewById(R.id.btn_out)
        btnClose.setOnClickListener { getOutProfile() }
    }

    private fun settingsOptionAssign(view: View){
        btnChangeUserName = view.findViewById(R.id.btn_change_username)
        btnChangeUserName.setOnClickListener {
            fillFields(getString(R.string.opcionCambiarNombre1),
                getString(R.string.opcionCambiarNombre2), false, false)
            showAndHiddeSettingsOptions()
        }

        btnChangeUserPass = view.findViewById(R.id.btn_change_pass)
        btnChangeUserPass.setOnClickListener {
            fillFields(getString(R.string.opcionCambiarPass1),
                getString(R.string.opcionCambiarPass2), false, true)
            showAndHiddeSettingsOptions()
        }

        btnChangeUserDirection = view.findViewById(R.id.btn_change_direction)
        btnChangeUserDirection.setOnClickListener {
            fillFields( "",getString(R.string.opcionCambiarDireccion), true, false)
            showAndHiddeSettingsOptions()
        }

        btnClose = view.findViewById(R.id.btn_back)
        btnClose.setOnClickListener { showAndHiddeSettings() }
    }

    private fun validateInformation(param: String){
        /*if (param.equals(constantes.sixthParamUser)){
            usuarioJson.remove(constantes.sixthParamUser)
            usuarioJson.put(constantes.sixthParamUser, newField.text.toString())
            updateRequest(usuarioJson)

        } else if (param.equals(constantes.secondParamUser)) {
            if (oldFiled.text!!.toString().equals(user.nombre_usuario)){
                usuarioJson.remove(constantes.secondParamUser)
                usuarioJson.put(constantes.secondParamUser, newField.text.toString())
                updateRequest(usuarioJson)
            }else{
                return
            }

        } else if (param.equals(constantes.thirdParamUser)) {
            if (oldFiled.text!!.toString().equals(user.passwd)){
                Log.d("Seguimiento", "Entro aqui")
                usuarioJson.remove(constantes.thirdParamUser)
                usuarioJson.put(constantes.thirdParamUser, newField.text.toString())
                updateRequest(usuarioJson)
            }else{
                return
            }
        }*/
    }

    private fun updateRequest(usuarioUpdated: JSONObject){
        val requestQueue = Volley.newRequestQueue(context)
        val dniId = user.id
        val url = "http://192.168.220.193/app_fashionmen_sw/public/usuarios/$dniId"

        val putRequest = JsonObjectRequest(Request.Method.PUT, url, usuarioUpdated,
            Response.Listener {response ->
                configuration.guardarUsuario(response.toString(), context!!)
                obtenerCargarUsuario(context!!)

                oldFiled.text!!.clear()
                newField.text!!.clear()
            },
            Response.ErrorListener {
                Log.d("ERROR", "No funciono")
            })
        requestQueue.add(putRequest)
    }

    private fun getOutProfile(){
        val fragmentManager = fragmentManager!!.beginTransaction()
        val loginFragment = FragmentLogin()

        fragmentManager.replace(R.id.nav_host_fragment, loginFragment)
        fragmentManager.addToBackStack(null)
        fragmentManager.commit()
        configuration.borrarDatosUsuario(context!!)
    }
}
