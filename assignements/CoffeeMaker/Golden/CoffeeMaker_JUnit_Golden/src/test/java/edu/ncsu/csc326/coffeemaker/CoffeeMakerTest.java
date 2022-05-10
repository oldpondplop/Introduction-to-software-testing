/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 * 
 * Permission has been explicitly granted to the University of Minnesota 
 * Software Engineering Center to use and distribute this source for 
 * educational purposes, including delivering online education through
 * Coursera or other entities.  
 * 
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including 
 * fitness for purpose.
 * 
 * 
 * Modifications 
 * 20171114 - Ian De Silva - Updated to comply with JUnit 4 and to adhere to 
 * 							 coding standards.  Added test documentation.
 */
package edu.ncsu.csc326.coffeemaker;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for CoffeeMaker class.
 * 
 * @author Sarah Heckman
 */
public class CoffeeMakerTest {
	
	/**
	 * The object under test.
	 */
	private CoffeeMaker coffeeMaker;
	
	// Sample recipes to use in testing.
	private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;

	/**
	 * Initializes some recipes to test with and the {@link CoffeeMaker} 
	 * object we wish to test.
	 * 
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	@Before
	public void setUp() throws RecipeException {
		coffeeMaker = new CoffeeMaker();
		
		//Set up for r1
		recipe1 = new Recipe();
		recipe1.setName("Coffee");
		recipe1.setAmtChocolate("0");
		recipe1.setAmtCoffee("3");
		recipe1.setAmtMilk("1");
		recipe1.setAmtSugar("1");
		recipe1.setPrice("50");
		
		//Set up for r2
		recipe2 = new Recipe();
		recipe2.setName("Mocha");
		recipe2.setAmtChocolate("20");
		recipe2.setAmtCoffee("3");
		recipe2.setAmtMilk("1");
		recipe2.setAmtSugar("1");
		recipe2.setPrice("75");
		
		//Set up for r3
		recipe3 = new Recipe();
		recipe3.setName("Latte");
		recipe3.setAmtChocolate("0");
		recipe3.setAmtCoffee("3");
		recipe3.setAmtMilk("3");
		recipe3.setAmtSugar("1");
		recipe3.setPrice("100");
		
		//Set up for r4
		recipe4 = new Recipe();
		recipe4.setName("Hot Chocolate");
		recipe4.setAmtChocolate("4");
		recipe4.setAmtCoffee("0");
		recipe4.setAmtMilk("1");
		recipe4.setAmtSugar("1");
		recipe4.setPrice("65");
	}
	
	
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with well-formed quantities
	 * Then we do not get an exception trying to read the inventory quantities.
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddInventory() throws InventoryException {
		coffeeMaker.addInventory("4","7","0","9");
	}
	
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with malformed quantities (i.e., a negative 
	 * quantity and a non-numeric string)
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryException() throws InventoryException {
		coffeeMaker.addInventory("4", "-1", "asdf", "3");
	}

	@Test
	public void testCheckInventory(){
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.makeCoffee(0, 75);
		assertEquals("Coffee: 12\n" + "Milk: 14\n" + "Sugar: 14\n" + "Chocolate: 15\n",
				coffeeMaker.checkInventory());
	}


	@Test
	public void testEditRecipe() throws RecipeException {
		coffeeMaker.addRecipe(recipe1);
		Recipe R = new Recipe();
		R.setName("EditedCoffee");
		R.setAmtCoffee("6");
		R.setAmtMilk("0");
		R.setAmtSugar("10");
		R.setAmtChocolate("3");
		R.setPrice("420");
		coffeeMaker.editRecipe(0, R);
		assertEquals(80, coffeeMaker.makeCoffee(0, 500));
	}
	@Test
	public void testMakeCoffeePayingMore() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(25, coffeeMaker.makeCoffee(0, 75));
	}

	@Test
	public void testMakeCoffeePayingLess()  {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(10, coffeeMaker.makeCoffee(0, 10));
	}

	@Test
	public void testMakeCoffeePayingExactly() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(0, coffeeMaker.makeCoffee(0, 50));
	}

	@Test
	public void testMakeCoffeeInvalidPayment() {
		assertEquals(-1, coffeeMaker.makeCoffee(0, -1));
	}

	@Test
	public void testMakeCoffeeMocha() {
		coffeeMaker.addRecipe(recipe2);
		assertEquals(75, coffeeMaker.makeCoffee(0, 75));
	}

	@Test
	public void testMakeCoffeeLatte() {
		coffeeMaker.addRecipe(recipe3);
		assertEquals(0, coffeeMaker.makeCoffee(0, 100));
	}

	@Test
	public void testMakeCoffeeHotChocolate() {
		coffeeMaker.addRecipe(recipe4);
		assertEquals(50, coffeeMaker.makeCoffee(0, 115));
	}

	@Test
	public void testAddCoffee() throws InventoryException {
		Inventory I = new Inventory();
		I.addCoffee("10");
		assertEquals(25, I.getCoffee());
	}

	@Test
	public void testAddMilk() throws InventoryException {
		Inventory I = new Inventory();
		I.addMilk("10");
		assertEquals(25, I.getMilk());
	}

	@Test
	public void testAddSugar() throws InventoryException {
		Inventory I = new Inventory();
		I.addSugar("10");
		assertEquals(25, I.getSugar());
	}

	@Test
	public void testAddChocolate() throws InventoryException {
		Inventory I = new Inventory();
		I.addChocolate("10");
		assertEquals(25, I.getChocolate());
	}

}
