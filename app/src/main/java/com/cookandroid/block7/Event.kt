package com.cookandroid.block7

import android.content.Context

// Event: MainGame, 이벤트 이름, 호출 가능 여부, 가중치
class Event(GameActivity: GameActivity, eventName: String,  weight: Int, isAvailable: Boolean) {
    // 게임 액티비티
    var GameActivity: GameActivity = GameActivity
    // 이벤트 이름
    var eventName: String = eventName
    // 가중치
    var weight: Int = weight
    // 호출 가능 여부
    var isAvailable: Boolean = isAvailable

    // 스크립트
    private var preScript: String = ""
    private var postScript: String = ""


    fun executeEventEffect() {
        when (eventName) {
            "event_invasion_robbery" -> event_invasion_robbery_effect()
            "event_invasion_grandma" -> event_invasion_grandma_effect()
            "event_invasion_firefighter" -> event_invasion_firefighter_effect()
            "evnet_plunder_oldMan" -> evnet_plunder_oldMan()
            "evnet_plunder_b1cr" -> evnet_plunder_b1cr()
            "evnet_plunder_nursingHome" -> evnet_plunder_nursingHome()
            "evnet_plunder_Lab" -> evnet_plunder_Lab()
            // 추가적인 이벤트에 대한 처리
            else -> {
                // 정의되지 않은 이벤트에 대한 기본 처리
            }
        }
    }

    /* 랜덤 이벤트 */
    // 랜덤으로 가지고 있는 아이템 중 하나를 잃고, 아이템 이름을 반환함
    fun loseRandomItem(): String {
        var selectedItem: Item? = GameActivity.getRandomItemitemListOwned()
        if(selectedItem != null) selectedItem.loseItem()
        if (selectedItem != null) { return selectedItem.nameKorItem }
        else return ""
    }
    // 랜덤으로 식량 중 1~3개를 잃고, 식량 이름과 잃은 개수를 반환함
    fun loseRandomFood(): Pair<String, Int> { // < 식량 객체, 잃은 개수 > 반환
        val selectedFood: Food = GameActivity.getRandomFood() // 랜덤 식량
        var loseFoodCount: Int = selectedFood.count // 잃은 식량의 개수를 저장하기 위한 변수
        selectedFood.loseFoodRandom(1,3) // 해당 식량이 0개인 경우 loseFoodRandom 내부에서 0개로 조정됨. 변경사항 없음.
        loseFoodCount -= selectedFood.count // 잃은 식량 개수
        return Pair(selectedFood.nameKorFood, loseFoodCount)
    }
    // 랜덤으로 안에 있는 멤버 중 한명이 다치고, 멤버 이름을 반환함
    fun hurtRandomMember(): String {
        val selectdeMember: Member = GameActivity.getRandomMemberFromListIsIn()
        selectdeMember.stateChangeHurt()
        return selectdeMember.nameKor
    }

    /* 각 이벤트에 대한 호출 동작 정의 */
    private fun event_invasion_robbery_effect() { // 침입 - 강도
        /*
        * < 시나리오 설명 >

        * # case1 -- 아이템, 식량이 있었고 둘 다 잃은 경우 postScript --
        * 문을 열어주자 보인 사람들은 무장한 강도였다. 우리는 강도에게 당했다.
        * (아이템 이름)을(를) 잃었습니다.
        * (식량 이름)을(를) (개수)개 잃었습니다.
        *
        * # case2 -- 아이템, 식량 중 한 종류만 잃은 경우 --
        * 문을 열어주자 보인 사람들은 무장한 강도였다. 우리는 강도에게 당했다.
        * + 아이템 혹은 식량을 잃었다는 문구 1개만 뜸
        *
        * # case 3 -- 아이템, 식량 모두 잃지 않은 경우 --
        * 문을 열어주자 보인 사람들은 무장한 강도였다. 우리는 강도에게 당했다.
        * 그러나 우리에게 가진 것이 별로 없다는 걸 안 강도들은, 다행히 그 누구도 해치지 않고 돌아갔습니다. 또한 그들은 그 무엇도 가져가지 않았습니다.
        *
        * < 고민해봐야 할 점 >
        * 아이템이 없고, 김밥 3개, 물 0개인 경우 무작위 식량 선택에서 물이 선택되면, 강도들이 아무것도 가지지 않고 돌아감
        * case 3에 해당함.
        * 이런 경우 가지고 있는 식량을 가져가게 할지, 아니면 그냥 넘어가게 할지?
        */
        var tmp: Int = 1 // if문 작성을 위해 임의로 생성한 변수.
        if(tmp == 1) { /* - 문을 열어준 경우 - */
            /* 가지고 있는 아이템 중, 랜덤으로 하나를 잃음 */
            val itemName: String = loseRandomItem()
            /* 김밥, 물 중 하나를 골라 랜덤으로 1~3개를 잃음 */
            val (foodName, loseFoodCount) = loseRandomFood()
            /* postScript 수정 */
            if(itemName != "") addPostScriptLoseItem(itemName)
            if(loseFoodCount != 0) addPostScriptLoseFood(foodName, loseFoodCount)
            if(itemName == "" && loseFoodCount == 0) // 아이템, 식량
                addPostScriptLoseNothing("그러나 우리에게 가진 것이 별로 없다는 걸 안 강도들은, 다행히 그 누구도 해치지 않고 돌아갔습니다. 또한 그들은 그 무엇도 가져가지 않았습니다.\n")
        } else { /* - 문을 열어주지 않은 경우 - */
            setPostScriptNull() // postScript를 ""로 세팅
        }
    } private fun event_invasion_grandma_effect() { // 침입 - 할머니
        var tmp: Int = 1 // if문 작성을 위해 임의로 생성한 변수.
        if (tmp == 1) { /* - 문을 열어준 경우 - */
            /* 안에 있는 멤버중 랜덤으로 1명이 다침 */
            val memberName = hurtRandomMember()
            /* 가지고 있는 아이템 중, 랜덤으로 하나를 잃음 */
            val itemName: String = loseRandomItem()
            /* postScript 수정 */
            if(itemName != "") addPostScriptLoseItem(itemName)
            addPostScriptHurtMamber(memberName)
        } else { /* - 문을 열어주지 않은 경우 - */
            setPostScriptNull() // postScript를 ""로 세팅
        }
    } private fun event_invasion_firefighter_effect() { // 침입 - 소방관
        var tmp: Int = 1 // if문 작성을 위해 임의로 생성한 변수.
        if (tmp == 1) { /* - 문을 열어준 경우 - */
            /* 안에 있는 멤버 중 랜덤으로 한명이 다침 */

        } else { /* - 문을 열어주지 않은 경우 - */

        }
    } private fun evnet_plunder_oldMan() { // 약탈 - 노인
        // event3에 대한 동작 정의
        // 예: 특별한 이펙트 발동, 게임 진행 상태 변경 등
    } private fun evnet_plunder_b1cr() { // 약탈 - 1호관 동아리방
        // event3에 대한 동작 정의
        // 예: 특별한 이펙트 발동, 게임 진행 상태 변경 등
    } private fun evnet_plunder_nursingHome() { // 약탈 - 요양원
        // event3에 대한 동작 정의
        // 예: 특별한 이펙트 발동, 게임 진행 상태 변경 등
    } private fun evnet_plunder_Lab() { // 약탈 - 연구실
        // event3에 대한 동작 정의
        // 예: 특별한 이펙트 발동, 게임 진행 상태 변경 등
    }

    // 스크립트 설정 메소드
    fun setPreScript(str: String) { preScript = str }
    fun setPostScript(str: String) { postScript = str }
    fun setPostScriptNull() { postScript = "" } // 아무일도 일어나지 않을 경우 호출
    fun addPostScriptLoseNothing(str: String) { postScript += str }
    fun addPostScriptLoseItem(itemName: String) { postScript += itemName + "을(를) 잃었습니다.\n" }
    fun addPostScriptLoseFood(foodName: String, cnt: Int) { postScript += foodName + "을(를) " + cnt.toString() +"개 잃었습니다.\n" }
    fun addPostScriptHurtMamber(memberName: String) { postScript += memberName + "이(가) 다쳤습니다.\n" }
}