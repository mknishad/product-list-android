package com.android.monir.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.monir.data.mappers.toProduct
import com.android.monir.data.remote.ProductApi
import com.android.monir.domain.model.Product
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductPagingSource @Inject constructor(val api: ProductApi) : PagingSource<Int, Product>() {
  override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      val anchorPage = state.closestPageToPosition(anchorPosition)
      anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
    try {
      val skip = params.key ?: 0
      val response = api.getUsers(limit = params.loadSize, skip = skip)
      return LoadResult.Page(
        data = response.products.map { it.toProduct() },
        prevKey = null,
        nextKey = if (response.products.size + skip < response.total) {
          skip + params.loadSize
        } else {
          null
        }
      )
    } catch (e: IOException) {
      return LoadResult.Error(e)
    } catch (e: HttpException) {
      return LoadResult.Error(e)
    }
  }

  /*override val keyReuseSupported: Boolean
    get() = true*/
}