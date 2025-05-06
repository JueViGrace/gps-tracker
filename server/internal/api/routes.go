package api

import (
	"gps-tracker/internal/types"
	"time"

	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/fiber/v2/middleware/monitor"
)

func (a *api) RegisterRoutes() {
	api := a.Group("/api")

	api.Get("/health", a.HealthRoute)
	api.Get("/metrics", monitor.New(monitor.Config{
		Refresh: time.Duration(time.Second),
	}))
}

func (a *api) HealthRoute(c *fiber.Ctx) error {
	res := types.RespondOk(a.db.Health(), "Success")
	return c.Status(res.Status).JSON(res)
}
