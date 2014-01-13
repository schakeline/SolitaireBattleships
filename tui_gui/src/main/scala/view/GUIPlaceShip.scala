package de.htwg.scala.solitairebattleship.view;

import de.htwg.scala.solitairebattleship.util.Orientation
import de.htwg.scala.solitairebattleship.model.Model
import de.htwg.scala.solitairebattleship.model.Ship
import de.htwg.scala.solitairebattleship.controller.GameController
import de.htwg.scala.solitairebattleship.model.exception.ShipCollisionException


object GUIPlaceShip {

  var selected = -1
  var startPos: Tuple2[Int,Int] = (-1,-1)

  def selectShip(id:Int)=
  {
    reset
    selected = id
  }

  def setPos(x:Int, y:Int, controller:GameController):String = {
    if(selected < 0) return "no ship selected";
    if(startPos == (-1,-1)){
      startPos = (x,y)
      return "1. click x: " + x + " y: " + y
    }
    else{
      var start = getStart(startPos._1, startPos._2, x, y)
      var end = getEnd(startPos._1, startPos._2, x, y)
      var or = Orientation.Horizontal;
      val theShip = Model.game.get.getShipWithID(selected)

      if(start._1 == end._1)  {
        if(checkLength(start._2,end._2, theShip) == false){
          reset
          return "Length not matching"
        }
        or = Orientation.Vertical
      }
      else if(start._2 == end._2) {
        if(checkLength(start._1,end._1,theShip) == false) {
          reset
          return "Length not matching"
        }
        or = Orientation.Horizontal
      }
      else{
        reset
        return "diagonal  Placement is not possible"
      }
      
      try{
          println("ship" + theShip.id + "2. click x: " + x + " y: " + y)
          controller.placeShip(theShip.id, start._1, start._2, or)
        }catch {
          case e: ShipCollisionException => {
            reset
            return "Collision"
          }
          case e:Exception => return"exception caught: " + e;
        }
      reset
      return "2. click x: " + x + " y: " + y
    }
  }

  def reset() = {
    selected = -1;
    startPos = (-1,-1)
  }

  private def sortPositions(x1:Int, y1:Int,x2:Int,y2:Int):Tuple2[Tuple2[Int,Int],Tuple2[Int,Int]] = {
    if(x1 <= x2 && y1 <= y2 ){((x1,y1),(x2,y2))}
    else { ((x2,y2),(x1,y1))}
  }

  private def getStart(x1:Int, y1:Int,x2:Int,y2:Int):Tuple2[Int,Int] = {
    var tmp = sortPositions(x1, y1, x2, y2)
    tmp._1
  }

  private def getEnd(x1:Int, y1:Int,x2:Int,y2:Int):Tuple2[Int,Int] = {
    var tmp = sortPositions(x1, y1, x2, y2)
    tmp._2
  }

  private def checkLength(pos1:Int,pos2:Int,theShip:Ship):Boolean = {
    val length = Math.abs(pos1 - pos2) + 1
    if(length == theShip.size) true
    else false
  }
}
