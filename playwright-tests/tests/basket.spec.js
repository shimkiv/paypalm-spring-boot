const {test, expect} = require('@playwright/test');
const BasketPage = require('../page-objects/basket-page');
const CheckoutPage = require('../page-objects/checkout-page');
const ProductList = require('../page-objects/product-list');
const paths = require('../test-data/paths');

test.describe('On a basket page', () => {
    test.use({ storageState: `${paths.applicationState}/logged-in.json` });

    const BEER = 'Beer';
    const CIGAR = 'Cigar';
    const ICE_CREAM = 'Ice cream';
    let basketPage;
    let checkoutPage;
    let productList;
    let clearShoppingCartButton;
    let proceedToCheckoutButton;
    let beerCheckbox;
    let cigarCheckbox;
    let beerPrice;
    let cigarPrice;
    let beerProduct;
    let cigarProduct;
    let iceCreamProduct;

    test.beforeEach(async ({page}) => {
        await page.goto('/');
        basketPage = new BasketPage(page);
        checkoutPage = new CheckoutPage(page);
        productList = new ProductList(page);
        clearShoppingCartButton = basketPage.clearShoppingCartButton;
        proceedToCheckoutButton = basketPage.proceedToCheckoutButton;
        beerCheckbox = productList.getProductCheckbox(BEER);
        cigarCheckbox = productList.getProductCheckbox(CIGAR);
        beerPrice = await productList.getProductPrice(BEER);
        cigarPrice = await productList.getProductPrice(CIGAR);
        beerProduct = productList.getProductBar(BEER);
        cigarProduct = productList.getProductBar(CIGAR);
        iceCreamProduct = productList.getProductBar(ICE_CREAM);
    });

    test.afterEach(async () => {
        await basketPage.clearShoppingCartIfNotEmpty();
    });

    test('Click on a product marks it as selected', async () => {
        await beerCheckbox.click();
        await expect(beerCheckbox).toBeChecked();
    });

    test('Click on selected product removes selection', async () => {
        await expect(beerCheckbox).not.toBeChecked();
        await beerCheckbox.click();
        await beerCheckbox.click();
        await expect(beerCheckbox).not.toBeChecked();
    });

    test('Selecting products increase "Total products selected count"', async () => {
        expect(await productList.getSelectedProductsCount()).toBe(0);
        await beerCheckbox.click();
        expect(await productList.getSelectedProductsCount()).toBe(1);
        await cigarCheckbox.click();
        expect(await productList.getSelectedProductsCount()).toBe(2);
    });

    test('Removing products decrease "Total products selected count"', async () => {
        await beerCheckbox.click();
        await cigarCheckbox.click();
        expect(await productList.getSelectedProductsCount()).toBe(2);
        await beerCheckbox.click();
        expect(await productList.getSelectedProductsCount()).toBe(1);
        await cigarCheckbox.click();
        expect(await productList.getSelectedProductsCount()).toBe(0);
    });

    test('"Total amount" is updated when product is selected', async () => {
        await beerCheckbox.click();
        expect(await productList.getTotalAmount()).toBe(beerPrice);
        await cigarCheckbox.click();
        expect(await productList.getTotalAmount()).toBe(beerPrice + cigarPrice);
    });

    test('"Total amount" is updated when product is removed from selected', async () => {
        await beerCheckbox.click();
        await cigarCheckbox.click();
        expect(await productList.getTotalAmount()).toBe(beerPrice + cigarPrice);
        await beerCheckbox.click();
        expect(await productList.getTotalAmount()).toBe(cigarPrice);
        await cigarCheckbox.click();
        expect(await productList.getTotalAmount()).toBe(0);
    });

    test('"Clear shopping cart" button is disabled when there is no products selected', async () => {
        await expect(clearShoppingCartButton).toBeDisabled();
    });

    test('"Clear shopping cart" button is enabled when product is selected', async () => {
        await beerCheckbox.click();
        await expect(clearShoppingCartButton).toBeEnabled();
    });

    test('"Clear shopping cart" button removes selection from all products', async () => {
        await beerCheckbox.click();
        await cigarCheckbox.click();
        await clearShoppingCartButton.click();
        await expect(beerCheckbox).not.toBeChecked();
        await expect(cigarCheckbox).not.toBeChecked();
        expect(await productList.getSelectedProductsCount()).toBe(0);
    });

    test('"Proceed to checkout" button is disabled when there is no products selected', async () => {
        await expect(proceedToCheckoutButton).toBeDisabled();
        await beerCheckbox.click();
        await beerCheckbox.click();
        await expect(proceedToCheckoutButton).toBeDisabled();
    });

    test('"Proceed to checkout" button is enabled when there is selected product', async () => {
        await beerCheckbox.click();
        await expect(proceedToCheckoutButton).toBeEnabled();
    });

    test('"Proceed to checkout" button redirects user to checkout', async () => {
        await beerCheckbox.click();
        await proceedToCheckoutButton.click();
        await expect(checkoutPage.checkoutLabel).toBeVisible();

        // additional cleanup
        await checkoutPage.returnBackButton.click();
    });

    test('"Proceed to checkout" button set correct products to be purchased', async () => {
        await beerCheckbox.click();
        await cigarCheckbox.click();
        await proceedToCheckoutButton.click();

        await expect(beerProduct).toBeVisible();
        await expect(cigarProduct).toBeVisible();
        await expect(iceCreamProduct).not.toBeVisible();
        expect(await productList.getSelectedProductsCount()).toBe(2);

        // additional cleanup
        await checkoutPage.returnBackButton.click();
    });

    test('"Proceed to checkout" button set correct total amount', async () => {
        await beerCheckbox.click();
        await cigarCheckbox.click();
        await proceedToCheckoutButton.click();

        expect(await productList.getTotalAmount()).toBe(beerPrice + cigarPrice);

        // additional cleanup
        await checkoutPage.returnBackButton.click();
    });

    test('Selected products remains selected after return from checkout', async () => {
        await beerCheckbox.click();
        await cigarCheckbox.click();
        await proceedToCheckoutButton.click();
        await checkoutPage.returnBackButton.click();

        await expect(beerCheckbox).toBeChecked();
        await expect(cigarCheckbox).toBeChecked();
        expect(await productList.getSelectedProductsCount()).toBe(2);
    });

    // TODO
    // test('Link to the product page works correctly', async ({ page }) => {
    // });
});