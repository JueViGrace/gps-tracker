package types

import (
	"gps-tracker/internal/database"
	"time"

	"github.com/google/uuid"
)

type TrackingResponse struct {
	Latitude  float64   `json:"latitude"`
	Longitude float64   `json:"longitude"`
	Altitude  float64   `json:"altitude"`
	Time      time.Time `json:"time"`
	SessionID uuid.UUID `json:"session_id"`
}

type TrackingRequest struct {
	Latitude  float64   `json:"latitude"`
	Longitude float64   `json:"longitude"`
	Altitude  float64   `json:"altitude"`
	Time      time.Time `json:"time"`
}

func (t *TrackingRequest) MapToResponse(sessionId uuid.UUID) *TrackingResponse {
	return &TrackingResponse{
		Latitude:  t.Latitude,
		Longitude: t.Longitude,
		Altitude:  t.Altitude,
		Time:      t.Time,
		SessionID: sessionId,
	}
}

func DbTrackingToTracking(db *database.SessionTracking) (*TrackingResponse, error) {
	time, err := time.Parse(time.DateTime, db.Time)
	if err != nil {
		return nil, err
	}

	sessionId, err := uuid.Parse(db.SessionID)
	if err != nil {
		return nil, err
	}

	return &TrackingResponse{
		Latitude:  db.Latitude,
		Longitude: db.Longitude,
		Altitude:  db.Altitude,
		Time:      time,
		SessionID: sessionId,
	}, nil
}
