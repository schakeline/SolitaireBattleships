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
  contents = controlPanel(None)
  
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
      Model.game match {
        case Some(grid) => controller.showSolution
        case _ => {}
      }
    }
    case ButtonClicked(_) => {}
  }   
    
  
  def update:Unit = {
      Model.game match {
        case Some(game) => contents =controlPanel(Some(game.gameGrid))
        case _          => None
      }
  }
  
  def showGameFinished {    
    contents = new BorderPanel{
      add(buttonBar ,BorderPanel.Position.North)
      add(new Label("Congratulations!"), BorderPanel.Position.South)
    }
  }
  
  def showValidationResult {
    state.text = "Not a valid solution"
    update
  }
  
  def showSolution {
    state.text = "Solution"
    Model.game match {
      case Some(game) => contents = controlPanel(Some(game.solution))
      case _          => None
    }
  }
  
  def gridField(optionGrid:Option[IGrid]):GridPanel = {  
    
    optionGrid match {
      case Some(grid) => {   
        val s:Int = grid.size
        var gPanel = new GridPanel(s + 2,s + 2){
          preferredSize = new Dimension(600,600)
      
          for (x <- 0 to s + 1; y <- 0 to s + 1){
            if (x == 0 && y == 0){ contents += emptyField }
            else if (x == 0 && y == s + 1) {contents += emptyField}
            else if (y == 0 && x == s + 1) {contents += emptyField}
            else if (x == s + 1  && y == s + 1) {contents += emptyField}
            else if (y == s + 1) {contents += rowSumField(x - 1)}
            else if (x == s + 1) {contents += clmSumField(y - 1)}
            else if (x == 0) {contents += lineLabel((y - 1))}
            else if (y == 0) {contents += lineLabel((x - 1))}
            else { contents += cell(y - 1,x - 1, grid)} 
          }
        }
        gPanel
      }
      case _ => new GridPanel(1,1)
    }
  }//update
  
  def buttonBar():GridPanel = {
    new swing.GridPanel(1,3){
     contents += SizeSelection
     contents += ButtonNewGame
     contents += ButtonShowSolution
    }
  }
  
  def controlPanel(optionGrid:Option[IGrid]):BorderPanel = {
    new swing.BorderPanel{
      add(buttonBar ,swing.BorderPanel.Position.North)
      add(state, BorderPanel.Position.South)
      add(gridField(optionGrid), BorderPanel.Position.Center)
      add(shipPanel,BorderPanel.Position.West)
    }   
  }
  
  def shipPanel:GridPanel = {
    Model.game match {
      case Some(game) =>{
        val ships = game.getUnplacedShips
        new ShipSelectionPanel(ships,this)
      }
      case _ => new GridPanel(1,1)
    }
  }
  
  
  private def emptyField = {new Label(){background = java.awt.Color.WHITE}}
  
  private def clmSumField(column:Int) = { 
    Model.game match{
      case Some(game) => {
        new Label(game.solution.getColumnSum(column).toString){
          horizontalAlignment = Alignment.Center
          verticalAlignment = Alignment.Center
          if (game.getUnplacedShips.isEmpty){
            if (game.validateColumnSum(column)) {foreground = java.awt.Color.GREEN}
            else {foreground = java.awt.Color.RED}
          }
        }
      }
      case _ => new Label("")
    }
  }
  
  private def rowSumField(row:Int) = {
    Model.game match{
      case Some(game) => {
        new Label(game.solution.getRowSum(row).toString ){
          horizontalAlignment = Alignment.Center
          verticalAlignment = Alignment.Center
          if (game.getUnplacedShips.isEmpty){
            if (game.validateRowSum(row)) {foreground = java.awt.Color.GREEN}
            else {foreground = java.awt.Color.RED}
          }
        }
      }
      case _ => new Label("")
    }
  }
  
  
  private def cell(x:Int, y:Int, grid:IGrid):Button = {
    grid.getCell(x,y) match{
      case Some(ship) => new Cell(x,y,false,this)
      case _ => new Cell(x,y,true,this)
    }
  }
  
  private def lineLabel(s:Int) = { 
    new Label((s+ 65).toChar + "")
  }
  
  def selectShip(id:Int):Unit = {
    GUIPlaceShip.selectShip(id)
    state.text = "ship selected: " + id
  }
  
  
  def placeShip(x:Int,y:Int):Unit = {
    state.text = GUIPlaceShip.setPos(x, y, controller) 
  }
  
  
} //class
