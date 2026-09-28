package com.evandromqs.neumonote.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.evandromqs.neumonote.R
import com.evandromqs.neumonote.ui.main.MainActivity
import com.evandromqs.neumonote.utils.CryptoManager

class LoginActivity : Activity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)

		val rootLayout = findViewById<View>(R.id.rootLayout)
		val passwordInput = findViewById<EditText>(R.id.etSenha)
		val enterButton = findViewById<Button>(R.id.btnEntrar)
		val errorMessage = findViewById<TextView>(R.id.tvMensagemErro)
		val cryptoManager = CryptoManager(this)
		val firstLaunch = !cryptoManager.verificarSenhaExiste()

		if (firstLaunch) {
			passwordInput.hint = getString(R.string.login_create_password_hint)
			enterButton.text = getString(R.string.login_create_button)
		}

		enterButton.setOnClickListener {
			val password = passwordInput.text.toString()
			if (password.isBlank()) {
				errorMessage.text = getString(R.string.login_empty_password)
				errorMessage.visibility = View.VISIBLE
				return@setOnClickListener
			}

			val authenticated = if (firstLaunch) {
				cryptoManager.salvarSenha(password)
				true
			} else {
				cryptoManager.verificarSenha(password)
			}

			if (authenticated) {
				startActivity(Intent(this, MainActivity::class.java))
				finish()
			} else {
				errorMessage.text = getString(R.string.login_incorrect_password)
				errorMessage.visibility = View.VISIBLE
			}
		}

		rootLayout.alpha = 0f
		rootLayout.animate()
			.alpha(1f)
			.setDuration(350L)
			.start()
	}
}