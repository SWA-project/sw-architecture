# Simple Frontend for testing Credig Banking system capabilities

## Quick start

First, start the server-side by following the [main instructions](https://github.com/SWA-project/sw-architecture) of this project.

1. Install dependencies with `npm install`.
2. Run `npm start ` to run the app in dev mode. Open [http://localhost:3001](http://localhost:3001) with your browser.

You are able to send new orders here and start/stop request stream to test capabilites of the system.

This client will only communicate with order-service that runs as a orchestrator of this system, making a saga per request sent from the client. 