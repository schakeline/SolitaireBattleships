package de.htwg.scala.solitairebattleship.view

import de.htwg.scala.solitairebattleship.model.IBattleship

trait IView {
  var model:IBattleship // setter: observer registrieren
  def receiveUpdate()
}