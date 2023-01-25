package uz.nurlibaydev.enguzbdictionary.di

import android.content.Context
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import uz.nurlibaydev.enguzbdictionary.data.DictionaryDatabase
import uz.nurlibaydev.enguzbdictionary.data.dao.EngUzDao
import uz.nurlibaydev.enguzbdictionary.data.dao.UzEngDao
import uz.nurlibaydev.enguzbdictionary.domain.repository.EngRepository
import uz.nurlibaydev.enguzbdictionary.domain.repository.UzRepository
import uz.nurlibaydev.enguzbdictionary.domain.repository.impl.EngRepositoryImpl
import uz.nurlibaydev.enguzbdictionary.domain.repository.impl.UzRepositoryImpl
import uz.nurlibaydev.enguzbdictionary.ui.eng_uz.bookmark.BookmarkAdapter
import uz.nurlibaydev.enguzbdictionary.ui.eng_uz.bookmark.BookmarkViewModel
import uz.nurlibaydev.enguzbdictionary.ui.eng_uz.definition.DefinitionViewModel
import uz.nurlibaydev.enguzbdictionary.ui.eng_uz.main.EngMainViewModel
import uz.nurlibaydev.enguzbdictionary.ui.eng_uz.main.EngWordAdapter
import uz.nurlibaydev.enguzbdictionary.ui.uzb_eng.bookmark.UzBookmarkAdapter
import uz.nurlibaydev.enguzbdictionary.ui.uzb_eng.bookmark.UzBookmarkViewModel
import uz.nurlibaydev.enguzbdictionary.ui.uzb_eng.definition.UzDefinitionViewModel
import uz.nurlibaydev.enguzbdictionary.ui.uzb_eng.main.UzViewModel
import uz.nurlibaydev.enguzbdictionary.ui.uzb_eng.main.UzWordAdapter

val roomModule = module {

    fun provideRoomDatabase(context: Context): DictionaryDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            DictionaryDatabase::class.java,
            "dictionary1.db"
        )
            .createFromAsset("dictionary1.db")
            .build()
    }

    fun provideEngDao(database: DictionaryDatabase): EngUzDao {
        return database.engUzDao()
    }

    fun provideUzDao(database: DictionaryDatabase): UzEngDao {
        return database.uzEngDao()
    }

    single { provideRoomDatabase(androidApplication()) }
    single { provideEngDao(get()) }
    single { provideUzDao(get()) }
}

val viewModelModule = module {

    /**
     * Old Version
     * */
    viewModel { EngMainViewModel(get()) }
    viewModel { UzViewModel(get()) }

    /**
     * New Version
     * viewModelOf(::MainViewModel)
     * */

    viewModel { DefinitionViewModel(get()) }
    viewModel { BookmarkViewModel(get()) }

    viewModel { UzBookmarkViewModel(get()) }
    viewModel { UzDefinitionViewModel(get()) }
}

val repositoryModule = module {
    single<EngRepository> { EngRepositoryImpl(dao = get()) }
    single<UzRepository> { UzRepositoryImpl(uzDao = get()) }
}

val adapterModule = module {
    single { EngWordAdapter() }
    single { UzWordAdapter() }
    single { BookmarkAdapter() }
    single { UzBookmarkAdapter() }
}