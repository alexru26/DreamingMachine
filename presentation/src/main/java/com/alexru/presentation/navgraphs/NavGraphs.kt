package com.alexru.presentation.navgraphs

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootGraph

/**
 * NavGraph for Discover screen
 */
@NavGraph<RootGraph>(start = true)
annotation class DiscoverGraph

/**
 * NavGraph for Library screen
 */
@NavGraph<RootGraph>
annotation class LibraryGraph