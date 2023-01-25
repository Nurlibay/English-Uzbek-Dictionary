package uz.nurlibaydev.enguzbdictionary.utils.Extensions

import android.database.Cursor
import uz.nurlibaydev.enguzbdictionary.data.entity.WordEntity

fun Cursor.geWordData(): WordEntity {
    return WordEntity(
        getInt(0),
        getString(1),
        getString(2),
        getString(3),
        getString(4),
        getString(5),
        getInt(6),
        getInt(7)
    )
}