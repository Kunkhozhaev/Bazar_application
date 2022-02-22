package com.example.bazar.core

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.bazar.App.Companion.getAppInstance
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, text, duration).show()

fun Fragment.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    if (context != null) {
        context!!.toast(text, duration)
    }
}

inline fun <T : View> T.onClick(crossinline func: T.() -> Unit) = setOnClickListener { func() }

inline fun <T : Toolbar> T.navOnClick(crossinline func: T.() -> Unit) =
    setNavigationOnClickListener { func() }

fun TextInputEditText.textToString() = this.text.toString()
fun TextView.textToString() = this.text.toString()

fun View.showSoftKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

fun View.hideSoftKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun TextInputEditText.checkIsEmpty(): Boolean = text == null ||
        textToString() == "" ||
        textToString().equals("null", ignoreCase = true)

fun AutoCompleteTextView.checkIsEmpty(): Boolean = text == null ||
        textToString() == "" ||
        textToString().equals("null", ignoreCase = true)

fun TextInputEditText.showError(error: String): Boolean {
    this.error = error
    this.showSoftKeyboard()
    return false
}
fun TextInputLayout.showError(error: String): Boolean {
    this.error = error
    this.showSoftKeyboard()
    return false
}
fun AutoCompleteTextView.showError(error: String): Boolean {
    this.error = error
    this.showSoftKeyboard()
    return false
}

fun ViewGroup.inflate(@LayoutRes id: Int): View =
    LayoutInflater.from(context).inflate(id, this, false)

fun RecyclerView.addVertDivider(context: Context?) {
    this.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
}

fun RecyclerView.addHorizDivider(context: Context?) {
    this.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
}

fun isNetworkAvailable(): Boolean {
    val info = getAppInstance().getConnectivityManager().activeNetworkInfo
    return info != null && info.isConnected
}

fun Context.getConnectivityManager() =
    getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Fragment.showProgress() {
    (requireActivity() as AppBaseActivity).showProgress(true)
}

fun Fragment.hideProgress() {
    (requireActivity() as AppBaseActivity).showProgress(false)
}

fun DialogFragment.showProgress() {
    (requireContext() as AppBaseActivity).showProgress(true)
}

fun DialogFragment.hideProgress() {
    (requireContext() as AppBaseActivity).showProgress(false)
}

fun Fragment.launchBrowser(link: String) {
    try {
        val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(myIntent)
    } catch (e: ActivityNotFoundException) {
        toast("Ни одно приложение не может обработать этот запрос \nПожалуйста, установите веб-браузер")
        e.printStackTrace()
    }
}