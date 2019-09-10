package com.gustavogoma.android.darkmodetest

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var darkModeSpinnerLabelView: TextView
    private lateinit var darkModeSpinnerView: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        darkModeSpinnerLabelView = findViewById(R.id.dark_mode_spinner_label_view)
        darkModeSpinnerView = findViewById(R.id.dark_mode_spinner_view)

        android_version_text_view.text = "Android ${Build.VERSION.RELEASE}"

        setupDarkModeOptions()
    }

    private fun setupDarkModeOptions() {
        if (!Settings.isDarkModeAvailable) {
            darkModeSpinnerView.visibility = View.GONE
            darkModeSpinnerLabelView.visibility = View.GONE

            return
        }

        val darkModesAvailable = Settings.getAvailableDarkModes()
        val adapter = SimpleSpinnerAdapter(this, R.layout.holder_string_spinner)
            .apply {
                this.data = darkModesAvailable
            }

        val clickListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // do nothing
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val darkMode = adapter.data[position]
                updateDarkMode(darkMode)
            }
        }

        val currentDarkMode = Settings.getDarkModePreference(this)
        val selectedItem = darkModesAvailable.indexOf(currentDarkMode)

        darkModeSpinnerView.adapter = adapter
        darkModeSpinnerView.setSelection(selectedItem)
        darkModeSpinnerView.onItemSelectedListener = clickListener
    }

    private fun updateDarkMode(darkMode: Settings.DarkMode) {
        Settings.setDarkModePreference(this, darkMode)
    }

    class SimpleSpinnerAdapter(
        private val context: Context,
        @LayoutRes val layout: Int
    ) : BaseAdapter(), SpinnerAdapter {

        var data = listOf<Settings.DarkMode>()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: View.inflate(context, layout, null)

            view.findViewById<TextView>(R.id.text_view).apply {
                this.text = context.getString(getItem(position).text)
            }

            return view
        }

        override fun getItem(position: Int): Settings.DarkMode {
            return data[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return data.count()
        }
    }
}
