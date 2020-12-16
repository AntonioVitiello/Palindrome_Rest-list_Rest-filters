package ant.vit.palindrome

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ant.vit.palindrome.model.RestModel
import ant.vit.palindrome.tools.SingleEvent
import ant.vit.palindrome.viewmodel.LoadRestViewModel
import kotlinx.android.synthetic.main.activity_load_posts.*


/**
 * Created by Vitiello Antonio
 */
class LoadPostActivity : AppCompatActivity() {
    private lateinit var mViewModel: LoadRestViewModel
    private lateinit var mAdapter: PostListAdapter
    private var filterChecked = false
    private var mMenuRes = R.menu.empty_menu

    companion object {
        const val TAG = "LoadRest"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_posts)

        mViewModel = ViewModelProvider(this).get(LoadRestViewModel::class.java)
        mViewModel.loadRest("59f2e79c2f0000ae29542931")

        mViewModel.progressLiveData.observe(this, ::showProgress)
        mViewModel.errorLiveData.observe(this, ::showError)
        mViewModel.restResponseLiveData.observe(this, ::fillData)

        initComponents()
    }

    private fun initComponents() {
        mAdapter = PostListAdapter()
        postRecyclerView.adapter = mAdapter

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        nextButton.setOnClickListener {
            nextAction()
        }
    }

    private fun fillData(models: MutableList<RestModel>) {
        mAdapter.switchData(models)
    }

    private fun showError(event: SingleEvent<String>) {
        event.getContentIfNotHandled()?.let { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    private fun showProgress(event: SingleEvent<Boolean>) {
        progressView.visibility =
            if (event.getContentIfNotHandled() == true) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
    }

    override fun onBackPressed() {
        if (mMenuRes != R.menu.empty_menu) {
            mMenuRes = R.menu.empty_menu
            invalidateOptionsMenu()
            mAdapter.applyFilter(false)
            nextButton.text = getString(R.string.rest_filters_label)
        } else {
            startPreviousActivity()
        }
    }

    private fun nextAction() {
        if (mMenuRes != R.menu.empty_menu) {
            startPreviousActivity()
            nextButton.text = getString(R.string.rest_list_label)
        } else {
            mMenuRes = R.menu.filter_menu
            invalidateOptionsMenu()
            nextButton.text = getString(R.string.palindrome_label)
        }
    }

    private fun startPreviousActivity() {
        val intent = Intent(this, IsPalindromeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(mMenuRes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_item -> {
                filterChecked = !item.isChecked()
                item.isChecked = filterChecked
                mAdapter.applyFilter(filterChecked)
                true
            }
            else -> {
                false
            }
        }
    }

}
