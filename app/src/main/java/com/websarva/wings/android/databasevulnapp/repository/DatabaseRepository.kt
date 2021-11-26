package com.websarva.wings.android.databasevulnapp.repository

import android.content.Context
import com.websarva.wings.android.databasevulnapp.model.ColumnName
import com.websarva.wings.android.databasevulnapp.model.Database
import javax.inject.Inject

interface DatabaseRepository {
    suspend fun create(context: Context, database: Database)
    suspend fun read(context: Context): Database
}

class DatabaseRepositoryClient @Inject constructor(): DatabaseRepository {
    override suspend fun create(context: Context, database: Database) {
        // databaseの作成とデータの追加
        context.openOrCreateDatabase("database", Context.MODE_PRIVATE, null).use {
            it.execSQL("CREATE TABLE IF NOT EXISTS Account(${ColumnName._id.name} INTEGER, ${ColumnName.Username.name} VARCHAR, ${ColumnName.Password.name} VARCHAR);")
            it.execSQL("INSERT INTO Account VALUES ('${database.id}', '${database.name}', '${database.password}');")
        }
    }

    override suspend fun read(context: Context): Database {
        var username = ""
        var password = ""

        context.openOrCreateDatabase("database", Context.MODE_PRIVATE, null).use {
            // databaseからデータを取得
            val data = it.rawQuery("SELECT * FROM Account;", null)
            data.moveToFirst()
            // idxを取得
            val idxUsername = data.getColumnIndex(ColumnName.Username.name)
            val idxPassword = data.getColumnIndex(ColumnName.Password.name)
            // idxからデータを取得
            username = data.getString(idxUsername)
            password = data.getString(idxPassword)
        }

        return Database(name = username, password = password)
    }
}