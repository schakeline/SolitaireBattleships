package de.htwg.scala.solitairebattleship.view

import de.htwg.scala.solitairebattleship.util.Observer
import de.htwg.scala.solitairebattleship.controller.GameController
import de.htwg.scala.solitairebattleship.model.Battleship
import de.htwg.scala.solitairebattleship.model.Grid
import de.htwg.scala.solitairebattleship.model.Ship
import de.htwg.scala.solitairebattleship.util.Orientation._
import java.lang.NumberFormatException

class TUI(val controller:GameController) extends Observer { // extends IView
  var model:Battleship = controller.model
  // listenTo model
  model.add(this)
  askForGridSize
  

  def update = {printTUI}

  private def printTUI = {
  	println("\n# SOLITAIRE BATTLESHIP \n")
  	printGrid()
  	println("")
  	printUnplacedShips
  	printInputCommands
  }

  /*
      A B C D E F G H I
  	A|~|~|~|~|~|~|~|~|~|0
  	B|+|~|+|+|~|~|~|+|+|3
  	C|+|~|~|~|~|~|~|~|~|1
  	D|~|~|#|#|#|#|~|~|~|4
  	E|~|~|~|~|~|~|~|~|~|4
  	F|~|~|~|~|~|~|~|~|~|4
  	G|~|~|X|X|X|X|~|~|~|4
  	H|~|~|~|~|~|~|~|~|~|4
	I|~|~|<|=|=|>|~|~|~|4
  	  2 0 2 2 1 1 0 1 1
   */
  private def printGrid() {
  	
  	var gridStr = "  " + fieldIndexRow(model.userGrid) + "\n"
  	gridStr += gridRows()
  	gridStr += "  " + columnSumRow(model.genGrid)
  	
  	println(gridStr)
  }

  private def fieldIndexRow(g:Grid) = {
  	var labels = ""
  	for (x <- 0 until g.size) {
  		labels += ((x+65).toChar + " ")
  	}
  	labels
  }

  private def gridRows() = {
  	var rows = ""
  	var size:Int = model.userGrid.size
  	for (y <- 0 until size) {
  		for (x <- 0 until size+2) {
  			var field:String =
  			  x match {
	  			case 0 => ((y+65).toChar + "|")
	  			case x if (x == size+1) => model.genGrid.getRowSum(y).toString + "\n"
	  			case _ => {
	  				var s:Ship = model.userGrid.getCell(x-1, y)
	  				if (s == null) "~|" else s.id+"|"
	  			}
  			}
  			rows += field
  		}
  	}
  	rows
  }

  private def columnSumRow(g:Grid) = {
  	var sums = ""
  	for (x <- 0 until g.size) {
  		sums += (g.getColumnSum(x).toString + " ")
  	}
  	sums
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
  	var ships:List[Ship] = model.getUnplacedShips
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
  	println("Enter q [Quit], n <size (2 < size < 11)> [new game], v [validate game], mv <id> <y> <x> <orientation> [move ship], rm <id> [remove ship]")
  }

  def processUserInput(in:String) = {
  	var continue = true

  	in match {
  		case "q" => continue = false
  		case "v" => println("controller.validate")
  		case "n" => askForGridSize
  		case _ => {
  			in.toList.filter(c => c != ' ') match {
  				case 'm' :: 'v' :: ship :: row :: column :: orientation :: Nil => {
  					controller.placeShip((ship.toInt - '0'.toInt),(row.toInt - 'A'.toInt), (column.toInt - 'A'.toInt), (if (orientation=='h') Horizontal else Vertical))
  				}
  				case 'r' :: 'm' :: ship :: Nil => {
  					controller.removeShip(ship.toInt - '0'.toInt)
  				}
  				case _ => println("Wrong Input!")
  			}
  		}
  	}
  	continue
  }

  private def askForGridSize = {
  	println("Please enter the desired gird size [3, 10].")
  	
  	var in:String = null;
  	do {
  		print("> ")
  		in = readLine()
  		var gSize = -1
  		try {
  			gSize = in.toInt
  			gSize match {
  			case x:Int if (x > 2 && x < 11) => controller.newGame(gSize)
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

}