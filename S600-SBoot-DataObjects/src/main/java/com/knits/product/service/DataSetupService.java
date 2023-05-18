package com.knits.product.service;

import com.knits.product.model.Group;
import com.knits.product.model.User;
import com.knits.product.repository.GroupRepository;
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
    private GroupRepository groupRepository;

    @Transactional
    public void setup (){

         // addUserToGroups(); //wrong, nothing will happen here...
        addGroupsToUsers(); //correct user_group table updated
    }

    private void addGroupsToUsers() {
        List<User> users =userRepository.findAll();
        List<Group> groups= groupRepository.findAll();

        int counter=0;

        for (User user : users){
            if (counter==groups.size()){
                counter=0;
            }

            List<Group> groupsToAddAsList=new ArrayList<>();
            groupsToAddAsList.add(groups.get(counter));
            user.setGroups(groupsToAddAsList);
            userRepository.save(user);
            counter++;
        }

    }


    //TODO (1) owner of relationship: this will not work
    private void addUserToGroups() {
        List<User> users =userRepository.findAll();
        List<Group> groups= groupRepository.findAll();
        int counter=0;

        for (Group group : groups){
            if (counter==users.size()){
                counter=0;
            }

            List<User> usersToAddAsList=new ArrayList<>();
            usersToAddAsList.add(users.get(counter));
            group.setUsers(usersToAddAsList);
            groupRepository.save(group);
            counter++;
        }
    }


}
