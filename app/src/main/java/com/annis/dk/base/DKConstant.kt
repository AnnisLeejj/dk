package com.annis.dk.base

import com.annis.dk.bean.UserEntity
import com.annis.dk.bean.WebSite
import com.annis.dk.utils.DkSPUtils
import com.google.gson.Gson

class DKConstant {
    companion object {
        fun clear() {
            DkSPUtils.saveLogin(false)
            DkSPUtils.saveUser(null)
            DkSPUtils.saveUID(null)
        }

        fun saveUserEntity(user: UserEntity) {
            DkSPUtils.saveLogin(true)
            DkSPUtils.saveUser(Gson().toJson(user))
            DkSPUtils.saveUID(user.id)
        }

        fun getUserEntity(): UserEntity {
            var userJson = DkSPUtils.getUser()
            userJson ?: let {
                return@let null
            }
            return Gson().fromJson(userJson, UserEntity::class.java)
        }

        fun saveWebsite(webSite: WebSite) {
            DkSPUtils.saveWebsite(Gson().toJson(webSite))
        }

        fun getWebsite(): WebSite {
            var webSiteJson = DkSPUtils.getWebsite()
            webSiteJson ?: let {
                return@let null
            }
            return Gson().fromJson(webSiteJson, WebSite::class.java)
        }

    }
}