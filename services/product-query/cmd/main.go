package main

import (
	"context"
	"log"
	"os"

	"github.com/joho/godotenv"
	"github.com/labstack/echo/v4"

	"product-query/internal/controller"
	"product-query/internal/database"
	"product-query/internal/query"
)

func main() {
	err := godotenv.Load("../.env")
	if err != nil {
		log.Panic("Error loading .env file")
	}

	router := echo.New()
	ctx := context.Background()

	db, err := database.NewDatabase(ctx, os.Getenv("MONGODB_NAME"), os.Getenv("MONGODB_URI"))
	if err != nil {
		log.Panic("Error connecting database")
	}

	productQuery := query.NewProductQuery(db)

	Handler(controller.NewHttpServer(productQuery), router)
	router.Logger.Fatal(router.Start(":3000"))
}

type ServerInterface interface {
	GetAll(c echo.Context) error
	GetById(c echo.Context) error
}

func Handler(si ServerInterface, router *echo.Echo) {
	if router == nil {
		router = echo.New()
	}

	api := router.Group("/api/v1")

	api.GET("/products", si.GetAll)
	api.GET("/products/:id", si.GetById)
}
