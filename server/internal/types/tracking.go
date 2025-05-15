package types

import (
	"gps-tracker/internal/database"

	"github.com/google/uuid"
)

type TrackingResponse struct {
	Latitude  float64   `json:"latitude"`
	Longitude float64   `json:"longitude"`
	Altitude  float64   `json:"altitude"`
	Time      string    `json:"time"`
	SessionID uuid.UUID `json:"session_id"`
}

type TrackingRequest struct {
	Latitude  float64 `json:"latitude"`
	Longitude float64 `json:"longitude"`
	Altitude  float64 `json:"altitude"`
	Time      string  `json:"time"`
}

func DbTrackingToTracking(db *database.SessionTracking) (*TrackingResponse, error) {
	sessionId, err := uuid.Parse(db.SessionID)
	if err != nil {
		return nil, err
	}

	return &TrackingResponse{
		Latitude:  db.Latitude,
		Longitude: db.Longitude,
		Altitude:  db.Altitude,
		Time:      db.Time,
		SessionID: sessionId,
	}, nil
}
