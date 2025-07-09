package com.example.ttlfixer

import android.content.Context
import android.content.SharedPreferences

class TtlManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("ttl_prefs", Context.MODE_PRIVATE)

    /** Kaydedilen TTL değeri (varsayılan 64) */
    var ttl: Int
        get() = prefs.getInt("ttl_value", 64)
        set(value) = prefs.edit().putInt("ttl_value", value).apply()

    /** Kaydedilen değeri iptables/ip6tables ile uygular. */
    fun applyTtl(): Boolean {
        val clearV4 = "iptables -t mangle -F"
        val clearV6 = "ip6tables -t mangle -F"
        val setV4 = "iptables -t mangle -I POSTROUTING -o rmnet_data+ -j TTL --ttl-set $ttl"
        val setV6 = "ip6tables -t mangle -I POSTROUTING -o rmnet_data+ -j HL --hl-set $ttl"
        return ShellHelper.runAsRoot(clearV4, setV4, clearV6, setV6)
    }
}
