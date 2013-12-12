package de.htwg.scala.solitairebattleship.view

import de.htwg.scala.solitairebattleship.controller._
import de.htwg.scala.solitairebattleship.model._
import de.htwg.scala.solitairebattleship.util.Observer
import scala.swing._
import scala.swing.event.ButtonClicked

class GUI(controller:GameController) extends swing.Frame with Observer{
  var model:Battleship = controller.model
  model.add(this)

  
  val TextFieldSize = new TextField("GameSize")
  val ButtonNewGame = new Button("New Game")
  val ButtonShowSolution = new Button("Show Solution")
  var GamePanel = new GridPanel(3,3){
    preferredSize = new Dimension(400,400)
    maximumSize = new Dimension(400,400)
  }
  var curGrid:Grid = null;
  var bPanel = new swing.BorderPanel{
    add(new swing.GridPanel(1,3) {
    		contents += TextFieldSize 
    		contents += ButtonNewGame
    		contents += ButtonShowSolution
    	} ,swing.BorderPanel.Position.North)
    }
  
  contents = bPanel
  
  listenTo(ButtonNewGame)
  listenTo(ButtonShowSolution)

  reactions += {
      	case ButtonClicked(ButtonNewGame) => {
      		val s:Int = TextFieldSize.text.toInt
      		println("new Game with size: " + s)
      		controller.newGame(s)
      		curGrid = model.genGrid;
      	}
      	case ButtonClicked(ButtonShowSolution) => {
      		println("ShowSolution")
      		curGrid = model.genGrid;
      		update(curGrid)
      		updateGrid
      	}
      	case _ => 
      }   
    
  
  def update = {
    updateGrid
  }
  
  def update(grid:Grid):GridPanel = {
    val s:Int = model.genGrid.size
    
    var gPanel = new GridPanel(s + 2,s +2){
      for(x <- 0 to s + 1; y <- 0 to s + 1){
        if(x == 0 && y == 0){ contents += emptyField }
        else if (x == 0 && y == s + 1) {contents += emptyField}
        else if (y == 0 && x == s + 1) {contents += emptyField}
        else if (x== s+1  && y == s+1) {contents += emptyField}
        else if (x == s + 1) {contents += rowSumField(y -1)}
        else if (y == s + 1) {contents += clmSumField(x -1)}
        else if (x == 0) contents += lineLabel((y - 1).toString)
        else if (y == 0) contents += lineLabel((x - 1).toString)
        else { 	val tmp = cell(x-1,y-1, grid)
        		tmp.repaint
        		contents += tmp
        	}
          
        }
      }

  	gPanel.repaint
  	gPanel
  }//update
  
  
  def updateGrid = {
        bPanel = new swing.BorderPanel{
    	add(new swing.GridPanel(1,3){
    		contents += TextFieldSize
    		contents += ButtonNewGame
    		contents += ButtonShowSolution
    	} ,swing.BorderPanel.Position.North)
    	add(update(curGrid), BorderPanel.Position.South)
    }   
    contents = bPanel
  }
  
  private def emptyField = {new Label(){background = java.awt.Color.WHITE}}
  
  private def clmSumField(column:Int) = {
    new Label(model.genGrid.getColumnSum(column).toString){
          	horizontalAlignment = Alignment.Center
          	verticalAlignment = Alignment.Center
    	}
    }
  
  private def rowSumField(row:Int) = {
    new Label(model.genGrid.getRowSum(row).toString ){
    	horizontalAlignment = Alignment.Center
    	verticalAlignment = Alignment.Center
    	}
    }
  
  
  private def cell(x:Int, y:Int, grid:Grid) = {
    if(grid == null) new Label{
            maximumSize = new Dimension(50,50)
            preferredSize = new Dimension(50,50)
            border =  javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK)
      		background = java.awt.Color.LIGHT_GRAY
      	}
    else if(grid.getCell(x, y) == null) new Label{
    		background = java.awt.Color.LIGHT_GRAY
    		maximumSize = new Dimension(50,50)
    		preferredSize = new Dimension(50,50)
    		border =  javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK)
    	}
    else new Label(grid.getCell(x, y).id.toString){
    		background = java.awt.Color.BLACK
    		maximumSize = new Dimension(50,50)
    		preferredSize = new Dimension(50,50)
    		border =  javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK)
    	}
  }
  
  private def lineLabel(s:String) = { 
    new Label(s)
  }
  
} //class



	  //fill grid with match case
      /*for(x <- 0 to s + 1; y <- 0 to s + 1){
    	  val sPlus1 = s+1
    	  val tmp = sPlus1
    	  (x,y) match {
    	    case (0,0) 			=> contents += emptyField
    	    case (sPlus1,0) 	=> contents += emptyField
    	    case (0,sPlus1)		=> contents += emptyField
    	    case (sPlus1,tmp)	=> contents += emptyField 
    	    case (sPlus1, _)	=> contents += rowSumField(y-1) 
    	    case (_ , sPlus1)	=> contents += clmSumField(x-1)
    	    case (0,_)			=> contents += lineLabel((y-1).toString)
    	    case (_,0)			=> contents += lineLabel((x-1).toString)
    	    case _				=> contents += cell(x-1,y-1)
    	  }
        }
      }*/
