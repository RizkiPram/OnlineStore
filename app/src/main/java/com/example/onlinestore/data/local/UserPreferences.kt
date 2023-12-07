package com.example.onlinestore.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserPreferences private constructor(private val dataStore:DataStore<Preferences>){

    fun getUser():Flow<UserModel>{
        return dataStore.data.map {
            UserModel(
                it[ID_KEY] ?: 0,
                it[TOKEN_KEY] ?: "",
                it[STATE_KEY] ?: false
            )
        }
    }
    suspend fun saveUser(user:UserModel){
        dataStore.edit {
            it[ID_KEY] = user.id
            it[TOKEN_KEY] = user.token
            it[STATE_KEY] = user.isLogin
        }
    }
    suspend fun logout() {
        dataStore.edit {
            it[ID_KEY] = 0
            it[TOKEN_KEY] = ""
            it[STATE_KEY] = false
        }
    }
    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null
        private val ID_KEY = intPreferencesKey("id")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}