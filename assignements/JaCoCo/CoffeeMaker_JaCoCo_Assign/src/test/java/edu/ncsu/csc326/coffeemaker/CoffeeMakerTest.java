package edu.ncsu.csc326.coffeemaker;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

/**
 * 
 * @author Sarah Heckman
 *
 * Extended by Mike Whalen
 *
 * Unit tests for CoffeeMaker class.
 */

public class CoffeeMakerTest {
	
	private Recipe r1;
	private Recipe r2;
	private Recipe r3;
	private Recipe r4;
	private Recipe r5;
	private CoffeeMaker cm;
	private RecipeBook recipeBookStub;
	private Recipe [] stubRecipies; 
	
	@Before
	public void setUp() throws 	RecipeException {
		
		cm = new CoffeeMaker();
		
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
		cm.addInventory("4","7","0","9");
	}

	@Test
	public void testCheckInventory(){
		cm.addRecipe(r1);
		Inventory I = new Inventory();

		Integer coffee = I.getCoffee() - r1.getAmtCoffee();
		Integer milk = I.getMilk() - r1.getAmtMilk();
		Integer sugar = I.getSugar() - r1.getAmtSugar();
		Integer chocolate = I.getChocolate() - r1.getAmtChocolate();

		cm.makeCoffee(0, 75);

		Assert.assertEquals("Coffee: "+ coffee +"\n"
							+"Milk: "+ milk +"\n"
							+"Sugar: "+ sugar +"\n"
							+"Chocolate: "+ chocolate +"\n",
							cm.checkInventory());
	}

	@Test
	public void testEditRecipe() throws RecipeException {
		cm.addRecipe(r1);
		Recipe R = new Recipe();
		R.setName("EditedCoffee");
		R.setAmtCoffee("6");
		R.setAmtMilk("0");
		R.setAmtSugar("10");
		R.setAmtChocolate("3");
		R.setPrice("420");
		cm.editRecipe(0, R);
		Assert.assertEquals(80, cm.makeCoffee(0, 500));
	}

	@Test
	public void testEditRecipe2() throws RecipeException {
		cm.addRecipe(r1);
		Recipe R = new Recipe();
		R.setName("EditedCoffee");
		R.setAmtCoffee("6");
		R.setAmtMilk("0");
		R.setAmtSugar("10");
		R.setAmtChocolate("3");
		R.setPrice("420");
		Assert.assertEquals(null, cm.editRecipe(1, R));
	}

	@Test
	public void testDeleteRecipe() {
		cm.addRecipe(r1);
		cm.addRecipe(r2);
		cm.addRecipe(r3);

		String deleted_recipe = cm.deleteRecipe(0);
		Assert.assertEquals(r1.getName(), deleted_recipe);
	}

	@Test
	public void testDeleteRecipe2() {
		cm.addRecipe(r1);
		cm.addRecipe(r2);
		cm.addRecipe(r3);

		String deleted_recipe = cm.deleteRecipe(-1);
		Assert.assertEquals(null, deleted_recipe);
	}

	@Test
	public void testDeleteRecipe3() {

		String deleted_recipe = cm.deleteRecipe(4);
		Assert.assertEquals(null, deleted_recipe);
	}

	@Test
	public void testDeleteRecipe4() {
		cm.addRecipe(r1);
		String deleted_recipe = cm.deleteRecipe(2);
		Assert.assertEquals(null, deleted_recipe);
	}

	@Test
	public void testAddRecipe() {
		cm.addRecipe(r1);
		boolean added = cm.addRecipe(r1);
		Assert.assertEquals(false, added);
	}

	@Test
	public void testAddRecipe2() {
		cm.addRecipe(r1);
		cm.addRecipe(r2);
		cm.addRecipe(r3);
		cm.addRecipe(r4);
		boolean not_added = cm.addRecipe(r5);
		Assert.assertEquals(false, not_added);
	}


	@Test
	public void testMakeCoffeePayingMore() {
		cm.addRecipe(r1);
		Assert.assertEquals(25, cm.makeCoffee(0, 75));
	}

	@Test
	public void testMakeCoffeePayingLess()  {
		cm.addRecipe(r1);
		Assert.assertEquals(10, cm.makeCoffee(0, 10));
	}

	@Test
	public void testMakeCoffeePayingExactly() {
		cm.addRecipe(r1);
		Assert.assertEquals(0, cm.makeCoffee(0, 50));
	}

	@Test
	public void testMakeCoffeeInvalidPayment() {
		Assert.assertEquals(-1, cm.makeCoffee(0, -1));
	}


	@Test
	public void testMakeCofeeMultipleRecipes() {
		cm.addRecipe(r1);
		cm.addRecipe(r2);
		cm.addRecipe(r3);
		Assert.assertEquals(0, cm.makeCoffee(2, 100));
	}

	@Test
	public void testMakeCofeeInvalidRecipe() {
		Assert.assertEquals(100, cm.makeCoffee(-1, 100));
	}

	@Test
	public void testMakeCofeeInvalidRecipe2() {
		Assert.assertEquals(100, cm.makeCoffee(4, 100));
	}

	@Test
	public void testMakeCoffeeMocha() {
		// not enough ingredients
		cm.addRecipe(r2);
		Assert.assertEquals(75, cm.makeCoffee(0, 75));
	}

	@Test
	public void testMakeCoffeeLatte() {
		cm.addRecipe(r3);
		Assert.assertEquals(0, cm.makeCoffee(0, 100));
	}

	@Test
	public void testMakeCoffeeHotChocolate() {
		cm.addRecipe(r4);
		Assert.assertEquals(50, cm.makeCoffee(0, 115));
	}

	@Test
	public void testAddCoffee() throws InventoryException {
		Inventory I = new Inventory();
		I.addCoffee("10");
		Assert.assertEquals(25, I.getCoffee());
	}

	@Test
	public void testAddMilk() throws InventoryException {
		Inventory I = new Inventory();
		I.addMilk("10");
		Assert.assertEquals(25, I.getMilk());
	}

	@Test
	public void testAddSugar() throws InventoryException {
		Inventory I = new Inventory();
		I.addSugar("10");
		Assert.assertEquals(25, I.getSugar());
	}

	@Test
	public void testAddChocolate() throws InventoryException {
		Inventory I = new Inventory();
		I.addChocolate("10");
		Assert.assertEquals(25, I.getChocolate());
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
		Assert.assertEquals(true, r1.equals(r));
	}



	@Test
	public void testReturnRecipeName() throws RecipeException {
		String rn = r1.toString();
		Assert.assertEquals("Coffee", rn);
	}


}



