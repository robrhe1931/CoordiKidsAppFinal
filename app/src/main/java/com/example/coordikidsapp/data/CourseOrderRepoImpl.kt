package com.example.coordikidsapp.data

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.coordikidsapp.commons.Constants
import com.example.coordikidsapp.domain.models.CourseOrder
import com.example.coordikidsapp.domain.models.UserProfileDetails
import com.example.coordikidsapp.domain.repository.CourseOrderRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CourseOrderRepoImpl(
    private val profileSharedPreferences: SharedPreferences
) :
    CourseOrderRepo {

    private var auth: FirebaseAuth? = null
    private var db: FirebaseFirestore? = null
    private var databaseInstance: FirebaseDatabase? = null

    private val orderStatusLiveData = MutableLiveData<Boolean>()

    init {
        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore
        databaseInstance = Firebase.database

    }

    override fun registerUser(courseOrder: CourseOrder) {
        CoroutineScope(Dispatchers.IO).launch {
            auth?.createUserWithEmailAndPassword(
                courseOrder.userDetails.email,
                courseOrder.userDetails.password
            )
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(Constants.CREATE_ORDER_TAG, "createUserWithEmail:success")
                        auth!!.currentUser!!.sendEmailVerification()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(Constants.VERIFY_EMAIL_TAG, "Email sent")
                                    uploadUserProfile(courseOrder)
                                }
                            }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(
                            Constants.CREATE_ORDER_TAG,
                            "createUserWithEmail:failure",
                            task.exception
                        )
                        orderStatusLiveData.postValue(false)
                    }
                }
        }
    }

    override fun uploadUserProfile(courseOrder: CourseOrder) {
        val userDetails = courseOrder.userDetails
        val userProfileDetails = UserProfileDetails(
            userDetails.caregiverFName,
            userDetails.caregiverLName,
            userDetails.phoneNumber,
            userDetails.email,
            userDetails.childFName,
            userDetails.childLName,
            userDetails.childDOB
        )
        auth?.currentUser?.let {
            db?.collection(Constants.USERS)
                ?.document(it.uid)
                ?.set(userProfileDetails, SetOptions.merge())
                ?.addOnSuccessListener {
                    Log.d(
                        Constants.CREATE_ORDER_TAG,
                        "Upload User Details Status : DocumentSnapshot successfully written!"
                    )
                    saveUserProfileDetails(auth!!.currentUser!!.uid, userProfileDetails)
                    createOrder(courseOrder)
                }
                ?.addOnFailureListener {
                    Log.e(
                        Constants.CREATE_ORDER_TAG,
                        "Upload User Details Status : DocumentSnapshot upload failed"
                    )
                    orderStatusLiveData.postValue(false)
                }
        }
    }

    override fun createOrder(courseOrder: CourseOrder) {
        CoroutineScope(Dispatchers.IO).launch {
            val userDetails = courseOrder.userDetails
            userDetails.password = ""
            courseOrder.userDetails = userDetails
            auth?.currentUser?.let {
                databaseInstance?.reference?.child(Constants.ORDERS)?.child(it.uid)
                    ?.child(courseOrder.orderId)
                    ?.setValue(courseOrder)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(
                                Constants.CREATE_ORDER_TAG,
                                "Create Order Status : DocumentSnapshot successfully written!"
                            )
                            orderStatusLiveData.postValue(true)
                        } else {
                            Log.e(
                                Constants.CREATE_ORDER_TAG,
                                "Create Order Status : DocumentSnapshot upload failed"
                            )
                            orderStatusLiveData.postValue(false)
                        }
                    }
            }
        }
    }

    private fun saveUserProfileDetails(id: String, userProfileDetails: UserProfileDetails) {
        val editor = profileSharedPreferences.edit()
        editor.putString(Constants.USER_ID, id)
        editor.putString(Constants.CAREGIVER_FNAME, userProfileDetails.caregiverFName)
        editor.putString(Constants.CAREGIVER_LNAME, userProfileDetails.caregiverLName)
        editor.putString(Constants.PHONE_NUMBER, userProfileDetails.phoneNumber)
        editor.putString(Constants.USER_EMAIL, userProfileDetails.email)
        editor.putString(Constants.CHILD_FNAME, userProfileDetails.childFName)
        editor.putString(Constants.CHILD_LNAME, userProfileDetails.childLName)
        editor.putString(Constants.CHILD_DOB, userProfileDetails.childDOB)
        editor.apply()
    }


    override fun getOrderStatusLiveData(): MutableLiveData<Boolean> {
        return orderStatusLiveData
    }
}