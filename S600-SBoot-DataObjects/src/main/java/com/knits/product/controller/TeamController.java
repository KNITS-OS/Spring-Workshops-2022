package com.knits.product.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.knits.product.exception.UserException;
import com.knits.product.model.JsonViews;
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
    private TeamService teamService;

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable(value = "id") Long id) {
        log.debug("REST request to get Team: {}", id);
        Team team = teamService.findById(id);
        return ResponseEntity.ok(team);
    }

    @GetMapping
    @JsonView(JsonViews.TeamDetails.class)
    public ResponseEntity<List<Team>> getAllTeams() {
        log.debug("REST request to get all Teams");
        List<Team> teamDtoList = teamService.findAll();
        return ResponseEntity.ok(teamDtoList);
    }

    @GetMapping("/entities")
    public ResponseEntity<List<Team>> getAllTeamsAsEntities() {
        log.debug("REST request to get all Teams");
        List<Team> teamDtoList = teamService.findAllAsEntities();
        return ResponseEntity.ok(teamDtoList);
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        log.debug("REST request to create Team");
        if (team == null) {
            throw new UserException("Team data is missing");
        }
        Team createdTeamDto = teamService.save(team);
        return ResponseEntity.ok(createdTeamDto);
    }

    @PutMapping
    public ResponseEntity<Team> updateTeam(@RequestBody Team team) {
        log.debug("REST request to update Team");
        Team updatedTeam = teamService.update(team);
        return ResponseEntity.ok(updatedTeam);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Team> partialUpdateTeam(
            @PathVariable("id") Long id,
            @RequestBody Team team
    ) {
        log.debug("REST request to partially update Team");
        team.setId(id);
        Team updatedTeamDto = teamService.partialUpdate(team);
        return ResponseEntity.ok(updatedTeamDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable("id") Long id) {
        log.debug("REST request to delete Team: {}", id);
        teamService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
