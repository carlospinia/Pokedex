package com.pineapplec.core.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.pineapplec.core.ui.R

/* 
    Created by Carlos Piña on 30/12/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

@Composable
fun Loader() {
    Box(modifier = Modifier.fillMaxSize()) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.diglett_loading))
        LottieAnimation(composition = composition, iterations = LottieConstants.IterateForever)
    }
}
