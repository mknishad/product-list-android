package com.android.monir.presentation.productlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.monir.R
import com.android.monir.domain.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
  modifier: Modifier = Modifier, viewModel: ProductListViewModel = hiltViewModel()
) {
  val productPagingItems: LazyPagingItems<Product> =
    viewModel.productListState.collectAsLazyPagingItems()

  Scaffold(
    topBar = {
      TopAppBar(title = { Text(text = stringResource(R.string.app_name)) })
    }, modifier = modifier.fillMaxSize()
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues), contentAlignment = Alignment.Center
    ) {
      PullToRefreshBox(
        isRefreshing = productPagingItems.loadState.refresh is LoadState.Loading,
        onRefresh = { viewModel.onEvent(ProductListEvent.Refresh) }
      ) {
        Column {
          LazyColumn(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
          ) {
            items(productPagingItems.itemCount) { index ->
              val productItem = productPagingItems[index]
              if (productItem != null) {
                ProductListItem(
                  product = productItem,
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                )
              }
            }

            item {
              if (productPagingItems.loadState.append is LoadState.Loading) {
                Box(modifier = Modifier.padding(16.dp)) {
                  CircularProgressIndicator()
                }
              }
            }
          }
        }
      }
      if (productPagingItems.loadState.refresh is LoadState.Error) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .align(Alignment.Center),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = (productPagingItems.loadState.refresh as LoadState.Error).error.localizedMessage
              ?: "An unexpected error occurs!",
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
          )
          Spacer(modifier = Modifier.height(16.dp))
          OutlinedButton(onClick = { viewModel.onEvent(ProductListEvent.Refresh) }) {
            Text("Retry")
          }
        }
      }
      if (productPagingItems.loadState.refresh is LoadState.Loading) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
      }
      if (productPagingItems.itemCount == 0
        && productPagingItems.loadState.refresh !is LoadState.Loading
        && productPagingItems.loadState.refresh !is LoadState.Error
      ) {
        Text(
          text = "No product found!",
          textAlign = TextAlign.Center,
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .align(Alignment.Center)
        )
      }
    }
  }
}

@Preview
@Composable
private fun ProductListScreenPreview() {
  ProductListScreen()
}