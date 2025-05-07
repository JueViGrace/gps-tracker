package api

import (
	"gps-tracker/internal/data"
	"gps-tracker/internal/types"
	"gps-tracker/internal/util"

	"github.com/gofiber/fiber/v2"
)

type authHandler struct {
	db        data.AuthStore
	validator *util.XValidator
}

func (a *api) authRoutes(api fiber.Router) {
	group := api.Group("/auth")
	handler := newAuthHandler(a.db.AuthStore(), a.validator)

	group.Post("/login", handler.login)
	group.Post("/refresh", a.authenticatedHandler(handler.refresh))

}

func newAuthHandler(db data.AuthStore, validator *util.XValidator) *authHandler {
	return &authHandler{
		db:        db,
		validator: validator,
	}
}

func (h *authHandler) login(c *fiber.Ctx) error {
	res := new(types.APIResponse)

	session, err := h.db.CreateSession()
	if err != nil {
		res = types.RespondBadRequest(nil, err.Error())
		return c.Status(res.Status).JSON(res)

	}

	res = types.RespondCreated(session, "Success")
	return c.Status(res.Status).JSON(res)
}

func (h *authHandler) refresh(c *fiber.Ctx, data *types.AuthData) error {
	res := new(types.APIResponse)

	session, err := h.db.Refresh(data.SessionID)
	if err != nil {
		res = types.RespondBadRequest(nil, err.Error())
		return c.Status(res.Status).JSON(res)

	}

	res = types.RespondCreated(session, "Success")
	return c.Status(res.Status).JSON(res)
}
