package uz.nurlibaydev.enguzbdictionary.data

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.nurlibaydev.enguzbdictionary.data.dao.EngUzDao
import uz.nurlibaydev.enguzbdictionary.data.dao.UzEngDao
import uz.nurlibaydev.enguzbdictionary.data.entity.WordEntity

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