package ant.vit.palindrome.network

import ant.vit.palindrome.network.dto.RestResponse
import io.reactivex.Single

/**
 * Created by Vitiello Antonio
 */
object RestRepository {
    private var networkProvider = NetworkProvider()

    fun getRestSingle(id: String): Single<RestResponse> {
        return networkProvider.getRestSingle(id)
    }

}