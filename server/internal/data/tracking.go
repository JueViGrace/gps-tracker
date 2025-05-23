package data

import (
	"context"
	"gps-tracker/internal/database"
	"gps-tracker/internal/types"
	"gps-tracker/internal/util"
	"time"

	"github.com/google/uuid"
)

type TrackingStore interface {
	GetTrackings(sessionId uuid.UUID) (trackings []types.TrackingResponse, err error)
	CreateTracking(sessionId uuid.UUID, t *types.TrackingRequest) (err error)
}

func (s *storage) TrackingStore() TrackingStore {
	return NewTrackingStore(s.ctx, s.queries)
}

type trackingStore struct {
	ctx     context.Context
	queries *database.Queries
}

func NewTrackingStore(ctx context.Context, queries *database.Queries) TrackingStore {
	return &trackingStore{
		ctx:     ctx,
		queries: queries,
	}
}

func (s *trackingStore) GetTrackings(sessionId uuid.UUID) (trackings []types.TrackingResponse, err error) {
	trackings = make([]types.TrackingResponse, 0)

	dbTrackings, err := s.queries.GetTrackingBySession(s.ctx, sessionId.String())
	if err != nil {
		return nil, err
	}

	for _, tr := range dbTrackings {
		tracking, err := types.DbTrackingToTracking(&tr)
		if err != nil {
			return nil, err
		}

		trackings = append(trackings, *tracking)
	}

	return
}

func (s *trackingStore) CreateTracking(sessionId uuid.UUID, t *types.TrackingRequest) (err error) {
	time, err := time.Parse(time.DateTime, t.Time)
	if err != nil {
		return err
	}

	params := database.CreateTrackingParams{
		Latitude:  t.Latitude,
		Longitude: t.Longitude,
		Altitude:  t.Altitude,
		Time:      util.FormatDate(time),
		SessionID: sessionId.String(),
	}

	err = s.queries.CreateTracking(s.ctx, params)
	if err != nil {
		return err
	}

	return
}
