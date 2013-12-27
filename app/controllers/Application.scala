package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import de.htwg.scala.solitairebattleship.controller.GameController
import de.htwg.scala.solitairebattleship.util.Orientation
import de.htwg.scala.solitairebattleship.model.Model

case class PlaceShipData(id: Int, x: String, y:String, orientation:String)

object Application extends Controller {

  val gameController = new GameController

  val newGameForm = Form( "size" -> number(min=3, max=10) )
  
  val placeShipForm = Form( 
    mapping(
      "id" -> number(min=0, max=10), //gameController.model.userGrid.size
      "x" -> text, //(min=0, max=10), 
      "y" -> text, //(min=0, max=10), 
      "orientation" -> text)
    (PlaceShipData.apply)
    (PlaceShipData.unapply)
  )

  val possibleGridSizes = (3 to 10).map(i =>i.toString).toList

  

  def index = Action {
    Ok(views.html.index(newGameForm, possibleGridSizes))
  }

  def playGame = Action { implicit request =>
    newGameForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(errors, possibleGridSizes)),
      size => {
        //Model create game
        gameController.newGame(size)
        Ok( views.html.playGame(Model.game, placeShipForm) )
      }
    )
  }

  def placeShip = Action {
    implicit request =>
      placeShipForm.bindFromRequest.fold(
        formWithErrors => {
          BadRequest(views.html.playGame(Model.game, placeShipForm))
        },
        placeShipData => {
          val orientation = if(placeShipData.orientation == "Horizontal") Orientation.Horizontal else Orientation.Vertical
          val x:Int = placeShipData.x(0).toInt-65
          val y:Int = placeShipData.y(0).toInt-65
          gameController.placeShip(placeShipData.id, x, y, orientation)
          println("OK")
          Ok( views.html.playGame(Model.game, placeShipForm) )
        }
      )
  }

}