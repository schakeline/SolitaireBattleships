package de.htwg.scala.solitairebattleship.view

import de.htwg.scala.solitairebattleship.model.IGame

trait IView {
  var model:IGame // setter: observer registrieren
  def receiveUpdate()
}