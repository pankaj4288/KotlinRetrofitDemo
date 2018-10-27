package com.pankaj.kotlinretrofitdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val apiInterface by lazy {
        APIInterface.create()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchDetails()
    }

    private fun fetchDetails() {
        disposable =
                apiInterface.getDetails()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { result -> showResult(result) }
    }

    private fun showResult(result: User?) {
        result_tv.text = result.toString()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
