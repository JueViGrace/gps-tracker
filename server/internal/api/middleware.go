package api

import (
	"gps-tracker/internal/data"
	"gps-tracker/internal/types"

	"github.com/gofiber/fiber/v2"
)

func (a *api) authenticatedHandler(handler types.AuthDataHandler) fiber.Handler {
	return func(c *fiber.Ctx) error {
		data, err := getUserDataForReq(c, a.db)
		if err != nil {
			res := types.RespondUnauthorized(nil, err.Error())
			return c.Status(res.Status).JSON(res)
		}

		return handler(c, data)
	}
}

func getUserDataForReq(c *fiber.Ctx, db data.Storage) (*types.AuthData, error) {
	jwt, err := types.ExtractJWTFromHeader(c, func(s string) {
		db.AuthStore().DeleteSessionByToken(s)
	})
	if err != nil {
		return nil, err
	}

	session, err := db.AuthStore().GetSessionById(jwt.Claims.SessionID)
	if err != nil {
		return nil, err
	}

	return &types.AuthData{
		SessionID: session.ID,
	}, nil
}
