export default class PaymentProcessingPage {
  constructor(page) {
    this.page = page;
    this.paymentProcessingModal = page.locator('#payment-processing-dialog .modal-content');
    this.errorText = page.locator('h1');
    this.errorReason = page.locator('.error-message');
    this.closeModalButton = page.locator('#paymentProcessingDialogCloseButton');
  }
}
