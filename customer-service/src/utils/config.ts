import 'dotenv/config';

const DATABASE_URL: string = process.env.DATABASE_URL || '';
const PORT: string = process.env.PORT || '3000';

export {
  DATABASE_URL,
  PORT
};

export default {
  DATABASE_URL,
  PORT
};

