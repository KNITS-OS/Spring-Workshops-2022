package com.knits.product.service;

import com.knits.product.mocks.dto.UserDtoMocks;
import com.knits.product.mocks.model.UserMocks;
import com.knits.product.model.User;
import com.knits.product.repository.UserRepository;
import com.knits.product.dto.UserDto;
import com.knits.product.mapper.UserMapper;
import com.knits.product.service.mapper.UserMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Spy
    private final UserMapper userMapper = new UserMapperImpl();

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    @DisplayName("Save User Success")
    void saveSuccess() {

        //1) create some mock data (make them reusable in ohter tests)
        UserDto toSaveDto =UserDtoMocks.shallowUserDto(null);

        //2) prepare mocks for everything they should return
        when(userRepository.save(Mockito.any(User.class))) //any object of type User will match here
                .thenAnswer(i -> i.getArguments()[0]); //echo 1st parameter received

        //3) class under test
        UserDto savedDto= userService.save(toSaveDto);

        //4) use captor in spy/mocks for everything they get as input
        verify(userRepository).save(userCaptor.capture());
        User toSaveEntity = userCaptor.getValue();

        //5) check if all dependencies were called (eventually with check on input data)
        verify(userMapper, times(1)).toEntity(toSaveDto);
        verify(userRepository, times(1)).save(toSaveEntity);
        verify(userMapper, times(1)).toDto(toSaveEntity);

        //6) assertions actual vs expected
        assertThat(toSaveDto).isEqualTo(savedDto); //not usually a good practice, but equals has override using all fields
    }

    @Test
    @DisplayName("Save User duplicated username")
    void saveShouldFailOnDuplicatedUsername() {
        //this need implementation to be changed first...
    }

    @Test
    @DisplayName("partial Update success")
    void partialUpdate (){

        Long entityIdToUpdate = 1L;
        String updateOnUserFirstName = "updatedFirstName";

        User foundEntity= UserMocks.shallowUser(entityIdToUpdate);
        UserDto toUpdateDto =userMapper.toDto(foundEntity); //this is recorded therefore time expected is 2
        toUpdateDto.setFirstName(updateOnUserFirstName);


        when(userRepository.findById(entityIdToUpdate)).thenReturn(Optional.of(foundEntity));

        UserDto updatedDto =userService.partialUpdate(toUpdateDto);

        verify(userRepository).save(userCaptor.capture());
        User toUpdateEntity = userCaptor.getValue();

        verify(userMapper, times(1)).partialUpdate(foundEntity,toUpdateDto);
        verify(userRepository, times(1)).save(foundEntity);
        verify(userMapper, times(2)).toDto(foundEntity);

        assertThat(toUpdateDto).isEqualTo(updatedDto);

    }


    @Test
    @DisplayName("delete success")
    void deleteSuccess (){
        Long entityIdToDelete = 1L;
        userService.delete(entityIdToDelete);
    }

    @Test
    @DisplayName("findAll success")
    void findAllSuccess (){
        int expectedSize=10;
        List<User> resultSet =UserMocks.shallowListOfUsers(expectedSize);
        when(userRepository.findAll()).thenReturn(resultSet);

        List<UserDto> resultsetDto = userService.findAll();
        verify(userMapper, times(expectedSize)).toDto(any(User.class));
        assertThat(resultsetDto.size()).isEqualTo(expectedSize);
    }



}
