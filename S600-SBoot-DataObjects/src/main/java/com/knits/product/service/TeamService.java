package com.knits.product.service;

import com.knits.product.exception.ExceptionCodes;
import com.knits.product.exception.UserException;
import com.knits.product.model.Team;
import com.knits.product.repository.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for managing {@link com.knits.product.model.Team}.
 */
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class TeamService {

    private TeamRepository teamRepository;

    /**
     * Save a team.
     *
     * @param team the entity to save.
     * @return the persisted entity.
     */
    public Team save(Team team) {
        log.debug("Request to save Team: {}", team);
        return teamRepository.save(team);
    }

    /**
     * Partially updates a team.
     *
     * @param team the entity to update partially.
     * @return the persisted entity.
     */
    public Team partialUpdate(Team team) {
        log.debug("Request to partially update Team: {}", team);
        return teamRepository.save(team);
    }

    /**
     * Overrides fields, including nulls.
     *
     * @param team the entity to update.
     * @return the persisted entity.
     */
    public Team update(Team team) {
        log.debug("Request to partially update Team: {}", team);
        return teamRepository.save(team);
    }

    public List<Team> findAll() {
        return teamRepository.findWithUsers();
    }

    public List<Team> findAllAsEntities() {
        return teamRepository.findAll();
    }

    /**
     * Get a team by the "id".
     *
     * @param id the id of the team.
     * @return the team.
     */
    public Team findById(Long id) {
        log.debug("Request Team by id: {}", id);
        return teamRepository.findById(id).orElseThrow(() ->
                new UserException("Team#" + id + " not found", ExceptionCodes.TEAM_NOT_FOUND));
    }



    /**
     * Delete a team by the "id".
     *
     * @param id the id of the team.
     */
    public void delete(Long id) {
        log.debug("Delete Team by id: {}", id);
        teamRepository.deleteById(id);
    }


}
