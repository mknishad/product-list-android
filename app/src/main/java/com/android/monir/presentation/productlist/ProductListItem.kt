package com.android.monir.presentation.productlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.android.monir.R
import com.android.monir.domain.model.Product

@Composable
fun ProductListItem(product: Product, modifier: Modifier = Modifier) {
  Card(modifier = modifier) {
    Row(
      modifier = Modifier.padding(16.dp),
      horizontalArrangement = Arrangement.Start,
      verticalAlignment = Alignment.CenterVertically
    ) {
      AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
          .data(product.thumbnail)
          .crossfade(true)
          .build(),
        placeholder = painterResource(R.drawable.ic_launcher_foreground),
        contentDescription = stringResource(R.string.thumbnail),
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .size(50.dp)
          .clip(CircleShape),
      )
      Spacer(modifier = Modifier.width(16.dp))
      Text(
        text = product.title,
        style = MaterialTheme.typography.headlineSmall,
        overflow = TextOverflow.Ellipsis
      )
    }
  }
}