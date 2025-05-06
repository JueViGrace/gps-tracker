package api

import (
	"context"
	"fmt"
	"os"
	"strconv"

	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/fiber/v2/middleware/cors"
	"github.com/gofiber/fiber/v2/middleware/logger"
)

type Api interface {
	Start() error
	ShutDownWithContext(ctx context.Context) error
}

type api struct {
	*fiber.App
}

func New() Api {
	return &api{
		App: fiber.New(fiber.Config{
			ServerHeader: "GPSTrackerServer",
			AppName:      "GPSTrackerServer",
			ErrorHandler: func(e *fiber.Ctx, err error) error {
				// TODO: respond
				return nil
			},
		}),
	}
}

func (a *api) Start() error {
	port, err := strconv.Atoi(os.Getenv("SERVER_PORT"))
	if err != nil {
		return err
	}

	a.App.Use(logger.New())
	a.App.Use(cors.New())

	// TODO: add routes

	// this should go after registering app routes
	a.App.Use(func(c *fiber.Ctx) error {
		// TODO: respond
		return nil
	})

	err = a.Listen(fmt.Sprintf(":%d", port))
	if err != nil {
		return err
	}

	return nil
}

func (a *api) ShutDownWithContext(ctx context.Context) error {
	return a.ShutDownWithContext(ctx)
}
