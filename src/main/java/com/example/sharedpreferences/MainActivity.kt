package com.example.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var nameInput: EditText
    lateinit var emailInput: EditText
    lateinit var saveBtn: Button
    lateinit var listLayout: LinearLayout
    lateinit var prefs: SharedPreferences

    val PREF_NAME = "mypref"
    val KEY_LIST = "user_list"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameInput = findViewById(R.id.editTextName)
        emailInput = findViewById(R.id.editTextEmail)
        saveBtn = findViewById(R.id.btnSave)
        listLayout = findViewById(R.id.listLayout)

        prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        showList()

        saveBtn.setOnClickListener {
            val name = nameInput.text.toString()
            val email = emailInput.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                addUserToPrefs(name, email)
                nameInput.text.clear()
                emailInput.text.clear()
                showList()
            }
        }
    }

    private fun addUserToPrefs(name: String, email: String) {
        val jsonArray = JSONArray(prefs.getString(KEY_LIST, "[]"))
        val userObj = JSONObject()
        userObj.put("name", name)
        userObj.put("email", email)
        jsonArray.put(userObj)
        prefs.edit().putString(KEY_LIST, jsonArray.toString()).apply()
    }

    private fun showList() {
        listLayout.removeAllViews()
        val jsonArray = JSONArray(prefs.getString(KEY_LIST, "[]"))
        for (i in 0 until jsonArray.length()) {
            val user = jsonArray.getJSONObject(i)
            val row = TextView(this)
            row.text = "👤 ${user.getString("name")}  |  📧 ${user.getString("email")}"
            row.setTextColor(resources.getColor(android.R.color.white))
            row.textSize = 16f
            row.setPadding(0, 8, 0, 8)
            listLayout.addView(row)
        }
    }
}
