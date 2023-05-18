package com.knits.product.service;

import com.knits.product.dto.GroupDto;
import com.knits.product.exception.ExceptionCodes;
import com.knits.product.exception.UserException;
import com.knits.product.mapper.GroupMapper;
import com.knits.product.model.Group;
import com.knits.product.repository.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing {@link com.knits.product.model.Group}.
 */
@Service
@Transactional
@Slf4j
public class GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private GroupRepository groupRepository;

    /**
     * Save a group.
     *
     * @param groupDto the entity to save.
     * @return the persisted entity.
     */
    public GroupDto save(GroupDto groupDto) {
        log.debug("Request to save Group: {}", groupDto);
        Group group = groupMapper.toEntity(groupDto);
        group = groupRepository.save(group);
        return groupMapper.toDto(group);
    }

    /**
     * Partially updates a group.
     *
     * @param groupDto the entity to update partially.
     * @return the persisted entity.
     */
    public GroupDto partialUpdate(GroupDto groupDto) {
        log.debug("Request to partially update Group: {}", groupDto);
        Group group = groupRepository.findById(groupDto.getId()).orElseThrow(() ->
                new UserException("Group#" + groupDto.getId() + " not found"));
        groupMapper.partialUpdate(group, groupDto);

        // TODO: Manage Group relationships to check updates
        groupRepository.save(group);
        return groupMapper.toDto(group);
    }

    /**
     * Overrides fields, including nulls.
     *
     * @param groupDto the entity to update.
     * @return the persisted entity.
     */
    public GroupDto update(GroupDto groupDto) {
        log.debug("Request to update Group: {}", groupDto);
        Group group = groupRepository.findById(groupDto.getId()).orElseThrow(() ->
                new UserException("Group#" + groupDto.getId() + " not found"));
        groupMapper.update(group, groupDto);

        // TODO: Manage Group relationships to check updates
        groupRepository.save(group);
        return groupMapper.toDto(group);
    }

    public List<GroupDto> findAll() {
        return groupRepository.findAll().stream()
                .map(groupMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Get a group by the "id".
     *
     * @param id the id of the group.
     * @return the group.
     */
    public GroupDto findById(Long id) {
        log.debug("Request Group by id: {}", id);
        Group group = groupRepository.findById(id).orElseThrow(() ->
                new UserException("Group#" + id + " not found", ExceptionCodes.GROUP_NOT_FOUND));
        return groupMapper.toDto(group);
    }

    /**
     * Delete a group by the "id".
     *
     * @param id the id of the group.
     */
    public void delete(Long id) {
        log.debug("Delete Group by id: {}", id);
        groupRepository.deleteById(id);
    }

    /**
     * Get all the groups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<GroupDto> search(Pageable pageable) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
