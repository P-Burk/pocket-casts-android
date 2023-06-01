package au.com.shiftyjelly.pocketcasts.di

import android.content.Context
import android.net.ConnectivityManager
import au.com.shiftyjelly.pocketcasts.servers.di.HorologistNetworkAwarenessWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun connectivityManager(@ApplicationContext application: Context): ConnectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @Singleton
    fun provideCallFactoryWrapper(): HorologistNetworkAwarenessWrapper = HorologistNetworkAwarenessWrapper.noopImpl
}
