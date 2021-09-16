//package com.application
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.helper.NetworkHelper
//import com.madapps.prefrences.EasyPrefrences
//
//abstract class BaseFragment : Fragment() {
//
//    protected abstract var layoutRes: Int
//
//    lateinit var prefs: EasyPrefrences
//
//    lateinit var networkHelper: NetworkHelper
//
//    protected abstract fun onViewCreated(view: View)
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val view = inflater.inflate(layoutRes, container, false)
//        prefs = EasyPrefrences(container!!.context)
//        networkHelper = NetworkHelper(container.context)
//        onViewCreated(view)
//
//        return view
//    }
//}
//
//fun BaseFragment.go(target: Class<*>) {
//    val intent = Intent(activity, target)
//    startActivity(intent)
//
//}