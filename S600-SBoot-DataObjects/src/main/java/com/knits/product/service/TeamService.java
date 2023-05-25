package com.knits.product.service;

import com.knits.product.dto.TeamDto;
import com.knits.product.dto.UserDto;
import com.knits.product.exception.ExceptionCodes;
import com.knits.product.exception.UserException;
import com.knits.product.mapper.JpaMapperUtils;
import com.knits.product.mapper.TeamMapper;
import com.knits.product.mapper.UserMapper;
import com.knits.product.model.Team;
import com.knits.product.model.User;
import com.knits.product.repository.TeamRepository;
import com.knits.product.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for managing {@link com.knits.product.model.Team}.
 */
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class TeamService {

    private TeamRepository teamRepository;

    private UserRepository userRepository;
    private TeamMapper teamMapper;

    private UserMapper userMapper;

    /**
     * Save a team.
     *
     * @param teamDto the teamDto to save.
     * @return the persisted entity.
     */
    public TeamDto save(TeamDto teamDto) {
        log.debug("Request to save Team: {}", teamDto);
        Team team =teamMapper.toEntity(teamDto);

        List<User> usersAsEntities = new ArrayList<>();

        for (UserDto userDto : teamDto.getUsers()){
            User user = userMapper.toEntity(userDto);
            user.setTeam(team);
            usersAsEntities.add(user);
        }
        userRepository.saveAll(usersAsEntities);
        return teamMapper.toDto(teamRepository.save(team));
    }


    /**
     * Partially updates a team.
     *
     * @param teamDto the entity to update partially.
     * @return the persisted entity.
     */
    public TeamDto partialUpdate(TeamDto teamDto) {
        log.debug("Request to partially update Team: {}", teamDto);
        Team team =teamRepository.findById(teamDto.getId()).orElseThrow(()
                -> new UserException("Team#" + teamDto.getId() + " not found"));
        teamMapper.partialUpdate(team,teamDto);
        return teamMapper.toDto(teamRepository.save(team));

        /*
        List<UserDto> userDtos = new ArrayList<>();

        if (!teamDto.getUsers().isEmpty()){
            Set<Long> idSet = teamDto.getUsers().stream()
                    .map(UserDto::getId)
                    .collect(Collectors.toSet());
            List<User> usersAsEntities =userRepository.findAllById(idSet);
            usersAsEntities.forEach(user -> user.setTeam(team));
            userDtos =userMapper.toDtoList(userRepository.saveAll(usersAsEntities));
        }

        TeamDto updatedTeam =teamMapper.toDto(teamRepository.save(team));
        teamDto.setUsers(userDtos);
        */

    }

    /**
     * Overrides fields, including nulls.
     *
     * @param teamDto the entity to update.
     * @return the persisted entity.
     */
    public TeamDto update(TeamDto teamDto) {
        log.debug("Request to update Team: {}", teamDto);
        Team team =teamRepository.findById(teamDto.getId()).orElseThrow(()
                -> new UserException("Team#" + teamDto.getId() + " not found"));
        teamMapper.update(team,teamDto);
        return teamMapper.toDto(teamRepository.save(team));
    }

    public List<Team> findAll() {
//        List<Team> teams =teamRepository.findAll();
//        log.info ("Initialized ?, {}", JpaMapperUtils.isJpaListInitialized(teams.get(0).getUsers()));
//        return teamMapper.toDtoList(teams);
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
        return teamMapper.toDto(teamRepository.findById(id).orElseThrow(() ->
                new UserException("Team#" + id + " not found", ExceptionCodes.TEAM_NOT_FOUND)));
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
