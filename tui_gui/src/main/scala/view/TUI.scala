package de.htwg.scala.solitairebattleship.view

import de.htwg.scala.solitairebattleship.util.Observer
import de.htwg.scala.solitairebattleship.controller.GameController
import de.htwg.scala.solitairebattleship.model._
import de.htwg.scala.solitairebattleship.util.Orientation._
import java.lang.NumberFormatException
import de.htwg.scala.solitairebattleship.model.exception.ShipCollisionException

class TUI(val controller:GameController) extends Observer with IView {

  private val askForGridSize = "Please enter: n <size [3, 10]> [new game], q [quit]."
  private val inputCommands = "Enter q [quit], n <size [3,10]> [new game], s [show solution], mv <id> <y> <x> <orientation> [move ship], rm <id> [remove ship]"
  
  Model.add(this) // listen to model
  controller.registerView(this) // register view
  printTitle
  update
  

  def showGameFinished {
    printGrid(Model.game.gameGrid, true)
    printGratualtion
  }
  def showValidationResult {
    printGrid(Model.game.gameGrid, true)
    printInfo(inputCommands)
  }

  def showSolution {
    println(colorYellow("\n- - - SOLUTION - - -\n"))
    printGrid(Model.game.solution)
    println("")
  }

  def update {
    if (Model.game != null) {
      printGrid(Model.game.gameGrid)
      printUnplacedShips
      printInfo(inputCommands)
    } else {
      printInfo(askForGridSize)
    }
  }

  /*
      A B C D 
    A|2|~|1|~|2
    B|~|~|~|~|0
    C|~|~|0|~|1
    D|~|~|0|~|1
      1 0 3 0 
   */
  private def printGrid(g:IGrid, showValidation:Boolean = false) {
    
    var gridStr = "  " + fieldIndexRow(g.size) + "\n"
    gridStr += gridRows(g, showValidation)
    gridStr += "  " + columnSumRow(g, showValidation)
    
    println(gridStr)
  }

  private def fieldIndexRow(gridSize:Int) = {
    var labels = ""
    for (x <- 0 until gridSize) {
      labels += ((x+65).toChar + " ")
    }
    labels
  }

  private def gridRows(g:IGrid, showValidation:Boolean) = {
    
    var collisions:List[Tuple2[Int,Int]] = Nil
    if (showValidation) collisions = Model.game.getCollisions

    var rows = ""
    var size = g.size
    for (y <- 0 until size) {
      for (x <- 0 until size+2) {
        var field:String =
          x match {
          case 0 => ((y+65).toChar + "|")
          case x if (x == size+1) => getColoredRowSum(y, g.getRowSum(y), showValidation) + "\n"
          case _ => {
            var s:Ship = g.getCell(x-1, y)
            if (s == null) "~|"
            else if (collisions.contains((x-1,y))) colorRed(s.id.toString) + "|"
            else s.id + "|"
          }
        }
        rows += field
      }
    }
    rows
  }

  private def columnSumRow(g:IGrid, showValidation:Boolean) = {
    var sums = ""
    for (x <- 0 until Model.game.gameGrid.size) {
      sums += (getColoredColumnSum(x, g.getColumnSum(x), showValidation) + " ")
    }
    sums
  }
  
  private def getColoredRowSum(row:Int, sum:Int, showValidation:Boolean) = {
    if (showValidation) {
      if (Model.game.validateRowSum(row)) colorGreen(sum.toString)
      else colorRed(sum.toString)
    }
    else sum.toString
  }

  private def getColoredColumnSum(col:Int, sum:Int, showValidation:Boolean) = {
    if (showValidation) {
      if (Model.game.validateColumnSum(col)) colorGreen(sum.toString)
      else colorRed(sum.toString)
    }
    else sum.toString
  }

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

  

  def processUserInput(in:String) = {
    var continue = true

    var input = in.toUpperCase

    try {
      input match {
      case "Q" => continue = false
      case "S" => controller.showSolution
      case x if (x.matches("N [0-9]{1,2}")) => {
        controller.newGame(x.slice(2,x.length).toInt)
      }
      case _ => {
        if (Model.game != null) {
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
          case _ => printError("Wrong Input!")
          }
        } else {
        printError(askForGridSize)
        }
      }
      }
    }
    catch {
      case e:IllegalArgumentException =>
        printError("Illegal argument")
      case e:IndexOutOfBoundsException =>
        printError("Index out of bounds.")
      case e:ShipCollisionException =>
        printError("ShipCollition: " + e.getMessage)
    }

    continue
  }


  private def printError(msg:String) { println(colorRed("[ERROR] ") + msg) }
  private def printInfo(msg:String) { println(colorBlue("[INFO] ") + msg) }

  private def printTitle {
    scala.io.Source.fromURL(getClass.getResource("/title.txt")).getLines.foreach(s => println(colorYellow(s)))    
  }

  private def printGratualtion {
    scala.io.Source.fromURL(getClass.getResource("/gratulation.txt")).getLines.foreach(s => println(colorGreen(s)))    
  }

  private def colorRed(s:String) = {Console.RED + s + Console.RESET}
  private def colorGreen(s:String) = {Console.GREEN + s + Console.RESET}
  private def colorYellow(s:String) = {Console.YELLOW + s + Console.RESET}
  private def colorBlue(s:String) = {Console.BLUE + s + Console.RESET}

}
