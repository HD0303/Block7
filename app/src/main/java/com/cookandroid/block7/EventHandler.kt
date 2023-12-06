package com.cookandroid.block7

import android.content.Context
import kotlin.random.Random

// EventHandler : 이벤트 객체 생성, 확률 조정 등
class EventHandler(GameActivity: GameActivity) {
    var GameActivity: GameActivity = GameActivity

    // event_invasion. : 침입 이벤트
    // evnet_plunder : 약탈 이벤트1호관

    /* 이벤트 객체 생성 */
    // 침입 - 강도
    var event_invasion_robbery = Event(GameActivity, "event_invasion_robbery", 1, true)
    // 침입 - 할머니
    var event_invasion_grandma = Event(GameActivity, "event_invasion_grandma", 1, true)
    // 침입 - 소방관
    var event_invasion_firefighter = Event(GameActivity, "event_invasion_firefighter", 1, true)
    // 약탈 - 노인
    var evnet_plunder_oldMan = Event(GameActivity, "evnet_plunder_oldMan", 1, true)
    // 약탈 - 1호관 동아리방
    var evnet_plunder_b1cr = Event(GameActivity, "evnet_plunder_b1cr", 1, true)
    // 약탈 - 요양원
    var evnet_plunder_nursingHome = Event(GameActivity, "evnet_plunder_nursingHome", 1, true)
    // 약탈 - 연구실
    var evnet_plunder_Lab = Event(GameActivity, "evnet_plunder_oldMan", 1, true)

    /* 모든 이벤트 리스트 - 이벤트 객체 생성 시 추가됨 (Event.init에서 추가됨) */
    var events = mutableListOf<Event>()

    /* 가중치를 기반으로 랜덤으로 이벤트 선택 및 실행 */
    fun executeRandomEvent() {
        // do-while 루프를 통해 선택된 이벤트 객체가 실행 불가능한 경우 다시 뽑도록 함
        // isAvailable가 true인 이벤트 객체가 뽑힐 때까지 실행
        var selectedEvent: Event

        do {
            selectedEvent = selectRandomEvent()
        } while (!selectedEvent.isAvailable)

        selectedEvent.executeEventEffect()
    }

    // 가중치를 기반으로 랜덤으로 이벤트 선택
    private fun selectRandomEvent(): Event {
        val totalWeight = events.sumBy { it.weight }

        var randomValue = Random.nextInt(totalWeight)
        var selectedEvent: Event = events.first()

        for (event in events) {
            if (randomValue < event.weight) {
                selectedEvent = event
                break
            }
            randomValue -= event.weight
        }

        return selectedEvent
    }

    /* 이벤트 스크립트 설정 */
    fun setEventScript() {
        event_invasion_robbery.setPreScript( "누군가 문을 두드린다. 열어주는게 좋을까?\n" )
        event_invasion_robbery.setPostScript( "문을 열어주자 보인 사람들은 무장한 강도였다. 우리는 강도에게 당했다.\n" )
        event_invasion_grandma.setPreScript("누군가 문을 두드린다. 열어주는게 좋을까?\n")
        event_invasion_grandma.setPostScript("문을 열어주지 보인 사람은 다름 아닌 한 할머니였다. 의아해하던 와중, 할머니는 급변해 우리를 공격해왔다.")
    }
}
