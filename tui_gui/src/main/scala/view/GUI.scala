package de.htwg.scala.solitairebattleship.view

import de.htwg.scala.solitairebattleship.controller._
import de.htwg.scala.solitairebattleship.model._
import de.htwg.scala.solitairebattleship.util.Observer
import de.htwg.scala.solitairebattleship.util.Orientation
import scala.swing._
import scala.swing.event.ButtonClicked
import com.sun.java.util.jar.pack.Package
import de.htwg.scala.solitairebattleship.model.exception.ShipCollisionException

class GUI(controller:GameController) extends swing.Frame with Observer with IView{
  Model.add(this)
  controller.registerView(this)
  
  val SizeSelection = new ComboBox("3"::"4"::"5"::"6"::"7"::"8"::"9"::"10"::Nil)
  val ButtonNewGame = new Button("New Game")
  val ButtonShowSolution = new Button("Show Solution")
  val state = new Label("")
    
  resizable = false
  contents = controlPanel(null)
  
  listenTo(ButtonNewGame)
  listenTo(ButtonShowSolution)

  reactions += {
      	case ButtonClicked(ButtonNewGame) => {
      		val s:Int = SizeSelection.selection.item.toInt 
      		state.text = "new game"
      		controller.newGame(s)
      		GUIPlaceShip.reset      		
      	}
      	case ButtonClicked(ButtonShowSolution) => {
      		if(Model.game != null)controller.showSolution
      	}
      	case ButtonClicked(_) => { println("clicked any button but not a special button?")}
      }   
    
  
  def update = {
    if(Model.game != null)
    {
      if(Model.game.gameGrid != null) contents = controlPanel(Model.game.gameGrid)
    }
  }
  
  def showGameFinished {
    state.text = "Congratulations!"
    update
  }
  
  def showValidationResult {
    state.text = "Not a valid solution"
    update
  }
  
  def showSolution {
    state.text = "Solution"
    if(Model.game!= null) contents = controlPanel(Model.game.solution)
  }
  
  def gridField(grid:IGrid):GridPanel = {
    if(grid == null) return new GridPanel(1,1)
    
    val s:Int =grid.size
    
    var gPanel = new GridPanel(s + 2,s +2){
      preferredSize = new Dimension(600,600)
      
      for (x <- 0 to s + 1; y <- 0 to s + 1){
        if (x == 0 && y == 0){ contents += emptyField }
        else if (x == 0 && y == s + 1) {contents += emptyField}
        else if (y == 0 && x == s + 1) {contents += emptyField}
        else if (x== s+1  && y == s+1) {contents += emptyField}
        else if (y == s + 1) {contents += rowSumField(x -1)}
        else if (x == s + 1) {contents += clmSumField(y -1)}
        else if (x == 0) contents += lineLabel((y - 1))
        else if (y == 0) contents += lineLabel((x - 1))
        else { 	contents += cell(y-1,x-1, grid) 	} 
          
        }
      }
  	gPanel
  }//update
  
  def controlPanel(grid:IGrid):BorderPanel = {
  		new swing.BorderPanel{
    		add(new swing.GridPanel(1,3)
    		{	
    			contents += SizeSelection
    			contents += ButtonNewGame
    			contents += ButtonShowSolution
    		} ,swing.BorderPanel.Position.North)
    		
        if(grid != null) add(gridField(grid), BorderPanel.Position.Center)
        if(Model.game != null)  add(shipPanel,BorderPanel.Position.West)
    	add(state, BorderPanel.Position.South)
    	}   
  
    }
  
  def shipPanel:GridPanel = {
    val ships = Model.game.getUnplacedShips
    new ShipSelectionPanel(ships,this)
  }
  
  
  private def emptyField = {new Label(){background = java.awt.Color.WHITE}}
  
  private def clmSumField(column:Int) = { 
    new Label(Model.game.solution.getColumnSum(column).toString){
        horizontalAlignment = Alignment.Center
        verticalAlignment = Alignment.Center
        if (Model.game.getUnplacedShips.isEmpty){
          if (Model.game.validateColumnSum(column)) foreground = java.awt.Color.GREEN
          else foreground = java.awt.Color.RED
        }
    	}
    }
  
  private def rowSumField(row:Int) = {
    new Label(Model.game.solution.getRowSum(row).toString ){
        horizontalAlignment = Alignment.Center
        verticalAlignment = Alignment.Center
        if (Model.game.getUnplacedShips.isEmpty){
          if (Model.game.validateRowSum(row)) foreground = java.awt.Color.GREEN
          else foreground = java.awt.Color.RED
        }
    	}
    }
  
  
  private def cell(x:Int, y:Int, grid:IGrid):Button = {
    if(grid == null) new Cell(x,y,true, this)
    else if(grid.getCell(x, y) == null) new Cell(x,y,true, this)
    else { new Cell(x,y,false, this)   }
  }
  
  private def lineLabel(s:Int) = { 
    new Label((s+65).toChar + "")
  }
  
  def selectShip(id:Int) = {
    GUIPlaceShip.selectShip(id)
    state.text = "ship selected: " + id
  }
  
  
  def placeShip(x:Int,y:Int):Unit = {
    state.text = GUIPlaceShip.setPos(x, y, controller) 
  }
  
  
} //class
