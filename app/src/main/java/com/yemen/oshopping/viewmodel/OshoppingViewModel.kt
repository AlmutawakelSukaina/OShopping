package com.yemen.oshopping.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.yemen.oshopping.model.*

import com.yemen.oshopping.retrofit.DeleteData

import com.yemen.oshopping.retrofit.FetchData
import com.yemen.oshopping.retrofit.PushData
import com.yemen.oshopping.retrofit.UpdateData
import com.yemen.oshopping.sharedPreferences.UserSharedPreferences


class OshoppingViewModel (private val app: Application) : AndroidViewModel(app) {

    val productItemLiveData: LiveData<List<ProductItem>>
    var categoryItemLiveData: LiveData<List<Category>>
    var productLiveData = MutableLiveData<Int>()
    var userLiveData_yassen = MutableLiveData<Int>()
    val mutableSearchTerm = MutableLiveData<String>()
    var reportItemLiveData: LiveData<List<Report>>
    val userLiveData= MutableLiveData <String> ()
    val userItemLiveData:LiveData<List<User>>

    init {
        productItemLiveData = FetchData().fetchProduct()
        categoryItemLiveData = FetchData().fetchCategory()
        reportItemLiveData=FetchData().fetchReport()
        userItemLiveData=FetchData().fetchUsers()
    }

    var productItemLiveDataByCategory: LiveData<List<ProductItem>> =
        Transformations.switchMap(productLiveData) { category_id ->
            FetchData().fetchProductByCategory(category_id)
        }

    fun loadProductByCategory(category_id: Int) {
        productLiveData.value = category_id
    }

    var productItemLiveDataByID: LiveData<ProductItem> =
        Transformations.switchMap(productLiveData) { product_id ->
            FetchData().fetchProductById(product_id)
        }
    var userLiveDataByID: LiveData<User> =
        Transformations.switchMap(userLiveData) { user_id ->
            FetchData().fetchUserById(user_id)
        }
    var productItemLiveDataByVendorID: LiveData<List<ProductItem>> =
        Transformations.switchMap(productLiveData) { vendor_id ->
            FetchData().fetchProductByVendorId (vendor_id)
        }

    var userItemLiveDataByEmail: LiveData<List<User>> =
        Transformations.switchMap(userLiveData) { email ->
            FetchData().fetchUserByEmail(email)
        }
    fun getUserByEmail(email: String) {
        userLiveData.value = email
    }
    fun getProductByVendorId(vendor_id: Int) {
        productLiveData.value = vendor_id
    }

    fun getProductById(product_id: Int) {
        productLiveData.value = product_id
    }
    fun getUserById(user_id: Int) {
        userLiveData.value = user_id
    }


    var searchLiveData: LiveData<List<ProductItem>> =
        Transformations.switchMap(mutableSearchTerm) { query ->
            FetchData().searchProduct(query)
        }

    fun search(query: String) {
        mutableSearchTerm.value = query
    }

    fun pushcat(category: Category) {
        PushData().pushCategory(category)
        categoryItemLiveData=FetchData().fetchCategory()
    }
    fun pushProduct(product: ProductDetails) = PushData().pushProduct(product)
    fun pushUser(user: User) = PushData().pushUser(user)
    fun pushReport(report: Report) { PushData().pushReport(report)
        reportItemLiveData=FetchData().fetchReport()
    }
    fun pushRating(rating: Rating) = PushData().pushRating(rating)


    //update data in database
    fun updateCategory(category: Category) = UpdateData().updateCategory(category)

    fun updateUser(user: User) = UpdateData().updateUser(user)
    fun updateReport(report: Report){
        UpdateData().updateReport(report)
        reportItemLiveData=FetchData().fetchReport()

    }
    //delete functions
    fun deleteCategory(category: Category) {
        DeleteData().deleteCategory(category)
        categoryItemLiveData=FetchData().fetchCategory()
    }
    fun deleteReport(report: Report) {
        DeleteData().deleteReport(report)
        reportItemLiveData=FetchData().fetchReport()
    }


    ////////
    //shared preferences
    fun setUserId(userId: Int=-1) {
        UserSharedPreferences.setStoredUserId(app, userId)
    }
    fun getstoredUserId():Int {
       return UserSharedPreferences.getStoredUserId(app)
    }

    fun setUserEmail(userEmail: String="none") {
        UserSharedPreferences.setStoredEmail(app, userEmail)
    }
    fun getStoredEmail():String? {
        return UserSharedPreferences.getStoredUserEmail(app)
    }

}