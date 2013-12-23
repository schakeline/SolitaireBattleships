package de.htwg.scala.solitairebattleship.view

import de.htwg.scala.solitairebattleship.controller._
import de.htwg.scala.solitairebattleship.model._
import de.htwg.scala.solitairebattleship.util.Observer
import de.htwg.scala.solitairebattleship.util.Orientation
import scala.swing._
import scala.swing.event.ButtonClicked
import com.sun.java.util.jar.pack.Package

class GUI(controller:GameController) extends swing.Frame with Observer{
  var model:Game = Model.game
  Model.add(this)
  
  val TextFieldSize = new TextField("5")
  val ButtonNewGame = new Button("New Game")
  val ButtonShowSolution = new Button("Show Solution")
  val ButtonEndGame = new Button("Exit")
  val state = new Label("")
  
  var selected = -1;
  var startPos:Tuple2[Int,Int] = (-1,-1) 
  
  resizable = false
  contents = controlPanel
  
  listenTo(ButtonNewGame)
  listenTo(ButtonShowSolution)
  listenTo(ButtonEndGame)

  reactions += {
      	case ButtonClicked(ButtonNewGame) => {
      		val s:Int = TextFieldSize.text.toInt
      		state.text = "new Game with size: " + s      		      		
      		controller.newGame(s)  
      		update //need to Update manual because selecting the curGrid is not a change in the model
      	}
      	case ButtonClicked(ButtonShowSolution) => {
      		state.text = "ShowSolution"
      		update //need to Update again, because now "curGrid" is set
      	}
      	case ButtonClicked(ButtonEndGame) => {
      	  System.exit(0);
      	}
      	case ButtonClicked(_) => { println("clicked any button but not a special button?")}
      }   
    
  
  def update = {
    model = Model.game
    contents = controlPanel
  }
  
  def gridField():GridPanel = {
    if(model.gameGrid == null) return new GridPanel(1,1)
    
    val s:Int = model.gameGrid.size
    
    var gPanel = new GridPanel(s + 2,s +2){
      preferredSize = new Dimension(400,400)
      
      for (x <- 0 to s + 1; y <- 0 to s + 1){
        if (x == 0 && y == 0){ contents += emptyField }
        else if (x == 0 && y == s + 1) {contents += emptyField}
        else if (y == 0 && x == s + 1) {contents += emptyField}
        else if (x== s+1  && y == s+1) {contents += emptyField}
        else if (y == s + 1) {contents += rowSumField(x -1)}
        else if (x == s + 1) {contents += clmSumField(y -1)}
        else if (x == 0) contents += lineLabel((y - 1))
        else if (y == 0) contents += lineLabel((x - 1))
        else { 	contents += cell(y-1,x-1, model.gameGrid) 	} 
          
        }
      }
  	gPanel
  }//update
  
  def controlPanel:BorderPanel = {
  		new swing.BorderPanel{
    		add(new swing.GridPanel(1,3)
    		{	
    			contents += TextFieldSize
    			contents += ButtonNewGame
    			contents += ButtonShowSolution
    			contents += ButtonEndGame
    		} ,swing.BorderPanel.Position.North)
    		
        if(model != null){
          add(gridField, BorderPanel.Position.Center)
          add(shipPanel,BorderPanel.Position.West)
    		}
    		add(state, BorderPanel.Position.South)
    	}   
  
    }
  
  def shipPanel:GridPanel = {
    val ships = model.getUnplacedShips
    
    if(ships.isEmpty == false){
      val width = 100
      val height = 400
	    val max = (ships.maxBy(_.size)).size
      val cellSize = width / max
	    val lines = height / cellSize
      //ships.length * 2 +1
	    val gui = this
	    println("max: " + max + " lines: " +lines)
	    var gPanel = new GridPanel(lines, max){
	      preferredSize = new Dimension(width, height)
        
	      for (y <- 0 until lines; x <-0 until max){         
          if (y % 2 == 1 && (y /2) < ships.size) {
	          if (x < ships(y/2).size)  {
	            val id = ships(y/2).id
	            val s = new ShipSelection(id, gui)
	            contents += s
	            listenTo(s)
	          }
	          else contents += new Label()
	        }
	        else contents += new Label()
	      }
	    }
	    gPanel
    }
    else {      
      new GridPanel(1,1)
    }
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
  }
  
  
  def placeShip(x:Int,y:Int):Unit = {
    val theShip = model.getShipWithID(selected)
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
    	if (startPos._2 > y) tmpY = x
    	
    	model.placeShip(theShip, tmpX, tmpY, or)
    	state.text = "ship Placed x:" + x + " y:"  + y +" Orientation:"+ or
    	startPos = (-1,-1)
    }
      
  }
  
  
} //class
