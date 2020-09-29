package com.ke.rxcontactspicker

import androidx.fragment.app.FragmentActivity
import io.reactivex.Observable

class RxContactsPicker( activity: FragmentActivity) {

    private val tag = BuildConfig.LIBRARY_PACKAGE_NAME + RxContactsPicker::class.java.name


    private val delegateFragment: DelegateFragment

    init {

        val fragment = activity.supportFragmentManager.findFragmentByTag(tag)

        if (fragment == null) {
            delegateFragment = DelegateFragment()
            activity.supportFragmentManager.beginTransaction().add(delegateFragment, tag).commitNow()
        } else {
            delegateFragment = fragment as DelegateFragment
        }
    }

    fun start(): Observable<PickerResult> {
        delegateFragment.start()

        return Observable.just(1)
            .flatMap {
                delegateFragment.resultSubject
            }

    }
}