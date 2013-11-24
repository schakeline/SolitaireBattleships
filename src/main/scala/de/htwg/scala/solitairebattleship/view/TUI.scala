package de.htwg.scala.solitairebattleship.view


import de.htwg.scala.solitairebattleship.controller.GameController
import de.htwg.scala.solitairebattleship.model.Battleship
import de.htwg.scala.solitairebattleship.model.Grid
import de.htwg.scala.solitairebattleship.model.Ship

class TUI(val controller:GameController) { // extends IView
  // listenTo controller
  
  var model:Battleship = controller.createGameWithSize(10)
  update

  def update = {printTUI}

  private def printTUI = {
  	println("\n# SOLITAIRE BATTLESHIP \n")
  	printGrid(model.userGrid)
  	println("")
  	printUnplacedShips
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
  private def printGrid(g:Grid) {
  	
  	var gridStr = "  " + fieldIndexRow(g) + "\n"
  	gridStr += gridRows(g)
  	gridStr += "  " + columnSumRow(g)
  	
  	println(gridStr)
  }

  private def fieldIndexRow(g:Grid) = {
  	var labels = ""
  	for (i <- 0 until g.size) {
  		labels += ((i+65).toChar + " ")
  	}
  	labels
  }

  private def gridRows(g:Grid) = {
  	var rows = ""
  	var size:Int = g.size
  	for (i <- 0 until size) {
  		for (j <- 0 until size+2) {
  			var field:String =
  			  j match {
	  			case 0 => ((i+65).toChar + "|")
	  			case x if (x == size+1) => g.getRowSum(i).toString + "\n"
	  			case _ => if (g.getCell(i, j-1) == null) "~|" else "X|"
  			}
  			rows += field
  		}
  	}
  	rows
  }

  private def columnSumRow(g:Grid) = {
  	var sums = ""
  	for (i <- 0 until g.size) {
  		sums += (g.getColumnSum(i).toString + " ")
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
  
}