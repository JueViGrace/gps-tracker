package types

import (
	"gps-tracker/internal/database"

	"github.com/gofiber/fiber/v2"
	"github.com/google/uuid"
)

type AuthDataHandler = func(*fiber.Ctx, *AuthData) error

type AuthData struct {
	SessionID uuid.UUID
}

type AuthResponse struct {
	ID           uuid.UUID `json:"id"`
	AccessToken  string    `json:"access_token"`
	RefreshToken string    `json:"refresh_token"`
}

type Session struct {
	ID           uuid.UUID
	AccessToken  string
	RefreshToken string
}

func DbSessionToSession(db *database.Session) (*Session, error) {
	id, err := uuid.Parse(db.ID)
	if err != nil {
		return nil, err
	}

	return &Session{
		ID:           id,
		AccessToken:  db.AccessToken,
		RefreshToken: db.RefreshToken,
	}, nil
}
