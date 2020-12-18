package ant.vit.palindrome.tools

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import ant.vit.palindrome.IsPalindromeActivity
import ant.vit.palindrome.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.Single
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Vitiello Antonio
 */
fun String.isPalindrome(): Boolean {
    val normalizedString = toLowerCase(Locale.getDefault())
    val splits = normalizedString.split(" ")
    val reversedString = splits.joinToString(" ") { split -> split.reversed() }
    Log.d(
        "isPalindrome",
        "isPalindrome:${normalizedString == reversedString} - normalizedString:$normalizedString - reversedString:$reversedString"
    )
    return normalizedString == reversedString
}

fun String.isPalindromeOnlyAlpha(): Boolean {
    val normalizedString =
        replace(Utils.onlyAlphaRegex, "").toLowerCase(Locale.getDefault())
    val splits = normalizedString.split(" ")
    val reversedString = splits.joinToString(" ") { split -> split.reversed() }
    Log.d("isPalindrome",
        "isPalindrome:${normalizedString == reversedString} - normalizedString:$normalizedString - reversedString:$reversedString"
    )
    return normalizedString == reversedString
}


fun <T> Single<T>.manageLoading(showProgressLiveData: MutableLiveData<SingleEvent<Boolean>>): Single<T> {
    return compose { upstream ->
        upstream
            .doOnSubscribe {
                showProgressLiveData.postValue(SingleEvent(true))
            }
            .doOnDispose {
                showProgressLiveData.postValue(SingleEvent(false))
            }
            .doOnError {
                showProgressLiveData.postValue(SingleEvent(false))
            }
            .doOnSuccess {
                showProgressLiveData.postValue(SingleEvent(false))
            }
    }
}

fun Date.format(dateFormat: SimpleDateFormat): String {
    return dateFormat.format(this)
}

fun String.parseDateOrNull(dateFormat: SimpleDateFormat): Date? {
    return try {
        dateFormat.parse(this)
    } catch (exc: ParseException) {
        Log.e("AndroidExtensions", "Error while parsing date:$this.")
        null
    }
}

fun ImageView.loadImage(imageUrl: String?) {
    Picasso.get()
        .load(imageUrl)
        .fit()
        .placeholder(R.drawable.ic_placeholder)
        .error(R.drawable.ic_broken_image)
        .into(this, object : Callback {
            override fun onSuccess() {
                Log.d("AndroidExtensions", "Image loaded: $imageUrl")
            }

            override fun onError(exc: Exception) {
                Log.e("AndroidExtensions", "Error while loading image: $imageUrl", exc)
            }
        })
}


