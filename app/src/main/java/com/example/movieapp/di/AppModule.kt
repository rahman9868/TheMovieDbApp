package com.example.movieapp.di

import com.example.core.domain.usecase.MovieInteractor
import com.example.core.domain.usecase.MovieUseCase
import com.example.movieapp.detail.DetailMovieViewModel
import com.example.movieapp.home.HomeViewModel
import com.example.movieapp.search.SearchViewModel
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}