package de.htwg.scala.solitairebattleship.view

import scala.swing._
import de.htwg.scala.solitairebattleship.model.Model;

class Cell(x: Int, y: Int, isEmpty: Boolean, theGUI: GUI) extends Button {
  val bSize = 50
  preferredSize = new Dimension(bSize, bSize)
  maximumSize = new Dimension(bSize, bSize)
  super.border = javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK)

  if (!isEmpty) {
    super.icon = new javax.swing.ImageIcon(getClass.getResource("/black.gif"))
    super.repaint
  } else {
    super.icon = new javax.swing.ImageIcon(getClass.getResource("/grey.gif"))
    super.repaint
  }

  listenTo(this)

  reactions += {
    case event.ButtonClicked(_) => {
      if (isEmpty) { theGUI.placeShip(x, y) }
      else {
        Model.game match {
          case Some(game) => {
            val ship = game.gameGrid.getCell(x, y)
            game.removeShip(ship.get)
          }
          case _ => None
        }
      }
    }
  }
}
