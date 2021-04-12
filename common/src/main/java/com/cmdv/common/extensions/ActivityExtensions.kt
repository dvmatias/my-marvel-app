package com.cmdv.common.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityCompat

inline fun <reified A : Activity> Activity.navigateTo(data: Bundle?, finish: Boolean) {
    Intent(this, A::class.java).also { i ->
        data?.let { b -> i.putExtras(b) }
        overridePendingTransition(0, 0)
        ActivityCompat.startActivity(this, i, null)
        if (finish) this.finish()
    }
}