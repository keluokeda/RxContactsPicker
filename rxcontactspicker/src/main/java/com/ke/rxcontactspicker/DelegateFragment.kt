package com.ke.rxcontactspicker

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.subjects.PublishSubject

class DelegateFragment : Fragment() {


    lateinit var resultSubject: PublishSubject<PickerResult>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun start() {
        resultSubject = PublishSubject.create()

        RxPermissions(this)
            .request(Manifest.permission.READ_CONTACTS)
            .subscribe {
                if (it) {
                    val intent = Intent(
                        Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                    )
                    startActivityForResult(intent, 100)
                } else {
                    resultSubject.onNext(PickerResult(null, "没有通讯录权限"))
                    resultSubject.onComplete()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val contactsData = data.data!!
            val contentResolver = requireActivity().contentResolver

            val cursor = contentResolver.query(
                contactsData,
                arrayOf(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                ),
                null,
                null,
                null
            )

            if (cursor != null && cursor.moveToFirst()) {
                val name = cursor.getString(0)
                val phone = cursor.getString(1)
                resultSubject.onNext(PickerResult(Contacts(name, phone)))

            }

            cursor?.close()

        }

        resultSubject.onComplete()
    }
}