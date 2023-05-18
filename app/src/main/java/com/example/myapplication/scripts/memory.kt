package com.example.myapplication.scripts

class memory {
    private var hashTable : MutableMap<String, Int> = mutableMapOf()

    /**
     * creat a var with value 0
     **/
    fun createDefaultVariables(names: List<String>) {
        for (name: String in names) {
            hashTable[name] = 0
        }
    }

    /**
     * set a value for an existing value
     **/
    fun setVariableValue(name: String, value: Int) {
        hashTable[name] = value
    }

    /**
     * return the value of a var
     **/
    fun getVariableValue(name: String): Int? {
        return hashTable[name]
    }

    /**
     * check if the var was already declared
     **/
    fun isVariableExist(name: String): Boolean {
        return name in hashTable.keys
    }

    /**
     * delete all vars
     **/
    fun clearVariables() {
        hashTable.clear()
    }

}