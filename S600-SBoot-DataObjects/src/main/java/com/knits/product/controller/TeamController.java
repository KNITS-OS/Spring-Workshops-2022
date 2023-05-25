package com.knits.product.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.knits.product.dto.TeamDto;
import com.knits.product.exception.UserException;
import com.knits.product.dto.Views;
import com.knits.product.mapper.TeamMapper;
import com.knits.product.model.Team;
import com.knits.product.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@Slf4j
public class TeamController {

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private TeamService teamService;

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable(value = "id") Long id) {
        log.debug("REST request to get Team: {}", id);
        TeamDto team = teamService.findById(id);
        return ResponseEntity.ok(team);
    }

    @GetMapping
    @JsonView(Views.TeamDetails.class)
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        log.debug("REST request to get all Teams");
        return ResponseEntity.ok(teamService.findAll());
    }

    @PostMapping
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto team) {
        log.debug("REST request to create Team");
        TeamDto createdTeamDto = teamService.save(team);
        return ResponseEntity.ok(createdTeamDto);
    }

    @PutMapping
    public ResponseEntity<TeamDto> updateTeam(@RequestBody TeamDto team) {
        log.debug("REST request to update Team");
        TeamDto updatedTeam = teamService.update(team);
        return ResponseEntity.ok(updatedTeam);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TeamDto> partialUpdateTeam(
            @PathVariable("id") Long id,
            @RequestBody TeamDto team
    ) {
        log.debug("REST request to partially update Team");
        team.setId(id);
        TeamDto updatedTeamDto = teamService.partialUpdate(team);
        return ResponseEntity.ok(updatedTeamDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable("id") Long id) {
        log.debug("REST request to delete Team: {}", id);
        teamService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
