import { expect, test } from '@playwright/test';
import LoginPage from '../../pageObject/LoginPage.js';
import ProductsPage from '../../pageObject/ProductsPage.js';
import CheckoutTablePage from '../../pageObject/CheckoutTablePage.js';
import PaymentCardPage from '../../pageObject/PaymentCardPage.js';
import PaymentProcessingPage from '../../pageObject/PaymentProcessingPage.js';
import * as consts from '../../testData/constants.js';
import * as functions from '../../helpers/functions.js';

test('Update Order Journey', async ({ page }) => {
  let loginPage = new LoginPage(page);
  let productsPage = new ProductsPage(page);
  let checkoutTablePage = new CheckoutTablePage(page);
  let paymentCardPage = new PaymentCardPage(page);
  let paymentProcessingPage = new PaymentProcessingPage(page);

  console.log('Log in the app');
  await loginPage.login();

  console.log('Select Beer product');
  await productsPage.beerCheckbox.click();
  expect(await productsPage.beerCheckbox.isChecked()).toBeTruthy();
  console.log('    Get Beer price');
  let price1 = functions.removeSpaces(await productsPage.beerPrice.innerText());
  console.log('    Verify *Total selected products* number is 1');
  let totalNumber1 = await functions.leaveNumbersOnly(await productsPage.totalProductsSelected.innerText());
  expect(totalNumber1).toBe('1');
  console.log('    Verify *Total amount* equals Product`s price');
  expect(await productsPage.totalAmount.innerText()).toContain(price1);
  console.log('    Proceed to the *Payment* page');
  await productsPage.proceedButton.click();

  console.log('*Checkout* table Verifications');
  console.log('    Verify Beer product is displayed and contains valid price');
  expect(await page.locator('//td[.="Beer"]').innerText()).toBe(consts.BEER);
  expect(await functions.removeSpaces(await page.locator('//td[.="Beer"]/..//td[2]').innerText())).toBe(price1);
  console.log('    Verify *Total products selected* value is 1');
  let totalNumber2 = await functions.leaveNumbersOnly(await checkoutTablePage.totalProductsSelected.innerText());
  expect(totalNumber2).toBe(totalNumber1);
  console.log('    Verify *Total amount* equals Product`s price');
  expect(await checkoutTablePage.totalAmount.innerText()).toContain(price1);
  console.log('    Verify *Pay* button contains valid price');
  expect(await paymentCardPage.payButton.innerText()).toContain(price1);

  console.log('Go back to *Products* page');
  await paymentCardPage.returnBackButton.click();

  console.log('Select Cigar product');
  await productsPage.cigarCheckbox.click();
  expect(await productsPage.cigarCheckbox.isChecked()).toBeTruthy();
  console.log('    Get Cigar price');
  let price2 = functions.removeSpaces(await productsPage.cigarPrice.innerText());
  console.log('    Verify *Total selected products* number was increased by 1');
  let totalNumber3 = await functions.leaveNumbersOnly(await productsPage.totalProductsSelected.innerText());
  expect(totalNumber3).toBe('2');
  console.log('    Verify *Total amount* was increased by Cigar price');
  let totalPrice = parseFloat(functions.replaceUsdSign(price1)) + parseFloat(functions.replaceUsdSign(price2));
  expect(await productsPage.totalAmount.innerText()).toContain(totalPrice.toString());
  console.log('    Proceed to the *Payment* page');
  await productsPage.proceedButton.click();

  console.log('*Checkout* table Verifications');
  console.log('    Verify Cigar product is displayed and contains valid price');
  expect(await page.locator('//td[.="Cigar"]').innerText()).toBe(consts.CIGAR);
  expect(await functions.removeSpaces(await page.locator('//td[.="Cigar"]/..//td[2]').innerText())).toBe(price2);
  console.log('    Verify *Total products selected* value is 2');
  let totalNumber4 = await functions.leaveNumbersOnly(await checkoutTablePage.totalProductsSelected.innerText());
  expect(totalNumber4).toBe(totalNumber3);
  console.log('    Verify *Total amount* was increased by Cigar price');
  expect(await checkoutTablePage.totalAmount.innerText()).toContain(totalPrice.toString());
  console.log('    Verify *Pay* button contains valid price');
  expect(await paymentCardPage.payButton.innerText()).toContain(totalPrice.toString());

  console.log('Fill in Card Number, CVV and click *Pay* button');
  await paymentCardPage.cardNumberInput.type(consts.NUMBER_INVALID);
  await paymentCardPage.cvvInput.type(consts.CVV_INVALID);
  await paymentCardPage.payButton.click();

  console.log('Verify *Payment processing* modal is displayed and close it');
  await expect(await paymentProcessingPage.paymentProcessingModal).toBeVisible();
  await paymentProcessingPage.closeModalButton.click();

  console.log('Verify *Products* page is displayed in it`s initial state - witghout selections');
  expect(await productsPage.beerCheckbox.isChecked()).toBeFalsy();
  console.log('Verify *Total selected products* number is 0');
  let number3 = await functions.leaveNumbersOnly(await productsPage.totalProductsSelected.innerText());
  expect(number3).toBe('0');

  console.log('Verify *Total amount* equals 0');
  expect(await productsPage.totalAmount.innerText()).toContain('$0.00');
});
