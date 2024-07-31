package query

import (
	"context"

	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"

	"product-query/internal/model"
)

type ProductQuery interface {
	GetAll(ctx context.Context) ([]model.Product, error)
	GetById(ctx context.Context, id string) (*model.Product, error)
}

type productQuery struct {
	Database *mongo.Database
}

func NewProductQuery(db *mongo.Database) ProductQuery {
	return &productQuery{
		Database: db,
	}
}

func (q *productQuery) GetAll(ctx context.Context) ([]model.Product, error) {
	collection := q.GetCollection()
	cursor, err := collection.Find(ctx, bson.D{})
	if err != nil {
		return nil, err
	}
	defer cursor.Close(ctx)

	var products []model.Product
	for cursor.Next(ctx) {
		var product model.Product
		if err := cursor.Decode(&product); err != nil {
			return nil, err
		}
		products = append(products, product)
	}

	if err := cursor.Err(); err != nil {
		return nil, err
	}

	return products, nil
}

func (q *productQuery) GetById(ctx context.Context, id string) (*model.Product, error) {
	objID, err := primitive.ObjectIDFromHex(id)
	if err != nil {
		return nil, err
	}

	result := q.GetCollection().FindOne(ctx, bson.D{{Key: "_id", Value: objID}})

	if result.Err() != nil && result.Err() != mongo.ErrNoDocuments {
		return nil, result.Err()
	}

	if result.Err() == mongo.ErrNoDocuments {
		return nil, nil
	}

	var product model.Product
	err = result.Decode(&product)
	if err != nil {
		return nil, err
	}
	return &product, nil
}

func (q *productQuery) GetCollection() *mongo.Collection {
	return q.Database.Collection("products")
}
