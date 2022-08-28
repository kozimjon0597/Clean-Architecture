package uz.kozimjon.cleanarchitecture.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.await
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


object FlowCallAdapterFactory: CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Flow::class.java != getRawType(returnType)) return null

        check(returnType is ParameterizedType) {
            ""
        }

        return object : CallAdapter<Any, Any> {
            override fun adapt(call: Call<Any>): Any {
                return flow {
                    try {
                        emit(call.await())
                    } catch (e: KotlinNullPointerException) {
                        emit(Unit)
                    }
                }
            }

            override fun responseType(): Type = getParameterUpperBound(0, returnType)
        }

    }

}