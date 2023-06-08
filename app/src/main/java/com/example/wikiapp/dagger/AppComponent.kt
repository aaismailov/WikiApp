package com.example.wikiapp.dagger

import android.content.Context
import com.example.wikiapp.ui.screens.auth.AuthViewModel
import com.example.wikiapp.ui.screens.favourite.FavouriteViewModel
import com.example.wikiapp.ui.screens.home.HomeViewModel
import com.example.wikiapp.ui.screens.main.MainViewModel
import com.example.wikiapp.ui.screens.newpage.NewPageViewModel
import com.example.wikiapp.ui.screens.profile.ProfileViewModel
import com.example.wikiapp.ui.screens.search.SearchViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModules::class, RepositoriesModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }

    fun inject(authViewModel: AuthViewModel)
    fun inject(homeViewModel: HomeViewModel)
    fun inject(mainViewModel: MainViewModel)
    fun inject(profileViewModel: ProfileViewModel)
    fun inject(newPageViewModel: NewPageViewModel)
    fun inject(favouriteViewModel: FavouriteViewModel)
    fun inject(searchViewModel: SearchViewModel)
}