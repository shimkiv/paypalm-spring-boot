module.exports = class LoginPage {
    static loginFieldSelector = '#userName';
    static passwordFieldSelector = '#userPassword';
    static submitButtonSelector = '[type="submit"]';
    static signInHeadingSelector = '.form-signin-heading';
    static incorrectCredentialsAlertSelector = '.alert-danger';
    static incorrectCredentialsAlertCloseBtnSelector = `${LoginPage.incorrectCredentialsAlertSelector} > [data-dismiss="alert"]`;

    constructor(page) {
        this.page = page;
        this.submitBtn = page.locator(this.constructor.submitButtonSelector);
        this.loginField = page.locator(this.constructor.loginFieldSelector);
        this.passwordField = page.locator(this.constructor.passwordFieldSelector);
        this.signInHeading = page.locator(this.constructor.signInHeadingSelector);
        this.incorrectCredentialsAlert = page.locator(this.constructor.incorrectCredentialsAlertSelector);
        this.incorrectCredentialsAlertCloseBtn = page.locator(this.constructor.incorrectCredentialsAlertCloseBtnSelector);
    }

    /**
     * Performs login attempt with given username and password.
     * If no username/password given, does not touch it's field.
     * @param {string} [username]
     * @param {string} [password]
     * @returns {Promise<void>}
     */
    async loginAs({username, password}) {
        if (username) {
            await this.loginField.fill(username)
        }
        if (password) {
            await this.passwordField.fill(password)
        }
        await this.submitBtn.click();
    }
};