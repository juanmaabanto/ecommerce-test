import { Schema } from 'mongoose';

export const CustomerSchema = new Schema({
  name: String,
  address: String,
});