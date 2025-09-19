package com.example.anemoneapp.ui.customView

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class EditTextUsername : TextInputEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Tidak perlu apa-apa
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Regex: hanya huruf dan angka, 3-20 karakter
                val usernameRegex = "^[A-Za-z0-9]{3,20}$"

                if (!s.toString().matches(usernameRegex.toRegex())) {
                    setError("Username harus 3-20 karakter, hanya huruf & angka", null)
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Tidak perlu apa-apa
            }

        })
    }
}