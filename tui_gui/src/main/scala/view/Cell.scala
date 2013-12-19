package de.htwg.scala.solitairebattleship.view

import scala.swing._

class Cell(x:Int, y:Int, isEmpty:Boolean, theGUI:GUI)  extends Button {
  
	preferredSize = new Dimension(50,50)
	maximumSize = new Dimension(50,50)
	super.border =  javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK)
	
	if(isEmpty == false){
	  	super.icon = new javax.swing.ImageIcon(getClass.getResource("/black.gif"))
	    super.repaint	    
	  }
	else{
	  super.icon = new javax.swing.ImageIcon(getClass.getResource("/grey.gif"))
	  super.repaint	    
	}
	
	listenTo(this)
	
	reactions += {
	  case event.ButtonClicked(_) => 
	    {
	      if(isEmpty)
	    	  theGUI.placeShip(x, y)
	      else{
	        val ship = theGUI.model.gameGrid.getCell(x, y)
	        theGUI.model.removeShip(ship)
	      }
	    }
	}
}