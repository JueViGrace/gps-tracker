package data

import (
	"context"
	"gps-tracker/internal/database"
	"gps-tracker/internal/types"

	"github.com/google/uuid"
)

type AuthStore interface {
	GetSessionById(id uuid.UUID) (session *types.Session, err error)
	CreateSession() (session *types.AuthResponse, err error)
	Refresh(sessionId uuid.UUID) (session *types.AuthResponse, err error)
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

func (s *authStore) CreateSession() (session *types.AuthResponse, err error) {
	sessionId, err := uuid.NewV7()
	if err != nil {
		return nil, err
	}

	session, err = createTokens(sessionId)
	if err != nil {
		return nil, err
	}

	err = s.queries.CreateSession(s.ctx, database.CreateSessionParams{
		ID:           session.ID.String(),
		RefreshToken: session.RefreshToken,
		AccessToken:  session.AccessToken,
	})
	if err != nil {
		return nil, err
	}

	return
}

func (s *authStore) Refresh(id uuid.UUID) (session *types.AuthResponse, err error) {
	savedSession, err := s.GetSessionById(id)
	if err != nil {
		return nil, err
	}

	session, err = createTokens(savedSession.ID)
	if err != nil {
		return nil, err
	}

	err = s.queries.UpdateSession(s.ctx, database.UpdateSessionParams{
		ID:           session.ID.String(),
		RefreshToken: session.RefreshToken,
		AccessToken:  session.AccessToken,
	})
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

func createTokens(sessionId uuid.UUID) (*types.AuthResponse, error) {

	accessToken, err := types.CreateAccessToken(sessionId)
	if err != nil {
		return nil, err
	}

	refreshToken, err := types.CreateRefreshToken(sessionId)
	if err != nil {
		return nil, err
	}

	return &types.AuthResponse{
		ID:           sessionId,
		AccessToken:  accessToken,
		RefreshToken: refreshToken,
	}, nil
}
