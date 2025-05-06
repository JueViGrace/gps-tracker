package data

import (
	"context"
	"gps-tracker/internal/database"
	"gps-tracker/internal/types"

	"github.com/google/uuid"
)

type AuthStore interface {
	GetSessionById(id uuid.UUID) (session *types.Session, err error)
	DeleteSession(id uuid.UUID) (err error)
	DeleteSessionByToken(token string) (err error)
}

func (s *storage) AuthStore() AuthStore {
	return NewAuthStore(s.ctx, s.queries)
}

type authStore struct {
	ctx     context.Context
	queries *database.Queries
}

func NewAuthStore(ctx context.Context, queries *database.Queries) AuthStore {
	return &authStore{
		ctx:     ctx,
		queries: queries,
	}
}

func (s *authStore) GetSessionById(id uuid.UUID) (session *types.Session, err error) {
	session = new(types.Session)

	dbSession, err := s.queries.GetSessionById(s.ctx, id.String())
	if err != nil {
		return nil, err
	}

	session, err = types.DbSessionToSession(&dbSession)
	if err != nil {
		return nil, err
	}

	return
}

func (s *authStore) DeleteSession(id uuid.UUID) (err error) {
	err = s.queries.DeleteSessionById(s.ctx, id.String())
	if err != nil {
		return err
	}

	return
}

func (s *authStore) DeleteSessionByToken(token string) (err error) {
	err = s.queries.DeleteSessionByToken(s.ctx, database.DeleteSessionByTokenParams{
		RefreshToken: token,
		AccessToken:  token,
	})
	if err != nil {
		return err
	}

	return
}
