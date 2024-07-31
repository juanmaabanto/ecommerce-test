package controller

import (
	"net/http"

	"github.com/labstack/echo/v4"
)

func (h HttpServer) GetAll(c echo.Context) error {
	result, err := h.productQuery.GetAll(c.Request().Context())
	if err != nil {
		return c.JSON(http.StatusInternalServerError, err.Error())
	}

	return c.JSON(http.StatusOK, result)
}

func (h HttpServer) GetById(c echo.Context) error {
	id := c.Param("id")
	result, err := h.productQuery.GetById(c.Request().Context(), id)
	if err != nil {
		return c.JSON(http.StatusInternalServerError, err.Error())
	}

	if result == nil {
		return c.JSON(http.StatusNotFound, "Not Found")
	}

	return c.JSON(http.StatusOK, result)
}
