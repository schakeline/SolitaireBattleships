package de.htwg.scala.solitairebattleship.view


import de.htwg.scala.solitairebattleship.controller.GameController
import de.htwg.scala.solitairebattleship.model.Battleship
import de.htwg.scala.solitairebattleship.model.Grid
import de.htwg.scala.solitairebattleship.model.Ship

class TUI(val controller:GameController) { // extends IView
  // listenTo controller
  
  // TODO: replace with user input
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
  	for (x <- 0 until g.size) {
  		labels += ((x+65).toChar + " ")
  	}
  	labels
  }

  private def gridRows(g:Grid) = {
  	var rows = ""
  	var size:Int = g.size
  	for (y <- 0 until size) {
  		for (x <- 0 until size+2) {
  			var field:String =
  			  x match {
	  			case 0 => ((y+65).toChar + "|")
	  			case x if (x == size+1) => g.getRowSum(y).toString + "\n"
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

}