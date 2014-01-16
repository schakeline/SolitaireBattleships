package de.htwg.scala.solitairebattleship.model

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class VisibleGridSpec  extends FlatSpec with Matchers {
  "A visible Grid" should "have the correct size" in {
    val gridArray: List[List[Option[Ship]]] = {
      (None :: None :: None :: Nil) :: 
      (None :: None :: None :: Nil) ::
      (None :: None :: None :: Nil) ::
      Nil
    }
    
    val rowSums = 1 :: 2 :: 4 :: Nil
    val clmSums = 3 :: 0 :: 2 :: Nil
    
    val visGrid = new VisibleGrid(gridArray,rowSums,clmSums)
    
    visGrid.getRowSum(1) should be(2)
    visGrid.getColumnSum(2) should be(2)
    visGrid.size should be(3)
    
  }

}