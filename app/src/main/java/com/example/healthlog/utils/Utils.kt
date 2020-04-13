package com.example.healthlog.utils

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import com.example.healthlog.database.entitiy.ExerciseItem
import java.util.regex.Pattern


object Utils {
    fun keyboardHide(context: Context, editText: EditText) {
        val imm: InputMethodManager? = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(editText.windowToken, 0);
    }

    fun makeExerciseItemToSearchName(item: ExerciseItem) = "${item.name}(${item.part})"

    fun makeSearchNameToExerciseItem(name: String): ExerciseItem {
        val splits = name.replace(")","").split("(")
        return ExerciseItem(splits[0], splits[1])
    }

    fun checkExerciseSearchName(name: String): Boolean {
        val p = Pattern.compile("^[가-힣][가-힣]*\\([가-힣][가-힣]*\\)");
        return p.matcher(name).find()
    }
}