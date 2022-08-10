package uz.unidev.dictionary.data

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.unidev.dictionary.data.dao.EngUzDao
import uz.unidev.dictionary.data.dao.UzEngDao
import uz.unidev.dictionary.data.entity.WordEntity

/**
 *  Created by Nurlibay Koshkinbaev on 30/07/2022 23:53
 */

@Database(
    entities = [WordEntity::class],
    version = 4,
    exportSchema = false
)
abstract class DictionaryDatabase : RoomDatabase() {

    abstract fun engUzDao(): EngUzDao
    abstract fun uzEngDao(): UzEngDao

    /**
    companion object {

        @Volatile
        private var INSTANCE: DictionaryDatabase? = null

        fun getInstance(context: Context): DictionaryDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    DictionaryDatabase::class.java,
                    "dictionary_db"
                )
                    .createFromAsset("databases/dictionary1.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
    }
     */
}