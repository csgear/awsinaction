aws lambda create-function --function-name healthcheck-function --zip-file fileb://healthcheck-function.zip --profile vke-development

### deploy script

```
rm -rf ./dist && mkdir dist
cp -r ./src/* ./dist
cp package.json package-lock.json ./dist
cd dist
npm install --only=prod
zip -r ./healthcheck-function.zip .
aws lambda update-function-code --function-name healthcheck-function --zip-file fileb://healthcheck-function.zip --profile vke-development
cd ..
```

### clean 
