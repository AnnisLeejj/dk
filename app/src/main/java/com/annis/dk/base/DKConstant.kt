package com.annis.dk.base

import com.annis.dk.bean.UserEntity
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


    }
}