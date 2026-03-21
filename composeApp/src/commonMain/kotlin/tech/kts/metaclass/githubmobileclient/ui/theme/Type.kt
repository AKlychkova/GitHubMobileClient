package tech.kts.metaclass.githubmobileclient.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import githubmobileclient.composeapp.generated.resources.Res
import githubmobileclient.composeapp.generated.resources.mona_sans_vf
import org.jetbrains.compose.resources.Font

@Composable
fun monaSansFontFamily(): FontFamily {
    return FontFamily(
        Font(Res.font.mona_sans_vf)
    )
}

/**
 * Based on https://brand.github.com/foundations/typography
 */
@Composable
fun gitHubTypography(): Typography {

    val monaSans = monaSansFontFamily()

    return Typography(
        displayLarge = TextStyle(
            fontFamily = monaSans,
            fontWeight = FontWeight.Bold,
            fontSize = 57.sp,
            lineHeight = 64.sp,
            letterSpacing = (-0.25).sp
        ),

        displayMedium = TextStyle(
            fontFamily = monaSans,
            fontWeight = FontWeight.Bold,
            fontSize = 45.sp,
            lineHeight = 52.sp
        ),

        displaySmall = TextStyle(
            fontFamily = monaSans,
            fontWeight = FontWeight.SemiBold,
            fontSize = 36.sp,
            lineHeight = 44.sp
        ),

        headlineLarge = TextStyle(
            fontFamily = monaSans,
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp,
            lineHeight = 40.sp
        ),

        headlineMedium = TextStyle(
            fontFamily = monaSans,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            lineHeight = 36.sp
        ),

        headlineSmall = TextStyle(
            fontFamily = monaSans,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 32.sp
        ),

        titleLarge = TextStyle(
            fontFamily = monaSans,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            lineHeight = 28.sp
        ),

        titleMedium = TextStyle(
            fontFamily = monaSans,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp
        ),

        titleSmall = TextStyle(
            fontFamily = monaSans,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),

        bodyLarge = TextStyle(
            fontFamily = monaSans,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),

        bodyMedium = TextStyle(
            fontFamily = monaSans,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp
        ),

        bodySmall = TextStyle(
            fontFamily = monaSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp
        ),

        labelLarge = TextStyle(
            fontFamily = monaSans,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),

        labelMedium = TextStyle(
            fontFamily = monaSans,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp
        ),

        labelSmall = TextStyle(
            fontFamily = monaSans,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp
        )
    )
}