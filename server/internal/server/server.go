package server

import (
	"github.com/gofiber/fiber/v2"

	"gps-tracker/internal/database"
)

type FiberServer struct {
	*fiber.App

	db database.Service
}

func New() *FiberServer {
	server := &FiberServer{
		App: fiber.New(fiber.Config{
			ServerHeader: "gps-tracker",
			AppName:      "gps-tracker",
		}),

		db: database.New(),
	}

	return server
}
