import { expect, test } from '@playwright/test';
import LoginPage from '../pageObject/LoginPage.js';
import CommonPage from '../pageObject/CommonPage.js';
import ProductsPage from '../pageObject/ProductsPage.js';
import * as consts from '../testData/constants.js';
import * as functions from '../helpers/functions.js';

test.describe('Products Page Verifications', () => {
  test('Verify *Products* page initial and updated states', async ({ page }) => {
    let loginPage = new LoginPage(page);
    let commonPage = new CommonPage(page);
    let productsPage = new ProductsPage(page);

    console.log('Log in the app');
    await loginPage.login();

    console.log('Verify Products Table titles');
    expect(await commonPage.tableTitle.innerText()).toBe(consts.PRODUCTS_TITLE);
    expect(await commonPage.currencyTitle.innerText()).toContain(consts.CURRENCY_TITLE);
    expect(await commonPage.currencyTitle.innerText()).toContain(consts.USD_TITLE);

    console.log('Verify initial checkboxes state - Not checked');
    expect(await productsPage.beerCheckbox.isChecked()).toBeFalsy();
    expect(await productsPage.cigarCheckbox.isChecked()).toBeFalsy();
    expect(await productsPage.suicideCheckbox.isChecked()).toBeFalsy();
    expect(await productsPage.oneWayCheckbox.isChecked()).toBeFalsy();
    expect(await productsPage.iceCreamCheckbox.isChecked()).toBeFalsy();

    console.log('Verify all products are displayed and have valid prices');
    // Beer
    expect(await productsPage.beerProduct.innerText()).toBe(consts.BEER);
    expect(await productsPage.beerPrice.innerText()).toBe(consts.BEER_PRICE);
    // Cigar
    expect(await productsPage.cigarProduct.innerText()).toBe(consts.CIGAR);
    expect(await productsPage.cigarPrice.innerText()).toBe(consts.CIGAR_PRICE);
    // Suicide booth services
    expect(await productsPage.suicideProduct.innerText()).toBe(consts.SUICIDE);
    expect(await productsPage.suicidePrice.innerText()).toBe(consts.SUICIDE_PRICE);
    // One way ticket
    expect(await productsPage.oneWayProduct.innerText()).toBe(consts.ONE_WAY);
    expect(await productsPage.oneWayPrice.innerText()).toBe(consts.ONE_WAY_PRICE);
    // Ice cream
    expect(await productsPage.iceCreamProduct.innerText()).toBe(consts.ICE_CREAM);
    expect(await productsPage.iceCreamPrice.innerText()).toBe(consts.ICE_CREAM_PRICE);

    console.log('Verify initial total selected products number is 0');
    let number = await productsPage.totalProductsSelected.innerText();
    expect(await functions.leaveNumbersOnly(number)).toBe('0');

    console.log('Verify initial total amount $ is 0');
    expect(await productsPage.totalAmount.innerText()).toContain('$0.00');

    console.log('Verify *PROCEED TO CHECKOUT* and *CLEAR SHOPPING CART* buttons are disabled by default');
    await expect(await productsPage.proceedButton).toBeDisabled();
    await expect(await productsPage.clearShoppingCartButton).toBeDisabled();

    console.log('Select checkboxes and verify updated state');
    await productsPage.beerCheckbox.click();
    expect(await productsPage.beerCheckbox.isChecked()).toBeTruthy();
    await productsPage.cigarCheckbox.click();
    expect(await productsPage.cigarCheckbox.isChecked()).toBeTruthy();
    await productsPage.suicideCheckbox.click();
    expect(await productsPage.suicideCheckbox.isChecked()).toBeTruthy();
    await productsPage.oneWayCheckbox.click();
    expect(await productsPage.oneWayCheckbox.isChecked()).toBeTruthy();
    await productsPage.iceCreamCheckbox.click();
    expect(await productsPage.iceCreamCheckbox.isChecked()).toBeTruthy();

    console.log('Verify *PROCEED TO CHECKOUT* and *CLEAR SHOPPING CART* buttons are enabled');
    await expect(await productsPage.proceedButton).toBeEnabled();
    await expect(await productsPage.clearShoppingCartButton).toBeEnabled();

    console.log('Possible to clear *Shopping Cart*');
    await productsPage.clearShoppingCartButton.click();

    console.log('Verify *PROCEED TO CHECKOUT* and *CLEAR SHOPPING CART* buttons are disabled');
    await expect(await productsPage.proceedButton).toBeDisabled();
    await expect(await productsPage.clearShoppingCartButton).toBeDisabled();

    console.log('Verify checkboxes are Not checked');
    expect(await productsPage.beerCheckbox.isChecked()).toBeFalsy();
    expect(await productsPage.cigarCheckbox.isChecked()).toBeFalsy();
    expect(await productsPage.suicideCheckbox.isChecked()).toBeFalsy();
    expect(await productsPage.oneWayCheckbox.isChecked()).toBeFalsy();
    expect(await productsPage.iceCreamCheckbox.isChecked()).toBeFalsy();
  });
});
