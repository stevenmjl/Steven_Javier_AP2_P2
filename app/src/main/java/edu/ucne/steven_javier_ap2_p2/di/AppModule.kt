package edu.ucne.steven_javier_ap2_p2.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.steven_javier_ap2_p2.data.remote.JugadorApi
import edu.ucne.steven_javier_ap2_p2.data.repository.JugadorRepositoryImpl
import edu.ucne.steven_javier_ap2_p2.domain.repository.JugadorRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(moshi: Moshi): JugadorApi {
        return Retrofit.Builder()
            .baseUrl("https://gestionhuacalesapi.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(JugadorApi::class.java)
    }

    @Provides
    @Singleton
    fun provideJugadorRepository(api: JugadorApi): JugadorRepository {
        return JugadorRepositoryImpl(api)
    }
}