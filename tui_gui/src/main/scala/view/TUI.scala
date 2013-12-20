package de.htwg.scala.solitairebattleship.view

import de.htwg.scala.solitairebattleship.util.Observer
import de.htwg.scala.solitairebattleship.controller.GameController
import de.htwg.scala.solitairebattleship.model._
import de.htwg.scala.solitairebattleship.util.Orientation._
import java.lang.NumberFormatException

class TUI(val controller:GameController) extends Observer { // extends IView
  // listenTo model
  Model.add(this)
  askForGridSize
  

  def update {
    printTUI
  }

  private def printTUI {
    printGrid(Model.game.gameGrid)
    printUnplacedShips
    printInputCommands
  }

  /*
      A B C D E F G H I
    A|~|~|~|~|~|~|~|~|~|0
    B|1|~|2|2|~|~|~|+|+|3
    C|1|~|~|~|~|~|~|~|~|1
    D|~|~|3|3|3|3|~|~|~|4
    E|~|~|~|~|~|~|~|~|~|4
    F|~|~|~|~|~|~|~|~|~|4
    G|~|~|4|4|4|4|~|~|~|4
    H|~|~|~|~|~|~|~|~|~|4
    I|~|~|5|5|5|5|~|~|~|4
      2 0 2 2 1 1 0 1 1
   */
  private def printGrid(g:IGrid) {
    
    var gridStr = "  " + fieldIndexRow(g.size) + "\n"
    gridStr += gridRows(g)
    gridStr += "  " + columnSumRow(g)
    
    println(gridStr)
  }

  private def fieldIndexRow(gridSize:Int) = {
    var labels = ""
    for (x <- 0 until gridSize) {
      labels += ((x+65).toChar + " ")
    }
    labels
  }

  private def gridRows(g:IGrid) = {
    var rows = ""
    var size = g.size
    for (y <- 0 until size) {
      for (x <- 0 until size+2) {
        var field:String =
          x match {
          case 0 => ((y+65).toChar + "|")
          case x if (x == size+1) => getColoredRowSum(y, g.getRowSum(y)) + "\n"
          case _ => {
            var s:Ship = g.getCell(x-1, y)
            if (s == null) "~|" else s.id+"|"
          }
        }
        rows += field
      }
    }
    rows
  }

  private def columnSumRow(g:IGrid) = {
    var sums = ""
    for (x <- 0 until Model.game.gameGrid.size) {
      sums += (getColoredColumnSum(x, g.getColumnSum(x)) + " ")
    }
    sums
  }
  
  private def getColoredRowSum(row:Int, sum:Int) = {
    if (Model.game.getUnplacedShips.isEmpty) {
      if (Model.game.validateRowSum(row)) colorGreen(sum.toString) // FIXME: Validation
      else colorRed(sum.toString)
    }
    else sum.toString
  }

  private def getColoredColumnSum(col:Int, sum:Int) = {
    if (Model.game.getUnplacedShips.isEmpty) {
      if (Model.game.validateColumnSum(col)) colorGreen(sum.toString) // FIXME: Validation
      else colorRed(sum.toString)
    }
    else sum.toString
  }

  private def colorRed(s:String) = {Console.RED + s + Console.RESET}
  private def colorGreen(s:String) = {Console.GREEN + s + Console.RESET}

  /*
  ## Unplaced ships
  ### with length 1: <8>
  ### with length 2: <00> | <11> | <22> | <33>
  ### with length 3: <444> | <555> | <666>
  ### with length 4: <7777>
  */
  private def printUnplacedShips {
    var output = "## Unplaced ships"
    var ships:List[Ship] = Model.game.getUnplacedShips
    var tmpSize = 0
    for (s <- ships) {
      if (tmpSize != s.size){ 
        output += "\n### with size " + s.size + ": "
        tmpSize = s.size
      }
      else {
        output += "|"
      }
      output += s.toString
    }
    println(output)
  }

  private def printInputCommands {
    println("Enter q [quit], n [new game], v [validate game]" +
      "mv <id> <y> <x> <orientation> [move ship], rm <id> [remove ship]")
  }

  def processUserInput(in:String) = {
    var continue = true

    var input = in.toUpperCase

    input match {
      case "Q" => continue = false
      case "V" => println("controller.validate")
      case "N" => askForGridSize
      case _ => {
        input.toList.filter(c => c != ' ') match {
          case 'M' :: 'V' :: ship :: column :: row :: orientation :: Nil => {
            controller.placeShip( (ship.toInt - '0'.toInt),
              (row.toInt - 'A'.toInt),
              (column.toInt - 'A'.toInt),
              (if (orientation=='H') Horizontal else Vertical) )
          }
          case 'R' :: 'M' :: ship :: Nil => {
            controller.removeShip(ship.toInt - '0'.toInt)
          }
          case _ => println("Wrong Input!")
        }
      }
    }
    continue
  }

  private def askForGridSize = {
    printTitle
    println("Please enter the desired gird size [3, 10].")
    
    var in:String = null;
    do {
      print(Console.BLUE + "> " + Console.RESET)
      in = readLine()
      var gSize = -1
      try {
        gSize = in.toInt
        gSize match {
        case x:Int if (x > 1 && x < 11) => controller.newGame(gSize)
        case _ => {
          println("Wrong Input!")
          in = null
        }
        }
      } catch {
        case e:NumberFormatException =>
        println("[ERROR] Entered string is not a number.")
        in = null
      }

    } while (in == null)
  }

  private def printTitle {
    scala.io.Source.fromURL(getClass.getResource("/title.txt")).getLines.foreach(s => println(Console.YELLOW+s+Console.RESET))    
  }

}
