package de.htwg.scala.solitairebattleship.model

import de.htwg.scala.solitairebattleship.view.IView

trait IBattleship {
	
  var _genGrid:Grid
  var _userGrid:Grid
  var _ships:List[Ship]
  
  def genGrid = _genGrid
  def genGrid_(grid:Grid) = {
    _genGrid = grid
    update
  }
  
  def userGrid = _userGrid
  def userGrid_(grid:Grid) = {
    _userGrid = grid
    update
  }
  
  def ships = _ships
  def ships_(theShips:List[Ship]) = {
    _ships = theShips
    update
  }
  
  
  
  // Observer pattern
  private var observers:List[IView] = Nil
  
  def addObserver(theView:IView) = {observers = theView :: observers}
  def rmObserver(theView:IView) = {observers = observers.filterNot(p => p eq theView)}
  private def update = {observers.foreach(f => f.receiveUpdate)}
}