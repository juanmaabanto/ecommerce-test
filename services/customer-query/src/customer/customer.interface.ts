import { Document } from 'mongoose';

export interface Customer extends Document {
  readonly _id: string;
  readonly name: string;
  readonly address: string;
}