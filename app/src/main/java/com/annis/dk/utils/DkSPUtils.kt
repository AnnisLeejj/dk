package com.annis.dk.utils

import com.annis.baselib.utils.utils_haoma.SPUtils

class DkSPUtils {

    companion object {
        var spUtils = SPUtils("Config")
        /**
         * key
         */
        fun saveKey(key: String) {
            spUtils.putString("key", key)
        }

        fun getKey(): String {
            return spUtils.getString("key")
        }

        /**
         * 是否已登录
         */
        fun saveLogin(b: Boolean) {
            spUtils.putBoolean("login", b)
        }

        fun getLogin(): Boolean {
            return spUtils.getBoolean("login", false)
        }
    }
}