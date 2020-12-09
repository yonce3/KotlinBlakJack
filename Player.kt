package com.kotlin_blackjack

class Player() {
    var point = 0

    fun draw(card: Card) {
        println("あなたの引いたカードは、${card.kind}の${card.num}です")
        point += card.getPoint()
    }

    fun getPoint() {
        println("あなたの現在の得点は、${this.point}です")
    }
}