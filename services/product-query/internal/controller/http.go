package controller

import "product-query/internal/query"

type HttpServer struct {
	productQuery query.ProductQuery
}

func NewHttpServer(productQuery query.ProductQuery) HttpServer {
	return HttpServer{
		productQuery: productQuery,
	}
}
