name: 构建
on: [push]
jobs:
  check_and_test:
    runs-on: ubuntu-latest
    steps:
       - uses: actions/checkout@v3
       - run: cd admin && npm install yarn
       - run: yarn install
       - run: echo 'build complete!'