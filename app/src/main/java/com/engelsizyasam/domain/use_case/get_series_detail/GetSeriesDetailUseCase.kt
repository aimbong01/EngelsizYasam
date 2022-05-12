package com.engelsizyasam.domain.use_case.get_series_detail

import com.engelsizyasam.common.Resource
import com.engelsizyasam.data.remote.dto.toSeriesDetail
import com.engelsizyasam.domain.model.SeriesDetail
import com.engelsizyasam.domain.repository.SeriesDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSeriesDetailUseCase @Inject constructor(
    private val repository: SeriesDetailRepository
) {
    private var seriesPage: String = ""
     var playlistId: String = ""


    private var seriesDetailList = arrayListOf<SeriesDetail>()

    operator fun invoke(): Flow<Resource<List<SeriesDetail>>> = flow {
        try {
            emit(Resource.Loading<List<SeriesDetail>>())

            val total = repository.getSeriesDetail(pageToken = seriesPage, playlistId = playlistId).pageInfo.totalResults
            val page = if (total % 50 == 0)
                total / 50
            else
                (total / 50) + 1


            for (i in 1..page) {
                val base = repository.getSeriesDetail(pageToken = seriesPage, playlistId = playlistId)
                seriesDetailList.addAll(base.items.map { it.toSeriesDetail() })
                seriesPage = base.nextPageToken
            }

            emit(Resource.Success<List<SeriesDetail>>((seriesDetailList)))
        } catch (e: HttpException) {
            emit(Resource.Error<List<SeriesDetail>>(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error<List<SeriesDetail>>(e.localizedMessage ?: "Couldn't reach server. Check your internet connection."))
        }
    }
}