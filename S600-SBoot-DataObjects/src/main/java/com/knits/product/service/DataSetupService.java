package com.knits.product.service;


import com.knits.product.model.Team;
import com.knits.product.model.User;
import com.knits.product.repository.TeamRepository;
import com.knits.product.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class DataSetupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Transactional
    public void setup (){
        //addUserToTeams(); // Doesnt work Team doenst own relationship
        addTeamToUsers();
    }

    private void addTeamToUsers() {
        List<User> users = userRepository.findAll();
        List<Team> teams = teamRepository.findAll();

        int counter = 0;

        for (User user : users) {
            if (counter == teams.size()) {
                counter = 0;
            }

            user.setTeam(teams.get(counter));
            userRepository.save(user);
            counter++;
        }
    }

    private void addUserToTeams() {
        List<User> users = userRepository.findAll();
        List<Team> teams = teamRepository.findAll();
        int counter = 0;

        for (Team team : teams) {
            if (counter == users.size()) {
                counter = 0;
            }

            List<User> usersToAddAsList = new ArrayList<>();
            usersToAddAsList.add(users.get(counter));
            team.setUsers(usersToAddAsList);
            teamRepository.save(team);
            counter++;
        }
    }


}
