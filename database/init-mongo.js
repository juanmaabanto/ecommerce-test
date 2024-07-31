db = db.getSiblingDB('ecommerce');

db.createUser({
  user: "test",
  pwd: "testPWD",
  roles: [{ role: "readWrite", db: "ecommerce" }]
});

db.customers.insertMany([
  { _id: ObjectId("60d5f483f1e1b6c9b4f6c98f"), name: "Juan Abanto", address: "Calle Los Tulipanes" },
  { _id: ObjectId("60d5f483f1e1b6c9b4f6c990"), name: "Juan Perez", address: "Calle Las Petunias" },
  { _id: ObjectId("60d5f483f1e1b6c9b4f6c991"), name: "Jhon Smith", address: "Calle Washington" }
]);

db.products.insertMany([
  { _id: ObjectId("60d5f483f1e1b6c9b4f6c992"), name: 'Laptop HP', price: 1000, stock: 100 },
  { _id: ObjectId("60d5f483f1e1b6c9b4f6c993"), name: 'MackBook Pro', price: 2000, stock: 50 },
  { _id: ObjectId("60d5f483f1e1b6c9b4f6c994"), name: 'Mouse Logitech', price: 100, stock: 10 },
  { _id: ObjectId("60d5f483f1e1b6c9b4f6c995"), name: 'KeyBoard', price: 100, stock: 10 },
  { _id: ObjectId("60d5f483f1e1b6c9b4f6c996"), name: 'Backpack', price: 50, stock: 2 }
]);