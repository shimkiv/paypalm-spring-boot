module.exports = class PaymentProcessingDialog {
    static titleSelector = '.modal-title:has-text("Payment processing")';
    static paymentProcessingInfoSelector = '#paymentProcessingInfo';
    static closeButtonSelector = '#paymentProcessingDialogCloseButton';

    constructor(page) {
        this.page = page;
        this.title = page.locator(this.constructor.titleSelector);
        this.paymentProcessingInfo = page.locator(this.constructor.paymentProcessingInfoSelector);
        this.closeButton = page.locator(this.constructor.closeButtonSelector);
    }
};