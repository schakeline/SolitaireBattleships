import org.scalatest.FlatSpec
import org.scalatest.Matchers
import de.htwg.scala.solitairebattleship.model._

package de.htwg.scala.solitairebattleship.controller {

	class GameGeneratorSpec extends FlatSpec with Matchers {
	
		"Place 2 Ships of size 1 in a Grid(3)" should "be possible" in {
		  val shipList = new Ship(1,1) :: new Ship(2,1) :: Nil
		  val g = new GameGenerator(shipList,3)
		  var game = g.generateGrid() 
		  game.size should be(3)
		}
		
		
	}

} // package