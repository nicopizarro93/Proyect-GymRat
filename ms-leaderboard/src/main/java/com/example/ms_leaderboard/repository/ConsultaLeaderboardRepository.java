package com.example.ms_leaderboard.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.ms_leaderboard.model.ConsultaLeaderboard;

/**
 * Repositorio encargado de gestionar las consultas al leaderboard.
 * Proporciona operaciones CRUD para la entidad ConsultaLeaderboard.
 */
public interface ConsultaLeaderboardRepository extends CrudRepository<ConsultaLeaderboard, Long> {

}
