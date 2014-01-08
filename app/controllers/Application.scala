package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import de.htwg.scala.solitairebattleship.controller.GameController
import de.htwg.scala.solitairebattleship.util.Orientation
import de.htwg.scala.solitairebattleship.model.Model
import de.htwg.scala.solitairebattleship.model.IGame
import de.htwg.scala.solitairebattleship.model.exception.ShipCollisionException
import de.htwg.scala.solitairebattleship.util.Observer
import de.htwg.scala.solitairebattleship.view.IView


case class PlaceShipData(id: Int, x: String, y:String, orientation:String)

object Application extends Controller with Observer with IView {

  Model.add(this) // listen to model
  val gameController = new GameController
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

  var model:IGame = Model.game
  var showValidation:Boolean = false
  var gameFinish:Boolean = false
  var errorMsg:Option[String] = None


  def index = Action {
    Ok(views.html.index(newGameForm, possibleGridSizes))
  }

  def newGame = Action { implicit request =>
    newGameForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(errors, possibleGridSizes)),
      size => {
        //Model create game
        gameController.newGame(size)
        Redirect("/playGame")//Ok( views.html.playGame(model, placeShipForm, removeShipForm, None, false) )
      }
    )
  }

  def playGame = Action {
    var tmpErrorMsg = errorMsg
    errorMsg = None // reset global errorMsg
    Ok( views.html.playGame(model, placeShipForm, removeShipForm, tmpErrorMsg, showValidation) )
  }

  def placeShip = Action {
    implicit request =>
      placeShipForm.bindFromRequest.fold(
        formWithErrors => {
          BadRequest(views.html.playGame(model, placeShipForm, removeShipForm, Some("Bad Request"), false))
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
            Redirect("/gameFinished") //Ok(views.html.gameFinished())
          } else {
            Redirect("/playGame")//Ok( views.html.playGame(model, placeShipForm, removeShipForm, errorMsg, showValidation) )
          }
        }
      )
  }

  def removeShip = Action { implicit request =>
    removeShipForm.bindFromRequest.fold(
      errors => BadRequest(views.html.playGame(model, placeShipForm, removeShipForm, Some("Illegal argument"), false)),
      id => {
        //Model create game
        //var errorMsg:Option[String] = None
        try {
          gameController.removeShip(id)
        }
        catch {
          case e:IllegalArgumentException => 
            errorMsg = Some("Illegal argument")
            BadRequest( views.html.playGame(model, placeShipForm, removeShipForm, errorMsg, false) )
        }
        Redirect("/playGame")
      }
    )
  }

  def gameFinished = Action {
    Ok(views.html.gameFinished())
  }

  // Observer methods
  def update {
    model = Model.game
    showValidation = false
  }


  // IView methods
  def solution = Action {
    Ok(views.html.showSolution(model.solution))
  }

  def showSolution {

  }

  def showValidationResult {
    showValidation = true
  }

  def showGameFinished {
    gameFinish = true
  }

}