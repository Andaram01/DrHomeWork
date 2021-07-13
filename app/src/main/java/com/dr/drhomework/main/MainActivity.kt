package com.dr.drhomework.main

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.renderscript.ScriptGroup
import android.util.Log
import android.view.View
import com.dr.drhomework.R
import com.dr.drhomework.item.*
import com.dr.drhomework.item.component.DaggerCarComponent
import com.dr.drhomework.item.module.CarModule
import com.dr.drhomework.item.module.VikeModule
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import javax.inject.Inject

import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.ViewGroupBindingAdapter.setListener
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dr.drhomework.DrApplication
import com.dr.drhomework.base.BaseActivity
import com.dr.drhomework.databinding.ActivityMainBinding
import com.dr.drhomework.item.component.CarComponent
import com.dr.drhomework.main.adapter.MainContentAdapter
import com.dr.drhomework.util.Util
import com.dr.drhomework.util.extension.dpToPixel
import com.dr.drhomework.widgets.itemDecoration.ItemVerticalDecorator

class MainActivity : BaseActivity() {
    companion object {
        fun start(context: Context?) {
            context?.startActivity(
                context.intentFor<MainActivity>()
            )
        }
    }


    @Inject
    lateinit var vike : Vike

    var strtemp :String = "이거 뿌려줘"


    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

//    lateinit var mainViewModel: MainViewModel

    private val mainContentAdapter by lazy {
        MainContentAdapter()
    }

    @Inject
    lateinit var car : Car

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as DrApplication).carComponent?.inject(this)

        car.setClassName("메인1")
        car.driving()

        car.setTempData("다람")
        car.driving()


        vike.driving()


//        showLoadingPopup()

        Handler(Looper.getMainLooper()).postDelayed({
//            dismissLoadingPopup()
        }, 2000)

        setBinding()
        setRecyclerView()
        setListener()
        observerLiveData()
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.run{
            lifecycleOwner = this@MainActivity
            mainActivity = this@MainActivity
            viewModel = mainViewModel
        }
    }

    private fun setListener() {

    }

    var temp = "a"

    private fun observerLiveData() {
        with(mainViewModel){
            contentListLiveData.observe(this@MainActivity, Observer {
                dismissLoadingPopup()
                println(" contentListLiveData.observe  ${mainViewModel._stringTemp.value}")
            })

            _stringTemp.observe(this@MainActivity, Observer {

                println(" contentListLiveData.observe 11  ${mainViewModel._stringTemp.value}")
            })

            callShowLoadingLiveData.observe(this@MainActivity, Observer {
                showLoadingPopup()
            })
        }
    }

    private fun setRecyclerView() {

        binding.contentRv.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.contentRv.itemAnimator = DefaultItemAnimator()
        binding.contentRv.adapter = mainContentAdapter
        binding.contentRv.addItemDecoration(
            ItemVerticalDecorator(
                Util.dpToPx(this,8f),
                Util.dpToPx(this,8f),
                Util.dpToPx(this,8f),
                Util.dpToPx(this,8f),
                Util.dpToPx(this,8f),
                Util.dpToPx(this,8f)
            )
        )

        with(binding){
            contentRv.run{
                layoutManager = LinearLayoutManager(this@MainActivity)
                itemAnimator = DefaultItemAnimator()
                addItemDecoration(
                    ItemVerticalDecorator(
                        8.dpToPixel(),
                        8.dpToPixel()
                    )
                )
                adapter = mainContentAdapter
            }
        }
    }

    fun OnClickMain(){
//        mainViewModel._stringTemp.postValue("이거 태워줘~~~~~~~~~~~")
//        println(temp)
        Main2Activity.start(this)


    }
}



//package com.dr.drhomework.main


//
//import android.app.Activity
//import android.content.Context
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.renderscript.ScriptGroup
//import android.util.Log
//import android.view.View
//import com.dr.drhomework.R
//import com.dr.drhomework.item.*
//import com.dr.drhomework.item.component.DaggerCarComponent
//import com.dr.drhomework.item.module.CarModule
//import com.dr.drhomework.item.module.VikeModule
//import kotlinx.android.synthetic.main.activity_main.*
//import org.jetbrains.anko.intentFor
//import javax.inject.Inject
//
//import androidx.activity.viewModels
//import androidx.core.view.isVisible
//import androidx.databinding.DataBindingUtil
//import androidx.databinding.adapters.ViewGroupBindingAdapter.setListener
//import androidx.lifecycle.Observer
//import androidx.lifecycle.map
//import androidx.recyclerview.widget.DefaultItemAnimator
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.dr.drhomework.DrApplication
//import com.dr.drhomework.base.BaseActivity
//import com.dr.drhomework.databinding.ActivityMainBinding
//import com.dr.drhomework.item.component.CarComponent
//import com.dr.drhomework.main.adapter.MainContentAdapter
//import com.dr.drhomework.util.Util
//import com.dr.drhomework.util.extension.dpToPixel
//import com.dr.drhomework.widgets.itemDecoration.ItemVerticalDecorator
//
//class MainActivity : BaseActivity() {
//    companion object {
//        fun start(context: Context?) {
//            context?.startActivity(
//                context.intentFor<MainActivity>()
//            )
//        }
//    }
//
//    @Inject
//    lateinit var car : Car
//
//    @Inject
//    lateinit var vike : Vike
//
//    var strtemp :String = "이거 뿌려줘"
//
//
//    private lateinit var binding: ActivityMainBinding
//
//    private val mainViewModel: MainViewModel by viewModels()
//
//    private val mainContentAdapter by lazy {
//        MainContentAdapter()
//    }
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        (application as DrApplication).carComponent?.inject(this)
//
//        car.driving()
//
////        println("onCreate 001 $strDaram")
////        MainViewModel(mainRepository = this)
////        MainViewModel(this)
////        MainViewModel()
//
//
//
//
////        with(strDaram){
////
////        }
////
////        strDaram?.run {
////            println("onCreate 003 ${toString()}" )
////            strDaram01?.let {
////                if(contains(""))
////                    println("onCreate 003 ${toString()}" )
////
////                if(it.contains(""))
////                    println("onCreate 003 ${toString()}" )
////
////
////
////            }
//////            strDaram?.let { it->
//////
//////                it.toString()
//////                println("onCreate 004 $it" )
//////
//////            }
////        }
////
////
////
////
////        var ary02 = arrayListOf()
////
////        var ary03 = arrayOf()
////
////        var ary04 = mutableListOf<String>()
////
////        var ary01 = ArrayList<String>()
//
//
//
//
//
//
//
//
//
//
////        strDaram.let {
////            println("onCreate 005 $it" )
////        }
////
////        if(strDaram != null){
////
////        }else{
////
////        }
////
////        strDaram?:toString().let {
////            println("onCreate 004 $it" )
////        }
////
////        strDaram?.let {
////            println("onCreate 004 $it" )
////        }
////
////        strDaram!!.let {
////            println("onCreate 004 $it" )
////        }
//
//
//
////        vike!!.driving()
//
//        println("onCreate 002 ")
//
//        vike.driving()
//        setBinding()
//        setRecyclerView()
//        setListener()
//        observerLiveData()
//
//    }
//
//    private fun setBinding() {
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        binding.run{
//            lifecycleOwner = this@MainActivity
//            mainActivity = this@MainActivity
//            viewModel = mainViewModel
//        }
//    }
//
//    private fun setListener() {
//
//    }
//
//    var temp = "a"
//
//    private fun observerLiveData() {
//        with(mainViewModel){
////상품 정보 호출로 검색입력 포커스 아웃
//            contentListLiveData.observe(this@MainActivity, Observer {
//                println(" contentListLiveData.observe  ${mainViewModel._stringTemp.value}")
//            })
//
//            _stringTemp.observe(this@MainActivity, Observer {
//
//                println(" contentListLiveData.observe 11  ${mainViewModel._stringTemp.value}")
//            })
//        }
//    }
//
//    private fun setRecyclerView() {
//
//        binding.contentRv.layoutManager = LinearLayoutManager(this@MainActivity)
//        binding.contentRv.itemAnimator = DefaultItemAnimator()
//        binding.contentRv.adapter = mainContentAdapter
//        binding.contentRv.addItemDecoration(
//            ItemVerticalDecorator(
//                Util.dpToPx(this,8f),
//                Util.dpToPx(this,8f),
//                Util.dpToPx(this,8f),
//                Util.dpToPx(this,8f),
//                Util.dpToPx(this,8f),
//                Util.dpToPx(this,8f)
//            )
//        )
//
//        with(binding){
//            contentRv.run{
//                layoutManager = LinearLayoutManager(this@MainActivity)
//                itemAnimator = DefaultItemAnimator()
//                addItemDecoration(
//                    ItemVerticalDecorator(
//                        8.dpToPixel(),
//                        8.dpToPixel()
//                    )
//                )
//                adapter = mainContentAdapter
//            }
//        }
//    }
//
//    fun OnClickMain(){
////        case 1
//        mainViewModel._stringTemp.postValue("이거 태워줘~~~~~~~~~~~")
//
////
////        mainViewModel._stringTemp2 = "클릭햇다"
//
////        case 2
////        mainViewModel._stringTemp2 = "클릭햇다"
////        binding.tvTitle.text = mainViewModel._stringTemp2
//
//
//        println(temp)
//    }
//}