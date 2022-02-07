const {test, expect} = require('@playwright/test');
const BasketPage = require('../page-objects/basket-page');
const CheckoutPage = require('../page-objects/checkout-page');
const ProductList = require('../page-objects/product-list');
const PaymentProcessingDialog = require('../page-objects/payment-processing-dialog');
const paths = require('../test-data/paths');

test.describe('On a checkout page', () => {
    test.use({storageState: `${paths.applicationState}/logged-in.json`});

    const visaFakeCardNumber = '4111111111111111';
    const fakeCVV = '111';
    const BEER = 'Beer';
    const CIGAR = 'Cigar';
    let basketPage;
    let checkoutPage;
    let productList;
    let paymentProcessingDialog;
    let returnBackButton;
    let beerCheckbox;
    let cigarCheckbox;
    let beerPrice;
    let cigarPrice;

    test.beforeEach(async ({page}) => {
        await page.goto('/');
        basketPage = new BasketPage(page);
        checkoutPage = new CheckoutPage(page);
        productList = new ProductList(page);
        paymentProcessingDialog = new PaymentProcessingDialog(page);
        returnBackButton = checkoutPage.returnBackButton;
        beerCheckbox = productList.getProductCheckbox(BEER);
        cigarCheckbox = productList.getProductCheckbox(CIGAR);
        beerPrice = await productList.getProductPrice(BEER);
        cigarPrice = await productList.getProductPrice(CIGAR);


        await beerCheckbox.click();
        await cigarCheckbox.click();
        await basketPage.proceedToCheckoutButton.click();
    });

    test.afterEach(async () => {
        await basketPage.clearShoppingCartIfNotEmpty();
    });

    test('Price shown on "Pay" button is equal to "Total amount" price', async () => {
        expect(await checkoutPage.getAmountOnPayButton()).toEqual(await productList.getTotalAmount());

        // cleanup
        await returnBackButton.click();
    });

    test('Payment cannot be made without card number', async () => {
        await checkoutPage.cardCvvField.fill(fakeCVV);
        await checkoutPage.payButton.click();
        await expect(paymentProcessingDialog.title).not.toBeVisible();
        await expect(checkoutPage.payButton).toBeVisible();

        // cleanup
        await returnBackButton.click();
    });

    test('Payment cannot be made without card CVV', async () => {
        await checkoutPage.cardNumberField.fill(visaFakeCardNumber);
        await checkoutPage.payButton.click();
        await expect(paymentProcessingDialog.title).not.toBeVisible();
        await expect(checkoutPage.payButton).toBeVisible();

        // cleanup
        await returnBackButton.click();
    });

    test('Payment with wrong data fails and opens a dialog window, which returns us to basket page when closed', async () => {
        await checkoutPage.cardNumberField.fill(visaFakeCardNumber);
        await checkoutPage.cardCvvField.fill(fakeCVV);
        await checkoutPage.payButton.click();

        await expect(paymentProcessingDialog.title).toBeVisible();
        await expect(paymentProcessingDialog.paymentProcessingInfo).toContainText('FAILED');

        await paymentProcessingDialog.closeButton.click();

        await expect(basketPage.proceedToCheckoutButton).toBeVisible();
    });

    // TODO - fix bug in the application:
    // for some unknown reasons after closing payment processing dialog product list on basket page behaves inconsistent
    // sometimes selected products remains selected, sometimes not

    // TODO - add positive test-cases after mocking successful response from payment processor
});