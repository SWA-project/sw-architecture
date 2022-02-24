FROM ubuntu:18.04

EXPOSE 3001

WORKDIR /usr/src/app

RUN apt-get update && apt-get install -y curl
RUN curl -sL https://deb.nodesource.com/setup_lts.x | bash -
RUN apt install -y nodejs

COPY . .

RUN npm install

CMD  ["npm", "start"]