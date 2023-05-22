package com.knits.product.service;

import com.knits.product.dto.TeamDto;
import com.knits.product.exception.ExceptionCodes;
import com.knits.product.exception.UserException;
import com.knits.product.mapper.TeamMapper;
import com.knits.product.mapper.UserMapper;
import com.knits.product.model.Team;
import com.knits.product.repository.TeamRepository;
import com.knits.product.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing {@link com.knits.product.model.Team}.
 */
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class TeamService {

    private UserMapper userMapper;

    private TeamMapper teamMapper;

    private TeamRepository teamRepository;

    /**
     * Save a team.
     *
     * @param teamDto the entity to save.
     * @return the persisted entity.
     */
    public TeamDto save(TeamDto teamDto) {
        log.debug("Request to save Team: {}", teamDto);
        Team team = teamMapper.toEntity(teamDto);
        team = teamRepository.save(team);
        return teamMapper.toDto(team);
    }

    /**
     * Partially updates a team.
     *
     * @param teamDto the entity to update partially.
     * @return the persisted entity.
     */
    public TeamDto partialUpdate(TeamDto teamDto) {
        log.debug("Request to partially update Team: {}", teamDto);
        Team team = teamRepository.findById(teamDto.getId()).orElseThrow(() ->
                new UserException("Team#" + teamDto.getId() + " not found"));
        teamMapper.partialUpdate(team, teamDto);

        // TODO: Manage Team relationships to check updates
        teamRepository.save(team);
        return teamMapper.toDto(team);
    }

    /**
     * Overrides fields, including nulls.
     *
     * @param teamDto the entity to update.
     * @return the persisted entity.
     */
    public TeamDto update(TeamDto teamDto) {
        log.debug("Request to update Team: {}", teamDto);
        Team team = teamRepository.findById(teamDto.getId()).orElseThrow(() ->
                new UserException("Team#" + teamDto.getId() + " not found"));
        teamMapper.update(team, teamDto);

        // TODO: Manage Team relationships to check updates
        teamRepository.save(team);
        return teamMapper.toDto(team);
    }

    public List<TeamDto> findAll() {
        return teamRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
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
    public TeamDto findById(Long id) {
        log.debug("Request Team by id: {}", id);
        Team team = teamRepository.findById(id).orElseThrow(() ->
                new UserException("Team#" + id + " not found", ExceptionCodes.TEAM_NOT_FOUND));
        return toDto(team);
    }


    private TeamDto toDto(Team team) {
        TeamDto teamDto = teamMapper.toDto(team);
        teamDto.setUsers(userMapper.toDtoList(team.getUsers()));
        return teamDto;
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

    /**
     * Get all the teams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<TeamDto> search(Pageable pageable) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
