package com.engelsizyasam.domain.use_case.get_series

import com.engelsizyasam.common.Resource
import com.engelsizyasam.data.remote.dto.toSeries
import com.engelsizyasam.domain.model.Series
import com.engelsizyasam.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSeriesUseCase @Inject constructor(
    private val repository: SeriesRepository
) {
    private var seriesPage: String = ""

     private val seriesList = arrayListOf<Series>()

    operator fun invoke(): Flow<Resource<List<Series>>> = flow {
        try {
            emit(Resource.Loading<List<Series>>())


            val total = repository.getSeries(pageToken = seriesPage).pageInfo.totalResults
            val page = if (total % 5 == 0)
                total / 5
            else
                (total / 5) + 1

                for (i in 1..page) {
                    val series = repository.getSeries(pageToken = seriesPage)
                    seriesList.addAll(series.items.map { it.toSeries() })
                    seriesPage = series.nextPageToken
                }


            emit(Resource.Success<List<Series>>((seriesList)))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Series>>(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error<List<Series>>(e.localizedMessage ?: "Couldn't reach server. Check your internet connection."))
        }
    }
}