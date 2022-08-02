module.exports = {
    "env": {
        "browser": true,
        "es2021": true,
    },
    "extends": ["naver"],
    "parser": "@typescript-eslint/parser",
    "parserOptions": {
        "ecmaFeatures": {
            "jsx": true,
        },
        "ecmaVersion": "latest",
        "sourceType": "module",
    },
    "plugins": [
        "react",
        "@typescript-eslint",
    ],
    "rules": {
        "linebreak-style": "off",
        "indent": "off",
        "array-element-newline": "off",
        "quotes": "warn",
    },
};
