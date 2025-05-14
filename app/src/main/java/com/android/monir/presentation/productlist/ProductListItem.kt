package com.android.monir.presentation.productlist

import android.R.attr.contentDescription
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.android.monir.R
import com.android.monir.domain.model.Product

@Composable
fun ProductListItem(product: Product, modifier: Modifier = Modifier) {
  Card(modifier = modifier.fillMaxWidth()) {
    Column {
      Box(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
          model = ImageRequest.Builder(LocalContext.current)
            .data(product.thumbnail)
            .crossfade(true)
            .build(),
          placeholder = painterResource(R.drawable.ic_launcher_foreground),
          error = painterResource(R.drawable.ic_launcher_foreground),
          contentDescription = stringResource(R.string.thumbnail),
          modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        )
        Card(
          modifier = Modifier.align(Alignment.TopEnd),
          shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomStart = 8.dp,
            bottomEnd = 0.dp
          ),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
        ) {
          Text(
            text = product.category,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }
      Spacer(modifier = Modifier.width(16.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = product.title,
          style = MaterialTheme.typography.titleMedium,
          overflow = TextOverflow.Ellipsis
        )
      }
      Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "$%.2f".format(product.price - (product.price * product.discountPercentage / 100)),
          style = MaterialTheme.typography.bodyLarge,
          overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.width(16.dp))
        if (product.discountPercentage > 0) {
          Text(
            text = "$%.2f".format(product.price),
            style = TextStyle(textDecoration = TextDecoration.LineThrough, fontSize = 14.sp),
            overflow = TextOverflow.Ellipsis
          )
        }
      }
      Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = stringResource(R.string.rating),
            tint = Color.Yellow,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = product.rating.toString(),
            style = MaterialTheme.typography.bodyMedium,
          )
        }
      }
      Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        if (product.stock <= 5) {
          Text(
            text = "Only 3 left!",
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis
          )
        }
      }
    }
  }
}

@Preview
@Composable
private fun ProductListItemPreview() {
  ProductListItem(
    product = Product(
      id = 1,
      title = "Product title",
      category = "Product description",
      price = 100.0,
      discountPercentage = 10.0,
      rating = 4.5,
      stock = 100,
      availabilityStatus = "In Stock",
      thumbnail = ""
    )
  )
}
