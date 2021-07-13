package com.dr.drhomework.item

class Car constructor(engine: Engine, wheels: Wheels){


//    lateinit var strTemp : String
    var strTemp = "변경전"
    var strTemp02 = ""
    var strClassName = ""

    fun driving() : String{

        println("드라이빙 우와 $strTemp?  :  $strClassName")

        val size = 10

        var arr = Array<IntArray>(size, {IntArray(size)})

        var strSize = size.toString()
        strSize.length

        if(strSize.equals("")){

        }


        test(arr)


        return "드라이빙"
    }

    fun setTempData(string: String){
        strTemp = string
    }

    fun setClassName(string: String){
        strClassName = string
    }

    fun test(i: Array<IntArray>) {
        System.out.printf("%3d ",i.size);

    }
}