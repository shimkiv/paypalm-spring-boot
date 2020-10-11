package com.shimkiv.paypalm.pages;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;

public class ProductCatalogue {
private static final Logger log = LoggerFactory.getLogger(ProductCatalogue.class);

    public void addBeerToShoppingCart() throws Exception {
        $(By.xpath("//input[@name='DUHDZJHGYY']/..//span[@class='check']")).click();
        if(!$(By.xpath("//input[@name='DUHDZJHGYY'][@checked='checked']")).exists()) {
            throw new Exception("Could not add Beer to Shopping Cart");
        }
    }

    public void removeBeerFromShoppingCart() {
        if ($(By.xpath("//input[@name='DUHDZJHGYY'][@checked='checked']")).exists()) {
            $(By.xpath("//input[@name='DUHDZJHGYY']/..//span[@class='check']")).click();
        } else {
            log.info("Looks like Beer is not added to Shopping Cart, skipping");
        }
    }

    public void addCigarToShoppingCart() throws Exception {
        $(By.xpath("//input[@name='JDN4ILZB0T']/..//span[@class='check']")).click();
        if(!$(By.xpath("//input[@name='JDN4ILZB0T'][@checked='checked']")).exists()) {
            throw new Exception("Could not add Cigar to Shopping Cart");
        }
    }

    public void removeCigarFromShoppingCart() {
        if ($(By.xpath("//input[@name='JDN4ILZB0T'][@checked='checked']")).exists()) {
            $(By.xpath("//input[@name='JDN4ILZB0T']/..//span[@class='check']")).click();
        } else {
            log.info("Looks like Cigar is not added to Shopping Cart, skipping");
        }
    }

    public void addSuicideBoothServicesToShoppingCart() throws Exception {
        $(By.xpath("//input[@name='M50Z86T758']/..//span[@class='check']")).click(); //Suicide booth services
        if(!$(By.xpath("//input[@name='M50Z86T758'][@checked='checked']")).exists()) {
            throw new Exception("Could not add Suicide booth services to Shopping Cart");
        }
    }

    public void removeSuicideBoothServicesFromShoppingCart() {
        if ($(By.xpath("//input[@name='M50Z86T758'][@checked='checked']")).exists()) {
            $(By.xpath("//input[@name='M50Z86T758']/..//span[@class='check']")).click();
        } else {
            log.info("Looks like Suicide booth services are not added to Shopping Cart, skipping");
        }
    }

    public void addOneWayTicketToShoppingCart() throws Exception {
        $(By.xpath("//input[@name='M4MY49Z5JB']/..//span[@class='check']")).click(); //One way ticket
        if(!$(By.xpath("//input[@name='M4MY49Z5JB'][@checked='checked']")).exists()) {
            throw new Exception("Could not add One way ticket to Shopping Cart");
        }
    }

    public void removeOneWayTicketFromShoppingCart() {
        if ($(By.xpath("//input[@name='M4MY49Z5JB'][@checked='checked']")).exists()) {
            $(By.xpath("//input[@name='M4MY49Z5JB']/..//span[@class='check']")).click();
        } else {
            log.info("Looks like One way ticket is not added to Shopping Cart, skipping");
        }
    }

    public void addIceCreamToShoppingCart() throws Exception {
        $(By.xpath("//input[@name='DSX9JW5ACM']/..//span[@class='check']")).click(); //Ice cream
        if(!$(By.xpath("//input[@name='DSX9JW5ACM'][@checked='checked']")).exists()) {
            throw new Exception("Could not add Ice cream to Shopping Cart");
        }
    }

    public void removeIceCreamFromShoppingCart() {
        if ($(By.xpath("//input[@name='DSX9JW5ACM'][@checked='checked']")).exists()) {
            $(By.xpath("//input[@name='DSX9JW5ACM']/..//span[@class='check']")).click();
        } else {
            log.info("Looks like Ice cream is not added to Shopping Cart, skipping");
        }
    }
}
