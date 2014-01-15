package de.htwg.scala.solitairebattleship.model

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import de.htwg.scala.solitairebattleship.util.Observer

class ModelSpec extends FlatSpec with Matchers {
  "The Model " should "notifiy the observers" in {
    val obs = new ObserverMock()
    Model.add(obs)
    Model.game_=(null)

    obs.i should be(1)
  }

}

class ObserverMock extends Observer {
  var i = 0

  def update() = { i = i + 1 }
}