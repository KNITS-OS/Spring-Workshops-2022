package com.knits.product.controller;

import com.knits.product.dto.GroupDto;
import com.knits.product.exception.UserException;
import com.knits.product.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@Slf4j
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable(value = "id") Long id) {
        log.debug("REST request to get Group: {}", id);
        GroupDto groupDto = groupService.findById(id);
        return ResponseEntity.ok(groupDto);
    }

    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllGroups() {
        log.debug("REST request to get all Groups");
        List<GroupDto> groupDtoList = groupService.findAll();
        return ResponseEntity.ok(groupDtoList);
    }

    @PostMapping
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto groupDto) {
        log.debug("REST request to create Group");
        if (groupDto == null) {
            throw new UserException("Group data is missing");
        }
        GroupDto createdGroupDto = groupService.save(groupDto);
        return ResponseEntity.ok(createdGroupDto);
    }

    @PutMapping
    public ResponseEntity<GroupDto> updateGroup(@RequestBody GroupDto groupDto) {
        log.debug("REST request to update Group");
        if (groupDto == null) {
            throw new UserException("Group data is missing");
        }
        GroupDto updatedGroupDto = groupService.update(groupDto);
        return ResponseEntity.ok(updatedGroupDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GroupDto> partialUpdateGroup(
            @PathVariable("id") Long id,
            @RequestBody GroupDto groupDto
    ) {
        log.debug("REST request to partially update Group");
        if (groupDto == null) {
            throw new UserException("Group data is missing");
        }
        groupDto.setId(id);
        GroupDto updatedGroupDto = groupService.partialUpdate(groupDto);
        return ResponseEntity.ok(updatedGroupDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("id") Long id) {
        log.debug("REST request to delete Group: {}", id);
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GroupDto>> searchGroups(Pageable pageable) {
        log.debug("REST request to search Groups");
        Page<GroupDto> groupDtoList = groupService.search(pageable);
        return ResponseEntity.ok(groupDtoList);
    }
}
