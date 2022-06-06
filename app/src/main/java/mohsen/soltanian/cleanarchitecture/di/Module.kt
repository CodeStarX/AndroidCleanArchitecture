package mohsen.soltanian.cleanarchitecture.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mohsen.soltanian.cleanarchitecture.app.Application
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.application.AppInitializer
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.application.AppInitializerImpl
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.application.CoreConfig
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.application.TimberInitializer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    fun provideApplication(): Application = Application()


    @Provides
    @Singleton
    fun provideAppConfig(app: Application): CoreConfig = app.appConfig()


    @Provides
    @Singleton
    fun provideTimberInitializer() = TimberInitializer()

    @Provides
    @Singleton
    fun provideAppInitializer(timberManager: TimberInitializer): AppInitializer {
        return AppInitializerImpl(timberManager)
    }
}