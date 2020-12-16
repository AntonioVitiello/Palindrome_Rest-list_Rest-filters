package ant.vit.palindrome.network

import ant.vit.palindrome.network.dto.RestResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Vitiello Antonio
 */
interface ApiService {

    //eg: http://www.mocky.io/v2/59f2e79c2f0000ae29542931
    @GET("{id}")
    fun getRestSingle(@Path("id") id: String): Single<RestResponse>

}