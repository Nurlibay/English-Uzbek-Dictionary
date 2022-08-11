package uz.unidev.dictionary.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import uz.unidev.dictionary.data.entity.WordEntity

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<WordEntity>)

    /**
     * vararg - ... in java
     * this case you send insert function multiple arguments
     * that type is Dictionary Entity
     * */

    @Insert
    suspend fun insert(vararg words: WordEntity)

    @Update
    suspend fun update(data: WordEntity)

    @Update
    suspend fun update(vararg data: T)

    @Update
    suspend fun update(data: List<T>)

    @Delete
    suspend fun delete(data: T)

    @Delete
    suspend fun delete(data: List<T>)

    @Delete
    suspend fun delete(vararg data: T)
}