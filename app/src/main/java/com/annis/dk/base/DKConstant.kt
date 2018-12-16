package com.annis.dk.base

import com.annis.dk.bean.BankInfo
import com.annis.dk.bean.LoanInfo
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
            DkSPUtils.saveLoan(null)
        }

        /*****用户信息*****/
        fun saveUserEntity(user: UserEntity) {
            DkSPUtils.saveLogin(true)
            DkSPUtils.saveUser(Gson().toJson(user))
            DkSPUtils.saveUID(user.id)
        }

        fun getUserEntity(): UserEntity? {
            var userJson = DkSPUtils.getUser()
            userJson ?: let {
                return null
            }
            return Gson().fromJson(userJson, UserEntity::class.java)
        }

        /*****网站信息*****/
        fun saveWebsite(webSite: WebSite) {
            DkSPUtils.saveWebsite(Gson().toJson(webSite))
        }

        fun getWebsite(): WebSite? {
            var webSiteJson = DkSPUtils.getWebsite()
            webSiteJson ?: let {
                return null
            }
            return Gson().fromJson(webSiteJson, WebSite::class.java)
        }

        /*****贷款信息*****/
        fun saveLoan(loanInfo: LoanInfo) {
            DkSPUtils.saveLoan(Gson().toJson(loanInfo))
        }

        fun getLoan(): LoanInfo? {
            var webSiteJson = DkSPUtils.getLoan()
            webSiteJson ?: let {
                return null
            }
            return Gson().fromJson(webSiteJson, LoanInfo::class.java)
        }

        /*****银行卡信息*****/
        fun saveBankCard(it: BankInfo) {
            DkSPUtils.saveBankCard(Gson().toJson(it))
        }

        fun getBankCard(): BankInfo? {
            var bankJson = DkSPUtils.getBankCard()
            bankJson ?: let {
                return null
            }
            return Gson().fromJson(bankJson, BankInfo::class.java)
        }
    }
}