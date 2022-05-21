package com.kdz.jarvis.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kdz.jarvis.network.models.MarvelCharacter
import com.kdz.jarvis.network.models.wrappers.BaseDataWrapper
import com.kdz.jarvis.network.result.NetworkResult
import com.kdz.jarvis.network.services.MarvelService
import javax.inject.Inject

class CharacterPagingSource
@Inject constructor(
    private val marvelService: MarvelService
) : PagingSource<Int, MarvelCharacter>() {

    override fun getRefreshKey(state: PagingState<Int, MarvelCharacter>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.id
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MarvelCharacter> {
        // Start paging with the STARTING_KEY if this is the first load
        val start = params.key ?: STARTING_KEY
        // Load as many items as hinted by params.loadSize
        val range = start until start + params.loadSize

        val charactersResult = marvelService.getCharacters(params.loadSize, start + params.loadSize)

        return charactersResult.toLoadResult(
            prevKey = when (start) {
                STARTING_KEY -> null
                else -> ensureValidKey(key = range.first - params.loadSize)
            },
            nextKey = range.last + 1
        )
    }

    private fun ensureValidKey(key: Int) = STARTING_KEY.coerceAtLeast(key)

    private fun NetworkResult<BaseDataWrapper<MarvelCharacter>>.toLoadResult(
        prevKey: Int?,
        nextKey: Int
    ): LoadResult<Int, MarvelCharacter> =
        when (this) {
            is NetworkResult.ApiError -> LoadResult.Error(Throwable(error.status))
            is NetworkResult.NetworkError -> LoadResult.Error(exception)
            is NetworkResult.Success -> LoadResult.Page(value.data.results, prevKey, nextKey)
            is NetworkResult.UnknownError -> LoadResult.Error(throwable ?: Throwable())
        }

    companion object {
        private const val STARTING_KEY = 0
    }
}