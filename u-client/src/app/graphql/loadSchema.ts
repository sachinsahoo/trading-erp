// const { loadSchema } = require('@graphql-tools/load');
// const { UrlLoader } = require('@graphql-tools/url-loader');
// const { JsonFileLoader } = require('@graphql-tools/json-file-loader');
// const { GraphQLFileLoader } = require('@graphql-tools/graphql-file-loader');

// const schema1 = await loadSchema('type A { foo: String }');   // load from string w/ no loaders

// const schema2 = await loadSchema('http://localhost:3000/graphql', {   // load from endpoint
//     loaders: [
//         new UrlLoader()
//     ]
// });


/*

npx gql-gen --schema https://trading.urvaru.com/graphql --template graphql-codegen-introspection-template --out schema.json