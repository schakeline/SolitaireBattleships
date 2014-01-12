import org.scalatest.FlatSpec
import org.scalatest.Matchers
import de.htwg.scala.solitairebattleship.model._
import de.htwg.scala.solitairebattleship.util.Position
import de.htwg.scala.solitairebattleship.util.Orientation
import de.htwg.scala.solitairebattleship.model.Grid

package de.htwg.scala.solitairebattleship.controller {

	class GameGeneratorSpec extends FlatSpec with Matchers {
	
		"Place 2 Ships of size 1 in a Grid(3)" should "be possible" in {
		  val shipList = new Ship(1,2) :: new Ship(2,1) :: Nil
		  val g = new GameGenerator(shipList,3)
		  var game = g.generateGrid() 
		  game.size should be(3)
		}
				
		"Place 4 ships of size 1 in a Grid(3)" should "create a Game with one ship in each corner" in {
		  val ships = new Ship(0,1) :: new Ship(1,1) ::new Ship(2,1) :: new Ship(3,1)::Nil
		  val gen = new GameGenerator(ships,3)
		  val game =  gen.generateGrid
		  
		  game.getCell(0, 0) shouldNot be(None)
		  game.getCell(2, 0) shouldNot be(None)
		  game.getCell(0, 2) shouldNot be(None)
		  game.getCell(2, 2) shouldNot be(None)
		}
		
		"Place a ship of size 4 in a Grid(4)" should "no be possible" in {
		  val ships = new Ship(0,4) ::Nil
		  val gen = new GameGenerator(ships,3)
		  an [Exception] should be thrownBy  gen.generateGrid  		  
		}
		
		"Place 5 ships of size 1 in a Grid(3)" should "not be possible" in {
		  val ships = new Ship(0,1) :: new Ship(1,1) ::new Ship(2,1) :: new Ship(3,1)::new Ship(4,1)::Nil
		  val gen = new GameGenerator(ships,3)
		 
		  an [Exception] should be thrownBy gen.generateGrid
		  
		}
		
		
	}

} // package