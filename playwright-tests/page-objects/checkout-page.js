module.exports = class CheckoutPage {
    static checkoutLabelSelector = 'h3:has-text("Checkout")';
    static returnBackButtonSelector = '#returnBack';
    static payButtonSelector = '#payButton';
    static cardNumberFieldSelector = '#cardNumber';
    static expMonthDropdownSelector = '#expMonth';
    static expYearDropdownSelector = '#expYear';
    static cardCvvFieldSelector = '#cardCvv';
    static cardHolderNameFieldSelector = '#cardHolderName';

    constructor(page) {
        this.page = page;
        this.checkoutLabel = page.locator(this.constructor.checkoutLabelSelector);
        this.returnBackButton = page.locator(this.constructor.returnBackButtonSelector);
        this.payButton = page.locator(this.constructor.payButtonSelector);
        this.cardNumberField = page.locator(this.constructor.cardNumberFieldSelector);
        this.expMonthDropdown = page.locator(this.constructor.expMonthDropdownSelector);
        this.expYearDropdown = page.locator(this.constructor.expYearDropdownSelector);
        this.cardCvvField = page.locator(this.constructor.cardCvvFieldSelector);
        this.cardHolderNameField = page.locator(this.constructor.cardHolderNameFieldSelector);
    }

    /**
     * Returns total price of selected products, according to "Pay" button
     * ! Currently works with USD only. If other currencies will be added, implementation needs to be changed!
     * @returns {Promise<number>}
     */
    async getAmountOnPayButton() {
        const payButtonText = await this.payButton.innerText();
        return Number(payButtonText.replace('PAY USD', '').trim());
    }
};