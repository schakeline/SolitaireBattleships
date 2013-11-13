package de.htwg.scala.solitairebattleship.view

import de.htwg.scala.solitairebattleship.model.IBattleship

trait IView {
  val model:IBattleship
  def receiveUpdate()
}