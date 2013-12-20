package de.htwg.scala.solitairebattleship.model

import de.htwg.scala.solitairebattleship.util.Observable

object Model extends Observable {

  private var _game:Game = null


  def game_=(game:Game) {
    _game = game
    notifyObservers
  }

  def game:Game = _game
  
  /*private var _model:IGame = new Game
  private def model_=(model:IGame) {_model = model}
  def model = _model
  */
}