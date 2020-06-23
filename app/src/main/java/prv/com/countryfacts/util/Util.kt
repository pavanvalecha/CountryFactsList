package prv.com.countryfacts.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import prv.com.countryfacts.R
import timber.log.Timber


@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String?){
    Timber.d( "loadImage() - $url")
    view.loadImage(url, getProgressDrawable(view.context))
}


fun ImageView.loadImage(uri: String?, circularProgressDrawable: CircularProgressDrawable){
    Timber.d( uri.let { it ?: "null" })
    val options = RequestOptions.placeholderOf(circularProgressDrawable)
        .error(R.mipmap.ic_launcher)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

fun getProgressDrawable(context: Context): CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}