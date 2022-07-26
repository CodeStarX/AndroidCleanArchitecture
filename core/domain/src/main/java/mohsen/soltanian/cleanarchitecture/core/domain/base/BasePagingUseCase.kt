package mohsen.soltanian.cleanarchitecture.core.domain.base

import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class BasePagingUseCase<in Params, ReturnType> where ReturnType : Any {

    protected abstract fun execute(params: Params): Flow<PagingData<ReturnType>>

    operator fun invoke(params: Params): Flow<PagingData<ReturnType>> {
        return execute(params).flowOn(Dispatchers.IO)
    }
}
