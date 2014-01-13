package de.htwg.scala.solitairebattleship.view

import scala.swing.GridPanel
import scala.swing.Label
import de.htwg.scala.solitairebattleship.model.Ship
import java.awt.Dimension
import javax.swing.BorderFactory
import java.awt.Color

class ShipSelectionPanel(val ships:List[Ship],
                         val gui:GUI,
                         val lines:Int=20, 
                         val cellsPerLine:Int=5, 
                         width:Int = 150, 
                         height:Int = 600) extends GridPanel(lines,cellsPerLine) {

  preferredSize = new Dimension(width,height)
  
  for(y <-0 until lines; x <-0 until cellsPerLine)
  {
    if (y % 2 == 1 && (y /2) < ships.size) 
    {
      if (x < ships(y/2).size)  
      {
        val id = ships(y/2).id
        val s = new ShipSelection(id, gui)
        contents += s
        listenTo(s)
      }
      else {contents += new Label{border =  BorderFactory.createLineBorder(Color.BLACK)}}
    }
    else {contents += new Label{border =  BorderFactory.createLineBorder(Color.BLACK)}}
  }
  
}