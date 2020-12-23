package ant.vit.palindrome.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ant.vit.palindrome.R
import ant.vit.palindrome.model.RestModel
import ant.vit.palindrome.tools.loadImage
import kotlinx.android.synthetic.main.item_post.view.*

/**
 * Created by Vitiello Antonio
 */
class PostListAdapter : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {
    private val mRestModels = mutableListOf<RestModel>()
    private val mRestModelsBck = mutableListOf<RestModel>()
    private var mFilterOn = false

    companion object {
        const val TAG = "PostListAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = mRestModels.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindItem(mRestModels[position])

    fun switchData(data: List<RestModel>?) {
        mRestModels.clear()
        mRestModelsBck.clear()
        if (data != null) {
            mRestModels.addAll(data)
            mRestModelsBck.addAll(mRestModels)
        }
        notifyDataSetChanged()
    }

    fun applyFilter(filterOn: Boolean) {
        mFilterOn = filterOn
        if (mFilterOn) {
            mRestModels.apply {
                val sortedDescending = mRestModels.filter { it.userId == 1 }
                    .sortedByDescending { it.publishDate }
                clear()
                addAll(sortedDescending)
            }
        } else {
            mRestModels.apply {
                clear()
                addAll(mRestModelsBck)
            }
        }
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(model: RestModel) {
            with(itemView) {
                userIdText.text = model.id?.toString()
                titleText.text = model.title
                descriptionText.text = model.description
                postImage.loadImage(model.imageUrl)
                publishDateText.text = model.publishDateString
            }
        }

    }

}