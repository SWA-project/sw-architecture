FROM node:16.13.2-alpine3.14 as ts-compiler
WORKDIR /usr/app
COPY package*.json ./
COPY tsconfig*.json ./
RUN npm install
COPY . ./
RUN npm run build

FROM node:16.13.2-alpine3.14 as ts-remover
WORKDIR /usr/app
COPY --from=ts-compiler /usr/app/package*.json ./
COPY --from=ts-compiler /usr/app/build ./
RUN npm install --only=production

FROM node:16.13.2-alpine3.14
WORKDIR /usr/app
COPY --from=ts-remover /usr/app ./

CMD ["index.js"]