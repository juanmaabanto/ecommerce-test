db = db.getSiblingDB('ecommerce');

db.createUser({
  user: "test",
  pwd: "testPWD",
  roles: [{ role: "readWrite", db: "ecommerce" }]
});

db.customers.insertMany([
  { _id: 1, name: "Juan Abanto", address: "Calle Los Tulipanes" },
  { _id: 2, name: "Juan Perez", address: "Calle Las Petunias" },
  { _id: 3, name: "Jhon Smith", address: "Calle Washington" }
]);

db.products.insertMany([
  { _id: 1, name: 'Laptop HP', price: '1000', stock: '100' },
  { _id: 2, name: 'MackBook Pro', price: '2000', stock: '50' },
  { _id: 3, name: 'Mouse Logitech', price: '100', stock: '10' },
  { _id: 4, name: 'KeyBoard', price: '100', stock: '10' },
  { _id: 5, name: 'Backpack', price: '50', stock: '2' }
])