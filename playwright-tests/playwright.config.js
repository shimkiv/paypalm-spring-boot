const config = {
    use: {
        baseURL: require('./test-data/url').baseURL,
    },
    globalSetup: require.resolve('./global-setup'),
};

module.exports = config;