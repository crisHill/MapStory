package com.zls.mapstory.type

import kotlin.random.Random

/**
 * @author criszhai
 * @date 2021/11/3 13:17
 * @desc
 */
enum class Direction8(val deltaX: Int, val deltaY: Int, val type: Int, val desc: String) {

    RIGHT(1, 0,1, "→"),
    RIGHT_DOWN(1, 1,2, "↘"),
    DOWN(0, 1,3, "↓"),
    LEFT_DOWN(-1, 1,4, "↙"),
    LEFT(-1, 0,5, "←"),
    LEFT_UP(-1, -1,6, "↖"),
    UP(0, -1,7, "↑"),
    RIGHT_UP(1, -1,8, "↗");

    companion object {
        fun getByType(type: Int): Direction8? {
            for (dir in values()){
                if (dir.type == type){
                    return dir
                }
            }
            return null
        }
        fun getShuffledList(random: Random): MutableList<Direction8> {
            val raw: MutableList<Direction8> = mutableListOf()
            raw.addAll(values())

            val list: MutableList<Direction8> = mutableListOf()
            while (raw.size > 0){
                val index = random.nextInt(raw.size)
                list.add(raw.removeAt(index))
            }
            return list
        }
    }

    fun next(): Direction8 {
        if (this == RIGHT_UP) {
            return RIGHT
        }
        return getByType(this.type + 1)!!
    }

    fun pre(): Direction8 {
        if (this == RIGHT) {
            return RIGHT_UP
        }
        return getByType(this.type - 1)!!
    }

}