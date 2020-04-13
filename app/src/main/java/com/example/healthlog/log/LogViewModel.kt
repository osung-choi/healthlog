package com.example.healthlog.log

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthlog.database.HealthLogDB
import com.example.healthlog.database.entitiy.ExerciseLog
import com.example.healthlog.utils.Utils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LogViewModel: ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _addExerciseLog = MutableLiveData<ArrayList<ExerciseLog>>().apply { value = ArrayList() }
    val addExerciseLog: LiveData<ArrayList<ExerciseLog>> = _addExerciseLog

    private val _exerciseList = MutableLiveData<List<String>>()
    val exerciseList: LiveData<List<String>> = _exerciseList

    private var searchText = ""
    private lateinit var date: String


    val addExerciseClick = View.OnClickListener {
        checkSearchExercise(searchText)
    }

    val searchDone = {
        checkSearchExercise(searchText)
    }

    fun onContentTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        searchText = s.toString()
    }

    fun getExerciseList(date: String) {
        this.date = date
        HealthLogDB.getInstance()?.let {
            it.getExerciseDao().selectExerciseAllItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapSingle { itemList ->
                    Observable.fromIterable(itemList)
                        .map { item -> Utils.makeExerciseItemToSearchName(item) }
                        .toList()
                }.subscribe { itemList ->
                    _exerciseList.value = itemList
                }
        }
    }
    //^[가-힣][가-힣]*\([가-힣][가-힣]*\)
    private fun checkSearchExercise(searchText: String) {
        HealthLogDB.getInstance()?.let { db ->
            val disposable = Observable.just(searchText)
                .subscribeOn(Schedulers.io())
                .filter { searchName -> Utils.checkExerciseSearchName(searchName) }
                .map { okName -> Utils.makeSearchNameToExerciseItem(okName) }
                .filter { item -> db.getExerciseDao().selectExerciseItem(item.name, item.part) > 0}
                .map { ExerciseLog(0, date, it, "kg",3) } //디폴트 세트 수 3
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { exerciseLog ->
                    val list = _addExerciseLog.value
                    list?.let {
                        it.add(exerciseLog)
                        _addExerciseLog.value = it
                    }
                }

            compositeDisposable.add(disposable)
        }
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}

