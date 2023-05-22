package com.knits.product.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.knits.product.dto.TeamDto;
import com.knits.product.dto.views.Views;
import com.knits.product.exception.UserException;
import com.knits.product.model.Team;
import com.knits.product.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@Slf4j
@JsonView(Views.Public.class)
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable(value = "id") Long id) {
        log.debug("REST request to get Team: {}", id);
        TeamDto teamDto = teamService.findById(id);
        return ResponseEntity.ok(teamDto);
    }

    @GetMapping
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        log.debug("REST request to get all Teams");
        List<TeamDto> teamDtoList = teamService.findAll();
        return ResponseEntity.ok(teamDtoList);
    }

    @GetMapping("/entities")
    public ResponseEntity<List<Team>> getAllTeamsAsEntities() {
        log.debug("REST request to get all Teams");
        List<Team> teamDtoList = teamService.findAllAsEntities();
        return ResponseEntity.ok(teamDtoList);
    }

    @PostMapping
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto teamDto) {
        log.debug("REST request to create Team");
        if (teamDto == null) {
            throw new UserException("Team data is missing");
        }
        TeamDto createdTeamDto = teamService.save(teamDto);
        return ResponseEntity.ok(createdTeamDto);
    }

    @PutMapping
    public ResponseEntity<TeamDto> updateTeam(@RequestBody TeamDto teamDto) {
        log.debug("REST request to update Team");
        if (teamDto == null) {
            throw new UserException("Team data is missing");
        }
        TeamDto updatedTeamDto = teamService.update(teamDto);
        return ResponseEntity.ok(updatedTeamDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TeamDto> partialUpdateTeam(
            @PathVariable("id") Long id,
            @RequestBody TeamDto teamDto
    ) {
        log.debug("REST request to partially update Team");
        if (teamDto == null) {
            throw new UserException("Team data is missing");
        }
        teamDto.setId(id);
        TeamDto updatedTeamDto = teamService.partialUpdate(teamDto);
        return ResponseEntity.ok(updatedTeamDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable("id") Long id) {
        log.debug("REST request to delete Team: {}", id);
        teamService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TeamDto>> searchTeams(Pageable pageable) {
        log.debug("REST request to search Teams");
        Page<TeamDto> teamDtoList = teamService.search(pageable);
        return ResponseEntity.ok(teamDtoList);
    }
}
