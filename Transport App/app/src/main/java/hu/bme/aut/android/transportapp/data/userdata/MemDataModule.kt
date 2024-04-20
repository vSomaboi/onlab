package hu.bme.aut.android.transportapp.data.userdata

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object InMemDataModule {
    @Provides
    @Singleton
    fun provideMemDataStore() : UserDataStore? {
        var db: UserDataStore? = null
        try{
           db = UserDataStore()
        }catch (e: Exception){
            e.cause?.printStackTrace()
        }
        return db
    }
}
