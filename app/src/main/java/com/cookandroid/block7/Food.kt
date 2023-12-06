package com.cookandroid.block7

import android.view.View
import android.widget.ImageView

class Food(GameActivity: GameActivity, nameFood: String, nameKorFood: String, itemImage: ImageView) {
    // 식량 이름 (음식 or 물)
    var nameFood: String = nameFood
    var nameKorFood: String = nameKorFood
    // 식량 개수
    var count: Int = 0

    // 식량 획득 메소드
    fun getFood(cnt: Int) {
        this.count += cnt
    }

    // 식량 소모 메소드
    fun loseFood(cnt: Int) {
        count = maxOf(0, count - cnt)
    }

    fun getFoodRandom(start: Int, end: Int) {
        var cnt: Int = (start..end).random()
        getFood(cnt)
    }

    fun loseFoodRandom(start: Int, end: Int) {
        var lostCount: Int = (start..end).random()
        loseFood(lostCount)
    }

    // food의 이미지 업데이트 메소드
    fun updateVis() {

    }
}