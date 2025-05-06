package api

import (
	"fmt"
	"gps-tracker/internal/data"
	"gps-tracker/internal/types"
	"gps-tracker/internal/util"
	"strings"

	"github.com/gofiber/fiber/v2"
)

type trackingHandler struct {
	db        data.Storage
	validator *util.XValidator
}

func (a *api) TrackingRoutes(api fiber.Router) {
	group := api.Group("/tracking")
	handler := NewTrackingHandler(a.db, a.validator)

	group.Post("/location", a.authenticatedHandler(handler.RegisterLocation))
	group.Get("/", a.authenticatedHandler(handler.GetTracking))
}

func NewTrackingHandler(db data.Storage, validator *util.XValidator) *trackingHandler {
	return &trackingHandler{
		validator: validator,
	}
}

func (h *trackingHandler) GetTracking(c *fiber.Ctx, data *types.AuthData) (err error) {
	res := new(types.APIResponse)

	trackings, err := h.db.TrackingStore().GetTrackings(data.SessionID)
	if err != nil {
		res = types.RespondBadRequest(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	res = types.RespondOk(trackings, "Success")
	return c.Status(res.Status).JSON(res)
}

func (h *trackingHandler) RegisterLocation(c *fiber.Ctx, data *types.AuthData) (err error) {
	req := new(types.TrackingRequest)
	res := new(types.APIResponse)

	if err = c.BodyParser(req); err != nil {
		res = types.RespondBadRequest(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	if errs := h.validator.Validate(req); len(errs) > 0 {
		errMsgs := make([]string, 0)

		for _, err := range errs {
			errMsgs = append(errMsgs, fmt.Sprintf(
				"[%s]: '%v' | Needs to implement '%s'",
				err.FailedField,
				err.Value,
				err.Tag,
			))
		}

		res = types.RespondBadRequest(nil, strings.Join(errMsgs, " and "))
		return c.Status(res.Status).JSON(res)
	}

	// TODO: register location in the database

	res = types.RespondCreated("Received!", "Success")
	return c.Status(res.Status).JSON(res)
}
