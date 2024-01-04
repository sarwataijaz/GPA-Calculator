package com.example.gpacal

class Stack<T>{
    var stackList: ArrayList<T> = ArrayList()

    fun peek(): T? {
        if(stackList.isEmpty())
            return null
        else {
            return stackList.get(stackList.size - 1)
        }
    }

     fun getSize(): Int {
        return stackList.size
    }

     fun pop(): T? {
        if(stackList.isEmpty())
            return null
        else {
            return stackList.removeAt(stackList.size - 1)
        }
    }

     fun push(element: T) {
        stackList.add(element)
    }

    fun isEmpty(): Boolean {
        return stackList.isEmpty()
    }
}