package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import de.htwg.scala.solitairebattleship.controller.GameController
import de.htwg.scala.solitairebattleship.util.Orientation
import de.htwg.scala.solitairebattleship.model.Model
import de.htwg.scala.solitairebattleship.model.IGame
import de.htwg.scala.solitairebattleship.model.IGrid
import de.htwg.scala.solitairebattleship.model.exception.ShipCollisionException
import de.htwg.scala.solitairebattleship.util.Observer
import de.htwg.scala.solitairebattleship.view.IView
import de.htwg.scala.solitairebattleship.view.GUIFactory
import de.htwg.scala.solitairebattleship.view.TUIFactory



case class PlaceShipData(id: Int, x: String, y:String, orientation:String)

object Application extends Controller with Observer with IView {

  val gameController = new GameController
  
  val gui = new GUIFactory(gameController)
  gui.start

  val tui = new TUIFactory(gameController)
  tui.start
  
  Model.add(this) // listen to model
  
  gameController.registerView(this)


  val newGameForm = Form( "size" -> number(min=3, max=10) )
  
  val placeShipForm = Form( 
    mapping(
      "id" -> number(min=0),
      "x" -> text,
      "y" -> text,
      "orientation" -> text)
    (PlaceShipData.apply)
    (PlaceShipData.unapply)
  )

  val removeShipForm = Form( "id" -> number(min=0) )

  val possibleGridSizes = (3 to 10).map(i =>i.toString).toList

  //var model:IGame = Model.game.get
  var showValidation:Boolean = false
  var gameFinish:Boolean = false
  var errorMsg:Option[String] = None
  var solutionGrid:Option[IGrid] = None


  def index = Action {
    Ok(views.html.index(newGameForm, possibleGridSizes))
  }

  def newGame = Action { implicit request =>
    newGameForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(errors, possibleGridSizes)),
      size => {
        //Model create game
        gameController.newGame(size)
        Redirect("/playGame")
      }
    )
  }

  def playGame = Action {
    var tmpErrorMsg = errorMsg
    errorMsg = None // reset global errorMsg
    
    solutionGrid match {
      case Some(s) => Redirect("/solution")
      case _ =>
        try {
         Ok( views.html.playGame(Model.game.get, placeShipForm, removeShipForm, tmpErrorMsg, showValidation) )
        }
        catch {
          case e:Exception => Redirect("/")
        }
    }
  }

  def placeShip = Action {
    implicit request =>
      placeShipForm.bindFromRequest.fold(
        formWithErrors => {
          BadRequest(views.html.playGame(Model.game.get, placeShipForm, removeShipForm, Some("Bad Request"), false))
        },
        placeShipData => {
          val orientation = if(placeShipData.orientation == "Horizontal") Orientation.Horizontal else Orientation.Vertical
          val x:Int = placeShipData.x(0).toInt-65
          val y:Int = placeShipData.y(0).toInt-65
          
          
          try {
            gameController.placeShip(placeShipData.id, x, y, orientation)
          }
          catch {
            case e:IllegalArgumentException =>
              errorMsg = Some("Illegal argument")
            case e:IndexOutOfBoundsException =>
              errorMsg = Some("Index out of bounds.")
            case e:ShipCollisionException =>
              errorMsg = Some("ShipCollition: " + e.getMessage)
          }
          
          if (gameFinish) {
            gameFinish = false
            Redirect("/gameFinished")
          } else {
            Redirect("/playGame")
          }
        }
      )
  }

  def removeShip = Action { implicit request =>
    removeShipForm.bindFromRequest.fold(
      errors => BadRequest(views.html.playGame(Model.game.get, placeShipForm, removeShipForm, Some("Illegal argument"), false)),
      id => {
        //Model create game
        try {
          gameController.removeShip(id)
        }
        catch {
          case e:IllegalArgumentException => 
            errorMsg = Some("Illegal argument")
            BadRequest( views.html.playGame(Model.game.get, placeShipForm, removeShipForm, errorMsg, false) )
        }
        Redirect("/playGame")
      }
    )
  }

  def gameFinished = Action {
    Ok(views.html.gameFinished())
  }

  // Observer methods
  def update { showValidation = false }


  // IView methods
  def solution = Action {
    // Do just call controller if showSolution was selected on this view
    if (solutionGrid == None) {
      gameController.showSolution  
    }

    val tmpSolution = solutionGrid.get
    solutionGrid = None // reset global show solution
    
    try {
      Ok( views.html.showSolution(tmpSolution) )
    }
    catch {
      case e:Exception => Redirect("/")
    }
  }

  def showSolution {
    solutionGrid = Some(Model.game.get.solution)
  }

  def showValidationResult {
    showValidation = true
  }

  def showGameFinished {
    gameFinish = true
  }

}