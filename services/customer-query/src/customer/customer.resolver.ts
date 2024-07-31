import { Resolver, Query, Args } from '@nestjs/graphql';
import { CustomerService } from './customer.service';
import { Customer } from './customer.dto';

@Resolver(() => Customer)
export class CustomerResolver {
  constructor(private readonly customerService: CustomerService) {}

  @Query(() => [Customer])
  async customers(): Promise<Customer[]> {
    return this.customerService.findAll();
  }

  @Query(() => Customer)
  async customer(@Args('id') id: string): Promise<Customer> {
    return this.customerService.findById(id);
  }
}