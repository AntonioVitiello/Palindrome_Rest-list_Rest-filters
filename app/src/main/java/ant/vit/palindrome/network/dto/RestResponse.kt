package ant.vit.palindrome.network.dto


import com.fasterxml.jackson.annotation.JsonProperty

data class RestResponse(
    @JsonProperty("posts")
    val posts: List<Post>?
)