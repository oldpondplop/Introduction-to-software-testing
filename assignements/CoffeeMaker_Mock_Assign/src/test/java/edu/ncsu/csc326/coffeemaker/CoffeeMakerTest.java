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
 * Modifications
 * 20171113 - Michael W. Whalen - Extended with additional recipe.
 * 20171114 - Ian J. De Silva   - Updated to JUnit 4; fixed variable names.
 */
package edu.ncsu.csc326.coffeemaker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;



/**
 * Unit tests for CoffeeMaker class.
 * 
 * @author Sarah Heckman
 *
 * Extended by Mike Whalen
 */

public class CoffeeMakerTest {
	
	//-----------------------------------------------------------------------
	//	DATA MEMBERS
	//-----------------------------------------------------------------------
	private Recipe r1;
	private Recipe r2;
	private Recipe r3;
	private Recipe r4;
	private Recipe r5;
	
	private Recipe [] stubRecipies; 
	
	/**
	 * The coffee maker -- our object under test.
	 */
	private CoffeeMaker cm;
	
	/**
	 * The stubbed recipe book.
	 */
	private RecipeBook recipeBookStub;
	
	
	//-----------------------------------------------------------------------
	//	Set-up / Tear-down
	//-----------------------------------------------------------------------
	/**
	 * Initializes some recipes to test with, creates the {@link CoffeeMaker} 
	 * object we wish to test, and stubs the {@link RecipeBook}. 
	 * 
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	@Before
	public void setUp() throws RecipeException {
		
		recipeBookStub = mock(RecipeBook.class);
		cm = new CoffeeMaker(recipeBookStub, new Inventory());
		
		//Set up for r1
		r1 = new Recipe();
		r1.setName("Coffee");
		r1.setAmtChocolate("0");
		r1.setAmtCoffee("3");
		r1.setAmtMilk("1");
		r1.setAmtSugar("1");
		r1.setPrice("50");
		
		//Set up for r2
		r2 = new Recipe();
		r2.setName("Mocha");
		r2.setAmtChocolate("20");
		r2.setAmtCoffee("3");
		r2.setAmtMilk("1");
		r2.setAmtSugar("1");
		r2.setPrice("75");
		
		//Set up for r3
		r3 = new Recipe();
		r3.setName("Latte");
		r3.setAmtChocolate("0");
		r3.setAmtCoffee("3");
		r3.setAmtMilk("3");
		r3.setAmtSugar("1");
		r3.setPrice("100");
		
		//Set up for r4
		r4 = new Recipe();
		r4.setName("Hot Chocolate");
		r4.setAmtChocolate("4");
		r4.setAmtCoffee("0");
		r4.setAmtMilk("1");
		r4.setAmtSugar("1");
		r4.setPrice("65");
		
		//Set up for r5 (added by MWW)
		r5 = new Recipe();
		r5.setName("Super Hot Chocolate");
		r5.setAmtChocolate("6");
		r5.setAmtCoffee("0");
		r5.setAmtMilk("1");
		r5.setAmtSugar("1");
		r5.setPrice("100");

		stubRecipies = new Recipe [] {r1, r2, r3};
	}


	//-----------------------------------------------------------------------
	//	Test Methods
	//-----------------------------------------------------------------------

	// put your tests here!

	@Test
	public void testMakeCoffee() {
		cm = new CoffeeMaker(recipeBookStub, new Inventory());
		assertTrue(true);
	}

	//-----------------------------------------------------------------------
	//	Purchase coffee with enough ingredients & money
	//-----------------------------------------------------------------------

	@Test
	public void testPurchaseBeverage() {
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);
		cm = new CoffeeMaker(recipeBookStub, new Inventory());
		assertEquals(25, cm.makeCoffee(0,75));
	}

	//-----------------------------------------------------------------------
	//	Not enough money
	//-----------------------------------------------------------------------

	@Test
	public void testPurchaseBeverage1() {
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);
		cm = new CoffeeMaker(recipeBookStub, new Inventory());
		assertEquals(49, cm.makeCoffee(0,49));
	}

	//-----------------------------------------------------------------------
	//	Not enough ingredients
	//-----------------------------------------------------------------------

	@Test
	public void testPurchaseBeverage2() {
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);
		cm = new CoffeeMaker(recipeBookStub, new Inventory());
		assertEquals(100, cm.makeCoffee(1,100));
	}

	//-----------------------------------------------------------------------
	//	Exact amount of money
	//-----------------------------------------------------------------------

	@Test
	public void testPurchaseBeverage3() {
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);
		cm = new CoffeeMaker(recipeBookStub, new Inventory());
		assertEquals(0, cm.makeCoffee(2,100));

	}

	//-----------------------------------------------------------------------
	//	Trying to purchase a beverage that doesn't exist
	//-----------------------------------------------------------------------

	@Test
	public void testPurchaseBeverage4() {
		when(recipeBookStub.getRecipes()).thenReturn(new Recipe[]{r1,r2,null});
		cm = new CoffeeMaker(recipeBookStub, new Inventory());
		assertEquals(100, cm.makeCoffee(2,100));
	}

	//-----------------------------------------------------------------------
	//	Trying to purchase a beverage at index that doesn't exist
	//-----------------------------------------------------------------------

	@Test
	public void testPurchaseBeverage5() {
		when(recipeBookStub.getRecipes()).thenReturn(new Recipe[]{r1,r2,r5});
		cm = new CoffeeMaker(recipeBookStub, new Inventory());
		assertEquals(100, cm.makeCoffee(4,100));
	}

	//-----------------------------------------------------------------------
	// verify that inventory.useIngredients gets called
	// -----------------------------------------------------------------------

	@Test
	public void testPurchaseBeverage6() {
		Inventory I = spy(new Inventory());

		when(recipeBookStub.getRecipes()).thenReturn(new Recipe[]{r1,r3,r5});

		cm=new CoffeeMaker(recipeBookStub, I);

		cm.makeCoffee(1, 100);

		verify(I).useIngredients(r3);
	}

	//-----------------------------------------------------------------------
	//	verify that inventory.enoughIngredients gets called
	// -----------------------------------------------------------------------

	@Test
	public void testPurchaseBeverage7() {
		Inventory I = spy(new Inventory());

		when(recipeBookStub.getRecipes()).thenReturn(new Recipe[]{r1,r3,r5});

		cm=new CoffeeMaker(recipeBookStub, I);

		cm.makeCoffee(1, 100);

		verify(I).enoughIngredients(r3);
	}

	//-----------------------------------------------------------------------
	//	verify that recipe methods are called once for the selected recipe and not for the other recipes
	// -----------------------------------------------------------------------

	@Test
	public void testCallRecipeMethods() {
		Recipe spy_r1 = spy(r1);
		Recipe spy_r2 = spy(r2);
		Recipe spy_r3 = spy(r3);

		when(recipeBookStub.getRecipes()).thenReturn(new Recipe[]{spy_r1,spy_r2,spy_r3});

		cm=new CoffeeMaker(recipeBookStub, new Inventory());

		assertEquals(50,cm.makeCoffee(0,100));

		verify(spy_r1, atLeast(1)).getAmtCoffee();
		verify(spy_r1, atLeast(1)).getAmtMilk();
		verify(spy_r1, atLeast(1)).getAmtSugar();
		verify(spy_r1, atLeast(1)).getAmtChocolate();
		verify(spy_r1, atLeast(1)).getPrice();

		verify(spy_r2, times(0)).getAmtCoffee();
		verify(spy_r2, times(0)).getAmtMilk();
		verify(spy_r2, times(0)).getAmtSugar();
		verify(spy_r2, times(0)).getAmtChocolate();
		verify(spy_r2, times(0)).getPrice();

		verify(spy_r3, times(0)).getAmtCoffee();
		verify(spy_r3, times(0)).getAmtMilk();
		verify(spy_r3, times(0)).getAmtSugar();
		verify(spy_r3, times(0)).getAmtChocolate();
		verify(spy_r3, times(0)).getPrice();

	}
	//-----------------------------------------------------------------------
	//	verify that recipe methods are called once for the selected recipe and not for the other recipes
	// -----------------------------------------------------------------------
	@Test
	public void testCallRecipeMethods1() {

		Recipe spy_r3 = spy(r3);
		Recipe spy_r4 = spy(r4);
		Recipe spy_r5 = spy(r5);

		when(recipeBookStub.getRecipes()).thenReturn(new Recipe[]{spy_r3,spy_r4,spy_r5});
		cm = new CoffeeMaker(recipeBookStub, new Inventory());

		assertEquals(100,cm.makeCoffee(2,200));

		verify(spy_r3, times(0)).getAmtCoffee();
		verify(spy_r3, times(0)).getAmtMilk();
		verify(spy_r3, times(0)).getAmtSugar();
		verify(spy_r3, times(0)).getAmtChocolate();
		verify(spy_r3, times(0)).getPrice();

		verify(spy_r4, times(0)).getAmtCoffee();
		verify(spy_r4, times(0)).getAmtMilk();
		verify(spy_r4, times(0)).getAmtSugar();
		verify(spy_r4, times(0)).getAmtChocolate();
		verify(spy_r4, times(0)).getPrice();

		verify(spy_r5, atLeastOnce()).getAmtCoffee();
		verify(spy_r5, atLeastOnce()).getAmtMilk();
		verify(spy_r5, atLeastOnce()).getAmtSugar();
		verify(spy_r5, atLeastOnce()).getAmtChocolate();
		verify(spy_r5, atLeastOnce()).getPrice();
	}
	//-----------------------------------------------------------------------
	//	verify that recipe methods are called once for the selected recipe and not for the other recipes
	// -----------------------------------------------------------------------
	@Test
	public void testCallRecipeMethods2() {

		Recipe spy_r3 = spy(r3);
		Recipe spy_r2 = spy(r2);
		Recipe spy_r4 = spy(r4);

		when(recipeBookStub.getRecipes()).thenReturn(new Recipe[]{spy_r3,spy_r4,spy_r2});
		cm = new CoffeeMaker(recipeBookStub, new Inventory());

		assertEquals(135,cm.makeCoffee(1,200));

		verify(spy_r4, atLeast(1)).getAmtCoffee();
		verify(spy_r4, atLeast(1)).getAmtMilk();
		verify(spy_r4, atLeast(1)).getAmtSugar();
		verify(spy_r4, atLeast(1)).getAmtChocolate();
		verify(spy_r4, atLeast(1)).getPrice();

		verify(spy_r3, times(0)).getAmtCoffee();
		verify(spy_r3, times(0)).getAmtMilk();
		verify(spy_r3, times(0)).getAmtSugar();
		verify(spy_r3, times(0)).getAmtChocolate();
		verify(spy_r3, times(0)).getPrice();

		verify(spy_r2, times(0)).getAmtCoffee();
		verify(spy_r2, times(0)).getAmtMilk();
		verify(spy_r2, times(0)).getAmtSugar();
		verify(spy_r2, times(0)).getAmtChocolate();
		verify(spy_r2, times(0)).getPrice();


	}

	//-----------------------------------------------------------------------
	//	check inventory
	// -----------------------------------------------------------------------
	@Test
	public void testCheckInventory(){

		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);

		cm=new CoffeeMaker(recipeBookStub, new Inventory());

		Inventory I = new Inventory();

		Integer coffee = I.getCoffee() - r1.getAmtCoffee();
		Integer milk = I.getMilk() - r1.getAmtMilk();
		Integer sugar = I.getSugar() - r1.getAmtSugar();
		Integer chocolate = I.getChocolate() - r1.getAmtChocolate();

		cm.makeCoffee(0, 75);

		assertEquals("Coffee: "+ coffee +"\n"
						+"Milk: "+ milk +"\n"
						+"Sugar: "+ sugar +"\n"
						+"Chocolate: "+ chocolate +"\n",
						cm.checkInventory());
	}

	//-----------------------------------------------------------------------
	//	check inventory before and after purchase
	// -----------------------------------------------------------------------
	@Test
	public void testCheckInventory2(){
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);

		Inventory I = new Inventory();
		cm=new CoffeeMaker(recipeBookStub, new Inventory());

		String initialInventory = cm.checkInventory();
		cm.makeCoffee(0, 75);
		String finalInventory = cm.checkInventory();

		assertNotEquals(initialInventory, finalInventory);

	}


}
