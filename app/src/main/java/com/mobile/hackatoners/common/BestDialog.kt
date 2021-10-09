package com.mobile.hackatoners.common

import android.content.Context
import android.content.res.Resources
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.mobile.hackatoners.R
import kotlinx.android.synthetic.main.dialog_blank.view.*

class DialogPreset(context: Context, data: DialogData) {

    private val dialog: AlertDialog = AlertDialog.Builder(context)
        .setView(R.layout.dialog_blank)
        .also { builder ->
            val view = View.inflate(context, R.layout.dialog_blank, null)
            view.applyData(data)
            builder.setView(view)
        }
        .create()

    fun dismiss() {
        dialog.dismiss()
    }

    fun show() {
        dialog.show()
    }


    private fun View.applyData(data: DialogData) {
        dialog_title.text = data.title
        dialog_message.text = Html.fromHtml(data.message.toString())
        dialog_message.movementMethod = LinkMovementMethod.getInstance()
        data.mainBtn?.let { (caption, action) ->
            data.positiveBtnColor?.let {
                dialog_main_btn.setTextColor(data.positiveBtnColor)
            }

            dialog_main_btn.text = caption
            dialog_main_btn.setOnClickListener {
                action()
                dismiss()
            }
            dialog_main_btn.show()
        }
        data.scndBtn?.let { (caption, action) ->
            dialog_second_btn.text = caption
            dialog_second_btn.setOnClickListener {
                action()
                dismiss()
            }
            dialog_second_btn.show()
        }
        val c = data.customization
        this.c()
    }

}

data class DialogData(
    val title: CharSequence,
    val message: CharSequence,
    val mainBtn: CaptionAction? = null,
    val scndBtn: CaptionAction? = null,
    val customization: View.() -> Unit = {},
    val positiveBtnColor: Int?
)

data class CaptionAction(
    val title: String,
    val action: Action
)

private typealias Action = DialogPreset.() -> Unit

private class DialogPresetEmitter(res: Resources) : DialogPresetBuilder(res) {

    fun emit(): DialogData =
        DialogData(
            title,
            body,
            positive,
            negative,
            customization,
            positiveBtnColor
        )

}

abstract class DialogPresetBuilder(private val resources: Resources) {

    protected lateinit var title: CharSequence
    protected lateinit var body: CharSequence
    protected var positive: CaptionAction? = null
    protected var negative: CaptionAction? = null
    protected var customization: View.() -> Unit = {}
    protected var positiveBtnColor: Int? = null

    fun title(title: String) {
        this.title = title
    }

    fun title(@StringRes title: Int) {
        this.title = resources.getString(title)
    }

    fun body(body: CharSequence) {
        this.body = body
    }

    fun body(@StringRes body: Int) {
        this.body = resources.getString(body)
    }

    fun positive(title: String, onClick: Action) {
        this.positive = CaptionAction(title, onClick)
    }

    fun positive(@StringRes title: Int, onClick: Action) {
        this.positive = CaptionAction(resources.getString(title), onClick)
    }

    fun negative(title: String, onClick: Action) {
        this.negative = CaptionAction(title, onClick)
    }

    fun negative(@StringRes title: Int, onClick: Action) {
        this.negative = CaptionAction(resources.getString(title), onClick)
    }

    fun customizeDialog(customization: View.() -> Unit) {
        this.customization = customization
    }

    fun positiveBtnColor(color : Int) {
        this.positiveBtnColor = color
    }

}

fun makeDialog(context: Context, builderF: DialogPresetBuilder.() -> Unit): DialogPreset {
    val builder = DialogPresetEmitter(context.resources)
    builder.builderF()
    return DialogPreset(context, builder.emit())
}
