package com.example.ttlfixer

import java.io.DataOutputStream

object ShellHelper {
    /** Basit "su" sarmalayıcısı. Tüm komutlar başarılıysa true döner. */
    fun runAsRoot(vararg commands: String): Boolean {
        return try {
            val process = Runtime.getRuntime().exec("su")
            val os = DataOutputStream(process.outputStream)
            commands.forEach { cmd ->
                os.writeBytes("$cmd\n")
            }
            os.writeBytes("exit\n")
            os.flush()
            process.waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }
}
