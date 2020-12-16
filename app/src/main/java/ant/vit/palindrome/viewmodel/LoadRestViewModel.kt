package ant.vit.palindrome.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ant.vit.palindrome.R
import ant.vit.palindrome.model.RestModel
import ant.vit.palindrome.model.mapRestResponse
import ant.vit.palindrome.network.RestRepository
import ant.vit.palindrome.tools.SingleEvent
import ant.vit.palindrome.tools.manageLoading
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Vitiello Antonio
 */
class LoadRestViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    var restResponseLiveData = MutableLiveData<MutableList<RestModel>>()
    var errorLiveData: MutableLiveData<SingleEvent<String>> = MutableLiveData()
    var progressLiveData: MutableLiveData<SingleEvent<Boolean>> = MutableLiveData()

    companion object {
        private const val TAG = "LoadRestViewModel"
    }

    fun loadRest(id: String) {
        compositeDisposable.add(
            RestRepository.getRestSingle(id)
                .manageLoading(progressLiveData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(::mapRestResponse)
                .subscribe({ model ->
                    restResponseLiveData.value = model
                }, {
                    val message =
                        getApplication<Application>().getString(R.string.generic_network_error_message)
                    errorLiveData.value = SingleEvent(message)
                    Log.e(TAG, null, it)
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }

}