package com.masiad.arescaperoom

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Single activity architecture
 * Contains {@see #NavHostFragment} as root of navigation
 * https://developer.android.com/guide/navigation/navigation-getting-started
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.main_activity)