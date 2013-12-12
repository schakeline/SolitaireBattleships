package de.htwg.scala.solitairebattleship

import de.htwg.scala.solitairebattleship.controller.GameController
import de.htwg.scala.solitairebattleship.view.TUI
import de.htwg.scala.solitairebattleship.view.GUI


object SolitaireBattleship {
  
  def main (args:Array[String]) = {
    
    // init controller
    val controller = new GameController

    //start GUI
    val gui = new GUI(controller)
    gui.open
    
    // start TUI
    /*val tui = new TUI(controller)
    
    // start input from tui
    do {
      print("> ")
    }
    while (tui.processUserInput(readLine()))*/
  }
}