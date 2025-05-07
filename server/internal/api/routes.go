package api

import (
	"gps-tracker/internal/types"
	"time"

	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/fiber/v2/middleware/monitor"
)

func (a *api) registerRoutes() {
	api := a.Group("/api")

	api.Get("/health", a.healthRoute)
	api.Get("/metrics", monitor.New(monitor.Config{
		Refresh: time.Duration(time.Second),
	}))

	a.authRoutes(api)
	a.trackingRoutes(api)
}

func (a *api) healthRoute(c *fiber.Ctx) error {
	res := types.RespondOk(a.db.Health(), "Success")
	return c.Status(res.Status).JSON(res)
}
