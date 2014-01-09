package controller

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import de.htwg.scala.solitairebattleship.view.IView
import de.htwg.scala.solitairebattleship.util.Orientation
import de.htwg.scala.solitairebattleship.controller.GameController
import de.htwg.scala.solitairebattleship.model.Model

class GameControllerSpec extends FlatSpec with Matchers {

	"Creating a game" should " check the size" in {
	  val cont = new GameController	  
	  an [Exception] should be thrownBy cont.newGame(2)
	  an [Exception] should be thrownBy cont.newGame(11)
	  
	  var g = cont.newGame(5)
	  g.!==(null) should be(true)
	}

	"place a Ship" should "update the Views"in {
	  val view = new ViewMock
	  val controller = new GameController
	  controller.registerView(view)
	  controller.newGame(3)
	  
	  controller.placeShip(0, 1, 1, Orientation.Horizontal)
	  
	  view.finished should be(0)
	  view.validation should be(0)
	  view.solution should be(0)
	  
	  controller.showSolution
	  view.solution should be(1)
	} 
	
	"Remove a Ship" should "Change the number of unplaced Ships" in{
	  val controller = new GameController
	  controller.newGame(3)
	  controller.placeShip(0, 0, 0, Orientation.Horizontal)
	  
	  val before = Model.game.getUnplacedShips.size
	  controller.removeShip(0)
	  Model.game.getUnplacedShips.size should be(before + 1)
	}
}


class ViewMock extends IView{
  var finished = 0 
  var validation = 0
  var solution = 0
  
  def showGameFinished = {finished += 1 }
  def showValidationResult = {validation += 1}
  def showSolution = {solution += 1}
}