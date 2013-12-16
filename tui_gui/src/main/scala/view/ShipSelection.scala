package de.htwg.scala.solitairebattleship.view

import scala.swing._


class ShipSelection(id:Int, theGUI:GUI) extends Button {

	super.icon = new javax.swing.ImageIcon(".\\Resources\\black.gif")
	border = Swing.EmptyBorder(15, 10, 10, 10)
	listenTo(this)
	
	reactions += {
	  case event.ButtonClicked(_) => { theGUI.selectShip(id)}
	}
}