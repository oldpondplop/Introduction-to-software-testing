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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
	// verify that inventory.useIngredients gets called
	// -----------------------------------------------------------------------
	@Test
	public void testPurchaseBeverage5() {
		Inventory I = spy(new Inventory());
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);
		cm=new CoffeeMaker(recipeBookStub, I);
		cm.makeCoffee(1, 100);
		verify(I).useIngredients(r2);
	}
	//-----------------------------------------------------------------------
	//	verify that inventory.enoughIngredients gets called
	// -----------------------------------------------------------------------
	@Test
	public void testPurchaseBeverage6() {
		Inventory I = spy(new Inventory());
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);
		cm=new CoffeeMaker(recipeBookStub, I);
		cm.makeCoffee(1, 100);
		verify(I).enoughIngredients(r2);
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


	@Test(expected = InventoryException.class)
	public void testAddInventoryException() throws InventoryException {
		cm.addInventory("4", "1", "asdf", "3");
	}
	@Test(expected = InventoryException.class)
	public void testAddInventoryException1() throws InventoryException {
		cm.addInventory("1", "asdf", "1", "1");
	}
	@Test(expected = InventoryException.class)
	public void testAddInventoryException2() throws InventoryException {
		cm.addInventory("asdf", "1", "1", "1");
	}
	@Test(expected = InventoryException.class)
	public void testAddInventoryException3() throws InventoryException {
		cm.addInventory("1", "1", "1", "asdf");
	}
	@Test(expected = InventoryException.class)
	public void testAddInventoryException4() throws InventoryException {
		cm.addInventory("-1", "1", "1", "1");
	}
	@Test(expected = InventoryException.class)
	public void testAddInventoryException5() throws InventoryException {
		cm.addInventory("1", "-1", "1", "1");
	}
	@Test(expected = InventoryException.class)
	public void testAddInventoryException6() throws InventoryException {
		cm.addInventory("1", "1", "-1", "1");
	}
	@Test(expected = InventoryException.class)
	public void testAddInventoryException7() throws InventoryException {
		cm.addInventory("1", "1", "1", "-1");
	}

	@Test
	public void testAddInventory() throws InventoryException {
		cm.addInventory("4","7","0","9");
	}

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

	@Test
	public void testDeleteRecipe() {
		when(recipeBookStub.getRecipes()).thenReturn(new Recipe[]{r1, r2, r3});
		cm = new CoffeeMaker(recipeBookStub, new Inventory());

		when(recipeBookStub.deleteRecipe()).thenReturn(r1.getName());

		String deleted_recipe = cm.deleteRecipe(0);

		assertEquals(r1.getName(), deleted_recipe);
	}

	@Test
	public void testMakeCoffeePayingMore() {
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);
		cm = new CoffeeMaker(recipeBookStub, new Inventory());
		assertEquals(25, cm.makeCoffee(0, 75));
	}

	@Test
	public void testMakeCoffeePayingLess()  {
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);
		cm = new CoffeeMaker(recipeBookStub, new Inventory());
		assertEquals(10, cm.makeCoffee(0, 10));
	}

	@Test
	public void testMakeCoffeePayingExactly() {
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);
		cm = new CoffeeMaker(recipeBookStub, new Inventory());
		assertEquals(0, cm.makeCoffee(0, 50));
	}

	@Test
	public void testMakeCoffeeInvalidPayment() {
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);
		cm = new CoffeeMaker(recipeBookStub, new Inventory());
		assertEquals(-1, cm.makeCoffee(0, -1));
	}


	@Test
	public void testMakeCofeeMultipleRecipes() {
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);
		cm = new CoffeeMaker(recipeBookStub, new Inventory());
		assertEquals(0, cm.makeCoffee(2, 100));
	}


	@Test
	public void testMakeCofeeInvalidRecipe2() {
		when(recipeBookStub.getRecipes()).thenReturn(new Recipe[] {r1,r2,r3,r4,null});
		cm = new CoffeeMaker(recipeBookStub, new Inventory());
		assertEquals(100, cm.makeCoffee(4, 100));
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

	@Test(expected=RecipeException.class)
	public void testCreateRecipe() throws RecipeException {
		Recipe r = new Recipe();
		r.setName("test");
		r.setAmtCoffee("-1");
		r.setAmtMilk("1");
		r.setAmtSugar("1");
		r.setAmtChocolate("1");
		r.setPrice("1");
	}

	@Test(expected=RecipeException.class)
	public void testCreateRecipe1() throws RecipeException {
		Recipe r = new Recipe();
		r.setName("test");
		r.setAmtCoffee("1");
		r.setAmtMilk("-1");
		r.setAmtSugar("1");
		r.setAmtChocolate("1");
		r.setPrice("1");
	}

	@Test(expected=RecipeException.class)
	public void testCreateRecipe2() throws RecipeException {
		Recipe r = new Recipe();
		r.setName("test");
		r.setAmtCoffee("1");
		r.setAmtMilk("1");
		r.setAmtSugar("-1");
		r.setAmtChocolate("1");
		r.setPrice("1");
	}
	@Test(expected=RecipeException.class)
	public void testCreateRecipe3() throws RecipeException {
		Recipe r = new Recipe();
		r.setName("test");
		r.setAmtCoffee("1");
		r.setAmtMilk("1");
		r.setAmtSugar("1");
		r.setAmtChocolate("-1");
		r.setPrice("1");
	}

	@Test(expected=RecipeException.class)
	public void testCreateRecipe4() throws RecipeException {
		Recipe r = new Recipe();
		r.setName("test");
		r.setAmtCoffee("1");
		r.setAmtMilk("1");
		r.setAmtSugar("1");
		r.setAmtChocolate("1");
		r.setPrice("-1");
	}

	@Test(expected=RecipeException.class)
	public void testCreateRecipe5() throws RecipeException {
		Recipe r = new Recipe();
		r.setName("test");
		r.setAmtCoffee("a");
		r.setAmtMilk("1");
		r.setAmtSugar("2");
		r.setAmtChocolate("3");
		r.setPrice("4");
	}

	@Test(expected=RecipeException.class)
	public void testCreateRecipe6() throws RecipeException {
		Recipe r = new Recipe();
		r.setName("test");
		r.setAmtCoffee("1");
		r.setAmtMilk("a");
		r.setAmtSugar("2");
		r.setAmtChocolate("3");
		r.setPrice("4");
	}
	@Test(expected=RecipeException.class)
	public void testCreateRecipe7() throws RecipeException {
		Recipe r = new Recipe();
		r.setName("test");
		r.setAmtCoffee("1");
		r.setAmtMilk("1");
		r.setAmtSugar("a");
		r.setAmtChocolate("3");
		r.setPrice("4");
	}
	@Test(expected=RecipeException.class)
	public void testCreateRecipe8() throws RecipeException {
		Recipe r=new Recipe();
		r.setName("test");
		r.setAmtCoffee("1");
		r.setAmtMilk("1");
		r.setAmtSugar("1");
		r.setAmtChocolate("a");
		r.setPrice("4");
	}
	@Test(expected=RecipeException.class)
	public void testCreateRecipe9() throws RecipeException {
		Recipe r=new Recipe();
		r.setName("test");
		r.setAmtCoffee("1");
		r.setAmtMilk("1");
		r.setAmtSugar("1");
		r.setAmtChocolate("1");
		r.setPrice("a");
	}
	@Test(expected=RecipeException.class)
	public void testCreateRecipe10() throws RecipeException {
		Recipe r=new Recipe();
		r.setName(null);
		r.setAmtCoffee("1");
		r.setAmtMilk("1");
		r.setAmtSugar("1");
		r.setAmtChocolate("1");
		r.setPrice("a");

	}


	@Test
	public void testCreateRecipe11() throws RecipeException {
		Recipe r = new Recipe();
		r.setName("Coffee");
		assertEquals(true, r1.equals(r));
	}



	@Test
	public void testReturnRecipeName() throws RecipeException {
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);
		String rn = r1.toString();
		assertEquals("Coffee", rn);
	}


}
