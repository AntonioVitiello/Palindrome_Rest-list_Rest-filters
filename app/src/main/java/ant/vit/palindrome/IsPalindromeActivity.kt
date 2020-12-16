package ant.vit.palindrome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ant.vit.palindrome.tools.isPalindrome
import kotlinx.android.synthetic.main.activity_is_palindrome.*

/**
 * Created by Vitiello Antonio
 */
class IsPalindromeActivity : AppCompatActivity() {

    companion object {
        val onlyAlphaRegex by lazy(LazyThreadSafetyMode.NONE) { "[^A-Za-z]".toRegex() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_is_palindrome)

        val firstString = "anNa bananaB kaYak"
        val secondString = "anNa banana kaYak"

        val firstIsPalindrome = firstString.isPalindrome()
        val secondIsPalindrome = secondString.isPalindrome()

        firstText.text = if (firstIsPalindrome) {
            getString(R.string.is_palindrome_label, firstString, "")
        } else {
            getString(R.string.is_palindrome_label, secondString, "not")
        }

        secondText.text = if (secondIsPalindrome) {
            getString(R.string.is_palindrome_label, firstString, "")
        } else {
            getString(R.string.is_palindrome_label, secondString, "not")
        }

        nextButton.setOnClickListener {
            val intent = Intent(this, LoadPostActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

}

