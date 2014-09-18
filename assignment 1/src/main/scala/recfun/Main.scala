package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == -1 || c == r + 1)
      0
    else if (r == 0)
      1
    else
      pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {

    def recursiveBalance(chars : List[Char], opening : Int, closing : Int) : Boolean = {
      if (closing > opening)
        false
      else if (chars.isEmpty)
        closing == opening
      else
        chars.head match {
          case '(' => recursiveBalance(chars.tail, opening + 1, closing)
          case ')' => recursiveBalance(chars.tail, opening, closing + 1)
          case _ => recursiveBalance(chars.tail, opening, closing)
        }
    }

    recursiveBalance(chars, 0, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {

  }
}

