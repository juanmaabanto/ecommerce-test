package model

type Product struct {
	ID    string  `bson:"_id" json:"id"`
	Name  string  `bson:"name" json:"name"`
	Price float32 `bson:"price" json:"price"`
	Stock int     `bson:"stock" json:"stock"`
}
