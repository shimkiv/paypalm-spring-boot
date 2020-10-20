package com.shimkiv.paypalm;

import com.shimkiv.paypalm.pages.Login;
import com.shimkiv.paypalm.pages.ProductCatalogue;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.codeborne.selenide.Selenide.*;


public class PaypalmWebApplicationJavaTests {
    private static final String TOTAL_PRODUCTS_SELECTED = "Total products selected: %d";
    private static final String TOTAL_AMOUNT = "Total amount: %s";

    Login login = new Login();
    ProductCatalogue productCatalogue = new ProductCatalogue();

    @BeforeSuite
    public void login() {
        open("http://localhost:8080/");
        login.authenticateWithUserNameAndPassword("demo", "demo", true);
        Assert.assertTrue($(By.xpath("//i[contains(text(), 'Bender')]")).isDisplayed(), "Could not login");
    }

    @Feature("Shopping Cart")
    @Description("Check that product quantity and total price in Shopping Cart are updated correctly.")
    @Test
    public void productQuantityAndTotalsUpdateCorrectly() throws Exception {
        SoftAssert productQuantityAndTotal = new SoftAssert();

        productCatalogue.addBeerToShoppingCart();

        productQuantityAndTotal.assertEquals($(By.xpath("//p[@style='float: left;']")).getText(),
                String.format(TOTAL_PRODUCTS_SELECTED, 1),
                "Incorrect product quantity after adding Beer");
        productQuantityAndTotal.assertEquals($(By.xpath("//p[@style='float: right;']")).getText(),
                String.format(TOTAL_AMOUNT, "$8.50"),
                "Incorrect total price after adding Beer");

        productCatalogue.addCigarToShoppingCart();

        productQuantityAndTotal.assertEquals($(By.xpath("//p[@style='float: left;']")).getText(),
                String.format(TOTAL_PRODUCTS_SELECTED, 2),
                "Incorrect product quantity after adding Cigar");
        productQuantityAndTotal.assertEquals($(By.xpath("//p[@style='float: right;']")).getText(),
                String.format(TOTAL_AMOUNT, "$10.30"),
                "Incorrect total price after adding Cigar");

        productCatalogue.addSuicideBoothServicesToShoppingCart();

        productQuantityAndTotal.assertEquals($(By.xpath("//p[@style='float: left;']")).getText(),
                String.format(TOTAL_PRODUCTS_SELECTED, 3),
                "Incorrect product quantity after adding Suicide booth services");
        productQuantityAndTotal.assertEquals($(By.xpath("//p[@style='float: right;']")).getText(),
                String.format(TOTAL_AMOUNT, "$10.53"),
                "Incorrect total price after adding Suicide booth services");

        productCatalogue.addOneWayTicketToShoppingCart();

        productQuantityAndTotal.assertEquals($(By.xpath("//p[@style='float: left;']")).getText(),
                String.format(TOTAL_PRODUCTS_SELECTED, 4),
                "Incorrect product quantity after adding One way ticket");
        productQuantityAndTotal.assertEquals($(By.xpath("//p[@style='float: right;']")).getText(),
                String.format(TOTAL_AMOUNT, "$77.13"),
                "Incorrect total price after adding One way ticket");

        productCatalogue.addIceCreamToShoppingCart();

        productQuantityAndTotal.assertEquals($(By.xpath("//p[@style='float: left;']")).getText(),
                String.format(TOTAL_PRODUCTS_SELECTED, 5),
                "Incorrect product quantity after adding Ice cream");
        productQuantityAndTotal.assertEquals($(By.xpath("//p[@style='float: right;']")).getText(),
                String.format(TOTAL_AMOUNT, "$78.13"),
                "Incorrect total price after adding Ice cream");

        productCatalogue.removeOneWayTicketFromShoppingCart();

        productQuantityAndTotal.assertEquals($(By.xpath("//p[@style='float: left;']")).getText(),
                String.format(TOTAL_PRODUCTS_SELECTED, 4),
                "Incorrect product quantity after removing One way ticket");
        productQuantityAndTotal.assertEquals($(By.xpath("//p[@style='float: right;']")).getText(),
                String.format(TOTAL_AMOUNT, "$11.53"),
                "Incorrect total price after removing One way ticket");
        productQuantityAndTotal.assertAll();
    }
}
