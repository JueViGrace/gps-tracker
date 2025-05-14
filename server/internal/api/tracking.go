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
	db        data.TrackingStore
	validator *util.XValidator
}

func (a *api) trackingRoutes(api fiber.Router) {
	group := api.Group("/tracking")
	handler := NewTrackingHandler(a.db.TrackingStore(), a.validator)

	group.Post("/register", a.authenticatedHandler(handler.registerLocation))
	group.Get("/", a.authenticatedHandler(handler.getTracking))
}

func NewTrackingHandler(db data.TrackingStore, validator *util.XValidator) *trackingHandler {
	return &trackingHandler{
		db:        db,
		validator: validator,
	}
}

func (h *trackingHandler) getTracking(c *fiber.Ctx, data *types.AuthData) (err error) {
	res := new(types.APIResponse)

	trackings, err := h.db.GetTrackings(data.SessionID)
	if err != nil {
		res = types.RespondBadRequest(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	res = types.RespondOk(trackings, "Success")
	return c.Status(res.Status).JSON(res)
}

func (h *trackingHandler) registerLocation(c *fiber.Ctx, data *types.AuthData) (err error) {
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

	err = h.db.CreateTracking(data.SessionID, req)
	if err != nil {
		res = types.RespondNotFound(nil, err.Error())
		return c.Status(res.Status).JSON(res)
	}

	res = types.RespondCreated("Received!", "Success")
	return c.Status(res.Status).JSON(res)
}
