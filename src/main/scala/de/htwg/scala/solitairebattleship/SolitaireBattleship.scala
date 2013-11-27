package de.htwg.scala.solitairebattleship

import de.htwg.scala.solitairebattleship.controller.GameController
import de.htwg.scala.solitairebattleship.view.TUI


object SolitaireBattleship {
	
	def main (args:Array[String]) = {
	  
	  // init controller
	  val controller = new GameController

	  // start UIs
	  val tui = new TUI(controller)

	  // start input from tui
	  do {
	  	print("> ")
	  }
	  while (tui.processUserInput(readLine()))
	}
}