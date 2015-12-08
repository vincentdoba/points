module.exports = {
    "extends": "standard",
    "plugins": [
        "standard"
    ],
    "ecmaFeatures": {
      "jsx": true
    },
    "rules": {
      "semi": [2, "always"],
      "no-unused-vars": 1,
      "quotes": [2, "double"],
      "space-before-function-paren": [2, "never"],
      "no-multiple-empty-lines": [2, {"max": 3, "maxEOF": 1}]
    }
};
