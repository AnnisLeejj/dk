package com.annis.dk.utils

import com.annis.baselib.utils.utils_haoma.SPUtils

class DkSPUtils {

    companion object {
        var spUtils = SPUtils("Config")
        /**
         * key
         */
        fun saveKey(key: String?) {
            spUtils.putString("key", key)
        }

        fun getKey(): String {
            return spUtils.getString("key")
        }

        /**
         * user json
         */
        fun saveWebsite(key: String?) {
            spUtils.putString("website", key)
        }

        fun getWebsite(): String? {
            return spUtils.getString("website")
        }

        /**
         * user json
         */
        fun saveUser(key: String?) {
            spUtils.putString("user", key)
        }

        fun getUser(): String? {
            return spUtils.getString("user")
        }

        /**
         * uid
         */
        fun saveUID(uid: String?) {
            spUtils.putString("uid", uid)
        }

        fun getUID(): String {
            return spUtils.getString("uid")
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