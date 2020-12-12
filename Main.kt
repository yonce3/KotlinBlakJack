package com.kotlin_blackjack

fun main(args: Array<String>) {
    println("ブラックジャックへようこそ")
    var player = Player()
    val delear = Delear()
    var deck = createDeck()

    var gameOver = false

    // 最初のドロー
    player.firstDraw(deck)

    // ディーラーの最初のドロー
    delear.firstDraw(deck)

    // プレイヤーのドロー
    var finishFlag = false
    while(!finishFlag) {
        player.showPoint()
        if (player.point < 21) {
            if (player.checkDraw()) {
                player.draw(deck.elementAt(0))
                deck.removeAt(0)
            } else {
                finishFlag = true
            }
        } else if(player.point == 21) {
            println("あなたの得点が、21点になりました。")
            finishFlag = true
        } else {
            println("あなたの得点が、21を超えたのでゲームオーバーです。")
            finishFlag = true
            gameOver = true
        }
    }


    if (!gameOver) {
        // ディーラーのドロー
        delear.showSecondCard()
        delear.showPoint()
        while (delear.point <= 17) {
            delear.draw(deck.elementAt(0))
            deck.removeAt(0)

            if (delear.point > 21) {
                println("ディーラーの得点が、21を超えたのであなたの勝ちです。")
                gameOver = true
            }
        }
    }

    if (!gameOver) {
        // 勝負
        check(player, delear)
    }
}

fun createDeck(): MutableList<Card> {
    var deck = mutableListOf<Card>()
    for (kind in Kinds.values()) {
        for (i in 1..13) {
            deck.add(Card(kind.kind, i))
        }
    }
    return deck.shuffled().toMutableList()
}

fun check(player: Player, delear: Delear) {
    println("あなたの得点は、${player.point}です。")
    println("ディーラーの得点は、${delear.point}です。")

    if (player.point > delear.point) {
        println("あなたの勝ちです!")
    } else if (player.point < delear.point) {
        println("ディーラーの勝ちです！")
    } else {
        println("引き分けです！")
    }

    println("お疲れ様でした。")
}

class Player() {
    var point = 0
    var hand = ArrayList<Card>()

    fun draw(card: Card) {
        println("あなたの引いたカードは、${card.kind}の${card.showNum()}です")
        hand.add(card)
        point += card.getPoint()
    }

    fun firstDraw(deck: MutableList<Card>) {
        for(i in 0..1) {
            this.draw(deck.elementAt(0))
            deck.removeAt(0)
        }
    }

    fun showPoint() {
        println("あなたの現在の得点は、${this.point}です")
    }

    fun checkDraw(): Boolean {
        println("カードを引きますか？ 引く場合は Y を、引かない場合は N を入力してください。")
        var result = false
        var flag = false
        while(!flag) {
            val input = readLine()
            if (input == "Y" || input == "y") {
                result = true
                flag = true
            } else if (input == "N" || input == "n") {
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
            println("ディーラーの引いたカードは、${card.kind}の${card.showNum()}です。")
        } else {
            println("ディーラーの2枚目のカードは分かりません。")
        }
        drawCount += 1
        hand.add(card)
        point += card.getPoint()
    }

    fun firstDraw(deck: MutableList<Card>) {
        for(i in 0..1) {
            this.draw(deck.elementAt(0))
            deck.removeAt(0)
        }
    }

    fun showPoint() {
        println("ディーラーの現在の得点は、${this.point}です。")
    }

    fun showSecondCard() {
        println("ディーラーの2枚目のカードは、${hand[1].kind}の${hand[1].num}でした。")
    }
}

class Card(val kind: String, val num: Int) {
    fun getPoint(): Int {
        when(this.num) {
            1 -> return this.selectAce()
            11, 12, 13 -> return 10
            else -> return this.num
        }
    }

    fun showNum(): String {
        when (this.num) {
            1 -> return "A"
            11 -> return "J"
            12 -> return "Q"
            13 -> return "K"
            else -> return "${this.num}"
        }
    }

    fun selectAce(): Int {
        println("Aceを引いたので、1か11を選択できます")
        println("1を選ぶ場合は、Y 11を選ぶ場合は、N を入力してください。")
        val input = readLine()
        var result = 0
        var finishFlag = false

        while (!finishFlag) {
            if (input == "Y" || input == "y") {
                result = 1
                finishFlag = true
            } else if (input == "N" || input == "n") {
                result = 11
                finishFlag = true
            } else {
                println("Y か Nで入力してください。")
            }
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
