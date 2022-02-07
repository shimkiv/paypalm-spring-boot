module.exports = class BasketPage {
    static clearShoppingCartButtonSelector = '#clearShoppingCartButton';
    static proceedToCheckoutButtonSelector = 'button:has-text("Proceed to checkout")';

    constructor(page) {
        this.page = page;
        this.clearShoppingCartButton = page.locator(this.constructor.clearShoppingCartButtonSelector);
        this.proceedToCheckoutButton = page.locator(this.constructor.proceedToCheckoutButtonSelector);
    }

    /**
     * Presses "Clear shopping card" button, if it is enabled
     * @returns {Promise<void>}
     */
    async clearShoppingCartIfNotEmpty() {
        if(await this.clearShoppingCartButton.isEnabled()) { await this.clearShoppingCartButton.click(); }
    }
};