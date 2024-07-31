import { Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Model, Document } from 'mongoose';
import { Customer } from './customer.dto';
import { Customer as CustomerModel } from './customer.interface';

@Injectable()
export class CustomerService {
  constructor(
    @InjectModel('Customer') private readonly customerModel: Model<CustomerModel>
  ) {}

  async findAll(): Promise<Customer[]> {
    const customers = await this.customerModel.find().exec();
    return customers.map(customer => this.toGraphQLCustomer(customer));
  }

  async findById(id: string): Promise<Customer> {
    const customer = await this.customerModel.findById(id).exec();
    return this.toGraphQLCustomer(customer);
  }

  private toGraphQLCustomer(customer: Document & CustomerModel): Customer {
    return {
      id: customer._id.toString(),
      name: customer.name,
      address: customer.address,
    };
  }
}