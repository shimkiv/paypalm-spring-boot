const TOTAL_PRODUCTS_SELECTED = 'Total products selected:';
const TOTAL_AMOUNT = 'Total amount:';

/**
 * Appears both on the basket page and checkout page.
 * The only difference is that on the checkout page there is no checkboxes
 */
module.exports = class ProductList {
    static productCheckboxSelector = 'span.check';
    static selectedProductsCountSelector = `p:has-text("${TOTAL_PRODUCTS_SELECTED}")`;
    static totalAmountSelector = `p:has-text("${TOTAL_AMOUNT}")`;
    static productPriceSelector = 'td:has-text("USD")'; // TODO - change when other currencies will be implemented

    constructor(page) {
        this.page = page;
        this.selectedProductCount = page.locator(this.constructor.selectedProductsCountSelector);
        this.totalAmount = page.locator(this.constructor.totalAmountSelector);
    }

    /**
     * Returns row element which contains checkbox, product name and price
     * @param productName - name of the product
     * @returns {Object} Playwright locator
     */
    getProductBar(productName) {
        return this.page.locator(`tr:has-text("${productName}")`);
    }

    /**
     * Returns element with checkbox related to given product
     * @param {Object | string } product - product element, represented by Playwright locator, or name of that product
     * @returns {Object} Playwright locator
     */
    getProductCheckbox(product) {
        product = typeof product === 'string' ? this.getProductBar(product) : product;
        return product.locator(this.constructor.productCheckboxSelector);
    }

    /**
     * Returns a number which represent amount of selected products, according to "Total products selected" label
     * @returns {Promise<number>} - resolves to a number of selected products
     */
    async getSelectedProductsCount () {
        const selectedProductsCountText = await this.selectedProductCount.innerText();
        return parseInt(selectedProductsCountText.replace(TOTAL_PRODUCTS_SELECTED, '').trim());
    }

    /**
     * Returns total price of selected products, according to "Total amount" label
     * ! Currently works with USD only. If other currencies will be added, implementation needs to be changed!
     * @returns {Promise<number>}
     */
    async getTotalAmount() {
        const totalAmountText = await this.totalAmount.innerText();
        return Number(totalAmountText.replace(TOTAL_AMOUNT, '').replace('USD', '').trim());
    }

    /**
     * Returns price of given product, as listed on the page
     * @param {Object | string } product - product element, represented by Playwright locator, or name of that product
     * @returns {Promise<number>} - resolves to product price
     */
    async getProductPrice(product) {
        product = typeof product === 'string' ? this.getProductBar(product) : product;
        const productPriceText = await product.locator(this.constructor.productPriceSelector).innerText();
        return Number(productPriceText.replace('USD', '').trim());
    }

};