package com.kotlin_blackjack

fun main(args: Array<String>) {
    println("ブラックジャックへようこそ")
    var player = Player()
    val delear = Delear()
    var deck = createDeck()

    // 最初のドロー
    player.firstDraw(deck)

    // ディーラーの最初のドロー
    delear.firstDraw(deck)

    // プレイヤーのドロー
    var finishFlag = false
    while(!finishFlag) {
        player.printPoint()
        if (player.point <= 21) {
            if (player.checkDraw()) {
                player.draw(deck.elementAt(0))
                deck.removeAt(0)
            } else {
                finishFlag = true
            }
        } else {
            println("あなたの得点が、21を超えたのでゲームオーバーです。")
            exit
        }
    }

    // ディーラーのドロー
    delear.showSecondCard()
    while (delear.point <= 17) {

    }
}

fun createDeck(): ArrayList<Card> {
    var deck = ArrayList<Card>()
    for (kind in Kinds.values()) {
        for (i in 1..13) {
            deck.add(Card(kind.kind, i))
        }
    }
    // return deck.shuffled(java.util.Random(0))
    return deck
}


class Deck(var cardList: Array<Card>) {
}

class Player() {
    var point = 0
    var hand = ArrayList<Card>()

    fun draw(card: Card) {
        println("あなたの引いたカードは、${card.kind}の${card.num}です")
        hand.add(card)
        point += card.getPoint()
    }

    fun firstDraw(deck: ArrayList<Card>) {
        for(i in 0..1) {
            this.draw(deck.elementAt(0))
            deck.removeAt(0)
        }
    }

    fun printPoint() {
        println("あなたの現在の得点は、${this.point}です")
    }

    fun checkDraw(): Boolean {
        println("カードを引きますか？ 引く場合は Y を、引かない場合は N を入力してください。")
        var result = false
        var flag = false
        while(!flag) {
            val input = readLine()
            if (input == "Y") {
                result = true
                flag = true
            } else if (input == "N") {
                result = false
                flag = true
            } else {
                println("Y か Nで入力してください。")
            }
        }
        return result
    }
}

class Delear() {
    var point = 0
    var hand = ArrayList<Card>()
    var drawCount = 0

    fun draw(card: Card) {
        if (drawCount != 1) {
            println("ディーラーの引いたカードは、${card.kind}の${card.num}です。")
        } else {
            println("ディーラーの2枚目のカードは分かりません。")
        }
        drawCount += 1
        hand.add(card)
        point += card.getPoint()
    }

    fun firstDraw(deck: ArrayList<Card>) {
        for(i in 0..1) {
            this.draw(deck.elementAt(0))
            deck.removeAt(0)
        }
    }

    fun showSecondCard() {
        println("ディーラーの2枚目のカードは、${hand[1].kind}の${hand[1].num}でした。")
        println("ディーラーの現在の得点は、${this.point}です。")
    }
}

class Card(val kind: String, val num: Int) {
    fun getPoint(): Int {
        var result = 0
        when(this.num) {
            1 -> result = 1
            11, 12, 13 -> result = 10
            else -> result = this.num
        }
        return result
    }
}

enum class Kinds(val kind: String) {
    DIAMOND("ダイヤ"),
    HEART("ハート"),
    SPADE("スペード"),
    CLUB("クラブ")
}
