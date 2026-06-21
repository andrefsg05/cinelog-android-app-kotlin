package com.example.sma.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sma.ui.theme.StarYellow
import com.example.sma.ui.theme.TextGrey

/**
 * Componente de rating com estrelas interativo.
 */
@Composable
fun RatingBar(
    rating: Float,
    maxStars: Int = 5,
    onRatingChanged: ((Float) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        for (i in 1..maxStars) {
            val icon = when {
                i <= rating -> Icons.Filled.Star
                i - 0.5f <= rating -> Icons.AutoMirrored.Filled.StarHalf
                else -> Icons.Outlined.StarOutline
            }

            val tint = if (i <= rating || i - 0.5f <= rating) {
                StarYellow
            } else {
                TextGrey
            }

            val starModifier = if (onRatingChanged != null) {
                Modifier
                    .size(24.dp)
                    .clickable { onRatingChanged(i.toFloat()) }
            } else {
                Modifier.size(24.dp)
            }

            Icon(
                imageVector = icon,
                contentDescription = "Estrela $i",
                tint = tint,
                modifier = starModifier
            )
        }
    }
}

/**
 * Preview do RatingBar.
 */
@Preview(showBackground = true, backgroundColor = 0xFF14181C)
@Composable
fun RatingBarPreview() {
    RatingBar(rating = 3.5f)
}
