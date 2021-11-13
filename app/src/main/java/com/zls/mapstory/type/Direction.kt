package com.zls.mapstory.type

import kotlin.random.Random

/**
 * @author criszhai
 * @date 2021/11/3 13:17
 * @desc
 */
enum class Direction(val deltaX: Int, val deltaY: Int, val type: Int, val desc: String) {

    RIGHT(1, 0,1, "→"),
    DOWN(0, 1,2, "↓"),
    LEFT(-1, 0,3, "←"),
    UP(0, -1,4, "↑");

    companion object {
        fun getByType(type: Int): Direction? {
            for (dir in values()){
                if (dir.type == type){
                    return dir
                }
            }
            return null
        }
        fun getShuffledList(random: Random): MutableList<Direction> {
            val raw: MutableList<Direction> = mutableListOf()
            raw.addAll(values())

            val list: MutableList<Direction> = mutableListOf()
            while (raw.size > 0){
                val index = random.nextInt(raw.size)
                list.add(raw.removeAt(index))
            }
            return list
        }
    }

    fun next(): Direction {
        if (this == UP) {
            return RIGHT
        }
        return getByType(this.type + 1)!!
    }

    fun pre(): Direction {
        if (this == RIGHT) {
            return UP
        }
        return getByType(this.type - 1)!!
    }

    fun opposite(): Direction {
        if (this.deltaY == 0){
            return getByType(4 - this.type)!!
        }
        return getByType(6 - this.type)!!
    }

    fun getDirections(): MutableList<Direction> {
        val result = mutableListOf<Direction>()
        result.add(pre())
        result.add(this)
        result.add(next())
        return result
    }

}