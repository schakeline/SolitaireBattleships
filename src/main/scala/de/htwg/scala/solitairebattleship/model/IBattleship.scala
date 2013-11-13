package de.htwg.scala.solitairebattleship.model

import de.htwg.scala.solitairebattleship.view.IView

trait IBattleship {
	
  val genGrid:Grid
  val userGrid:Grid
  val ships:List[Ship]
  
  // Observer pattern
  private var observers:List[IView] = Nil
  
  def addObserver(theView:IView) = {observers = theView :: observers}
  def rmObserver(theView:IView) = {observers = observers.filterNot(p => p eq theView)}
}