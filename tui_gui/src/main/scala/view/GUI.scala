package de.htwg.scala.solitairebattleship.view

import de.htwg.scala.solitairebattleship.controller._
import de.htwg.scala.solitairebattleship.model._
import de.htwg.scala.solitairebattleship.util.Observer
import de.htwg.scala.solitairebattleship.util.Orientation
import scala.swing._
import scala.swing.event.ButtonClicked
import com.sun.java.util.jar.pack.Package

class GUI(controller:GameController) extends swing.Frame with Observer with IView{
  var model:Game = Model.game
  Model.add(this)
  controller.registerView(this)
  
  val TextFieldSize = new TextField("5")
  val ButtonNewGame = new Button("New Game")
  val ButtonShowSolution = new Button("Show Solution")
  val state = new Label("")
  
  var selected = -1;
  var startPos:Tuple2[Int,Int] = (-1,-1) 
  
  resizable = false
  contents = controlPanel(null)
  
  listenTo(ButtonNewGame)
  listenTo(ButtonShowSolution)

  reactions += {
      	case ButtonClicked(ButtonNewGame) => {
      		val s:Int = TextFieldSize.text.toInt
      		state.text = "new Game with size: " + s      		      		
      		controller.newGame(s)  
      	}
      	case ButtonClicked(ButtonShowSolution) => {
      		state.text = "ShowSolution"
      		controller.showSolution
      	}
      	case ButtonClicked(_) => { println("clicked any button but not a special button?")}
      }   
    
  
  def update = {
    model = Model.game
    if(Model.game.gameGrid != null) contents = controlPanel(Model.game.gameGrid)
  }
  
  def showGameFinished {
    state.text = "Finished"
    update
  }
  
  def showValidationResult {
     state.text = "Validation"
     update
  }
  
  def showSolution {
     state.text = "Solution"
     contents = controlPanel(Model.game.solution)
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
    			contents += TextFieldSize
    			contents += ButtonNewGame
    			contents += ButtonShowSolution
    		} ,swing.BorderPanel.Position.North)
    		
        if(grid != null) add(gridField(grid), BorderPanel.Position.Center)
        if(model != null)  add(shipPanel,BorderPanel.Position.West)
    	add(state, BorderPanel.Position.South)
    	}   
  
    }
  
  def shipPanel:GridPanel = {
    val ships = model.getUnplacedShips
    new ShipSelectionPanel(ships,this)
  }
  
  
  private def emptyField = {new Label(){background = java.awt.Color.WHITE}}
  
  private def clmSumField(column:Int) = { 
    new Label(model.solution.getColumnSum(column).toString){
        horizontalAlignment = Alignment.Center
        verticalAlignment = Alignment.Center
        if (Model.game.getUnplacedShips.isEmpty){
          if (Model.game.validateColumnSum(column)) foreground = java.awt.Color.GREEN
          else foreground = java.awt.Color.RED
        }
    	}
    }
  
  private def rowSumField(row:Int) = {
    new Label(model.solution.getRowSum(row).toString ){
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
    state.text = "ship selected: " + id
    selected = id
    startPos = (-1,-1)
  }
  
  
  def placeShip(x:Int,y:Int):Unit = {
    var theShip:Ship = null
    if(selected >= 0) theShip = model.getShipWithID(selected)
    else{
      state.text = "Select a ship."
      return
    }
    
    val size:Int = model.gridSize
    
    if (startPos._1 < 0 || startPos._1 > size || startPos._2 < 0 || startPos._2 > size){
    	state.text = "1. click x: " + x +" y: " + y
    	startPos = (x,y)
    }
    else{
    	if(x <0 || y < 0 || x > size || y > size )
    		throw new IllegalArgumentException()
    	state.text = "2. click x: " + x +" y: " + y
    	
    	var or = Orientation.Horizontal  
    	
    	//vertical ship
    	if (startPos._1 == x){
    		val length = Math.abs(startPos._2 - y) +1
    		if (length != theShip.size) {
          state.text = ("length not matching")
          return 
        }
    		or = Orientation.Vertical
    	}
    	//horizontal ship
    	else if ( startPos._2 == y) {
    		val length = Math.abs(startPos._1 - x)+1
    		if (length != theShip.size) {
          state.text = ("length not matching")
          return
        }
    		or = Orientation.Horizontal
    	}
      //user tried to place diagonal
    	else {
    	  state.text = "not a valid position"
    	  startPos = (-1,-1)
    	  return
    	}
    	
    	var tmpX = startPos._1
    	var tmpY = startPos._2
    	if (startPos._1 > x) tmpX = x
    	if (startPos._2 > y) tmpY = y
    	
    	model.placeShip(theShip, tmpX, tmpY, or)
    	state.text = "ship Placed x:" + x + " y:"  + y +" Orientation:"+ or
    	startPos = (-1,-1)
    }
      
  }
  
  
} //class
