package ant.vit.palindrome.model

import ant.vit.palindrome.network.dto.RestResponse
import ant.vit.palindrome.tools.Utils.Companion.formatDateOrNull
import ant.vit.palindrome.tools.Utils.Companion.parseDateOrNull
import ant.vit.palindrome.tools.Utils.Companion.yearDateFormat

/**
 * Created by Vitiello Antonio
 */
fun mapRestResponse(response: RestResponse): MutableList<RestModel> {
    return mutableListOf<RestModel>().apply {
        response.posts?.forEach { post ->
            add(RestModel().apply {
                id = post.id
                userId = post.userId
                title = post.title
                description = post.description
                imageUrl = post.image
                publishDate = parseDateOrNull(yearDateFormat, post.publishedAt)
                publishDateString = formatDateOrNull(yearDateFormat, publishDate)
            })
        }
    }
}

