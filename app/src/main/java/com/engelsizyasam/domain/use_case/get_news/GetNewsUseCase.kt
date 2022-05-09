package com.engelsizyasam.domain.use_case.get_news

import com.engelsizyasam.domain.repository.NewsRepository
import com.engelsizyasam.common.Resource
import com.engelsizyasam.data.remote.dto.toNews
import com.engelsizyasam.domain.model.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(): Flow<Resource<List<News>>> = flow {
        try {
            emit(Resource.Loading<List<News>>())
            //TODO("add result success control")
            val news = repository.getNews().articles.map { it.toNews() }
            emit(Resource.Success<List<News>>((news)))
        } catch (e: HttpException) {
            emit(Resource.Error<List<News>>(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error<List<News>>(e.localizedMessage ?: "Couldn't reach server. Check your internet connection."))
        }
    }
}