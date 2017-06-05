package com.sentilabs.okko.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentilabs.okko.common.FacebookAccount;
import com.sentilabs.okko.common.LinkedAccounts;
import com.sentilabs.okko.common.VKAccount;
import com.sentilabs.okko.dto.user.UserDTO;
import com.sentilabs.okko.entity.user.UserEntity;
import com.sentilabs.okko.fixtures.UserFixtures;
import com.sentilabs.okko.repository.UserRepository;
import com.sentilabs.okko.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@DataMongoTest
class UserControllerTest {

    private static ObjectMapper objectMapper = new ObjectMapper(){{
       configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }};

    @Autowired
    private MongoTemplate mongoTemplate;

    //@MockBean
    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection("users");
    }

    private UserDTO getUserDTOFromMVCResult(MvcResult mvcResult) throws Exception {
        String contentAsString = mvcResult.getResponse().getContentAsString();
        UserDTO userDTO = objectMapper.readValue(contentAsString, UserDTO.class);
        return userDTO;
    }

    private List<UserDTO> getMultipleUserDTOsFromMVCResult(MvcResult mvcResult) throws Exception {
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<UserDTO> userDTOs = Arrays.asList(objectMapper.readValue(contentAsString, UserDTO[].class));
        return userDTOs;
    }

    private ResultActions requestUserById(String userId) throws Exception {
        String url = ControllerConfiguration.Users.USERS_FULL_BASE_URL + "/{userId}";
        ResultActions resultActions = mockMvc
                .perform(
                        get(url, userId)
                )
                .andExpect(request().asyncStarted());;
        resultActions = mockMvc.perform(asyncDispatch(resultActions.andReturn()));
        return resultActions;
    }

    private ResultActions requestAllUsers() throws Exception {
        String url = ControllerConfiguration.Users.USERS_FULL_BASE_URL;
        ResultActions resultActions = mockMvc
                .perform(
                        get(url)
                )
                .andExpect(request().asyncStarted());
        resultActions = mockMvc.perform(asyncDispatch(resultActions.andReturn()));
        return resultActions;
    }

    private ResultActions requestUserByEmail(String email) throws Exception {
        String url = ControllerConfiguration.Users.USERS_FULL_BASE_URL
                + "?" + ControllerConfiguration.Users.SEARCH_BY_EMAIL_REQ_PARAM + "={email}";
        ResultActions resultActions = mockMvc
                .perform(
                        get(url, email)
                )
                .andExpect(request().asyncStarted());
        resultActions = mockMvc.perform(asyncDispatch(resultActions.andReturn()));
        return resultActions;
    }

    private ResultActions createUsers(List<UserDTO> userDTOs) throws Exception {
        String url = ControllerConfiguration.Users.USERS_FULL_BASE_URL;
        ResultActions resultActions = mockMvc
                .perform(
                        post(url)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(objectMapper.writeValueAsBytes(userDTOs))
                )
                .andExpect(request().asyncStarted());
        resultActions = mockMvc.perform(asyncDispatch(resultActions.andReturn()));
        return resultActions;
    }

    private ResultActions deleteUserById(String userId) throws Exception {
        String url = ControllerConfiguration.Users.USERS_FULL_BASE_URL + "/{userId}";
        ResultActions resultActions = mockMvc
                .perform(
                        delete(url, userId)
                )
                .andExpect(request().asyncStarted());
        resultActions = mockMvc.perform(asyncDispatch(resultActions.andReturn()));
        return resultActions;
    }

    @Test
    void getUserById() throws Exception {
        //given(userRepository.findOne(Example.of(new UserEntity()))).willReturn(new UserEntity());
        List<FacebookAccount> facebookAccounts = new ArrayList<>();
        List<VKAccount> vkAccounts = new ArrayList<>();

        FacebookAccount facebookAccount = new FacebookAccount();
        facebookAccount.setId("id1");
        facebookAccount.setEmail("a@b.cc");
        facebookAccounts.add(facebookAccount);

        VKAccount vkAccount = new VKAccount();
        vkAccount.setId(123);
        vkAccount.setEmail("d@f.ee");
        vkAccounts.add(vkAccount);

        LinkedAccounts linkedAccounts = new LinkedAccounts();
        linkedAccounts.setFacebookAccounts(facebookAccounts);
        linkedAccounts.setVkAccounts(vkAccounts);

        UserEntity userEntity = new UserEntity();
        userEntity.setName("user1");
        userEntity.setLinkedAccounts(linkedAccounts);
        mongoTemplate.insert(userEntity);

        ResultActions resultActions = requestUserById("user1");
        MvcResult mvcResult = resultActions.andReturn();
        UserDTO userDTO = getUserDTOFromMVCResult(mvcResult);

        assertEquals(userDTO.getName(), userEntity.getName());
        assertNull(userDTO.getEmail());
        assertNull(userDTO.getPhone());
        assertNotNull(userDTO.getLinkedAccounts());
        assertNotNull(userDTO.getLinkedAccounts().getFacebookAccounts());
        assertNotNull(userDTO.getLinkedAccounts().getVkAccounts());

        assertEquals(1, userDTO.getLinkedAccounts().getFacebookAccounts().size());
        FacebookAccount facebookAccountResponse = userDTO.getLinkedAccounts().getFacebookAccounts().get(0);
        assertEquals(facebookAccount.getId(), facebookAccountResponse.getId());
        assertEquals(facebookAccount.getEmail(), facebookAccountResponse.getEmail());

        assertEquals(1, userDTO.getLinkedAccounts().getVkAccounts().size());
        VKAccount vkAccountResponse = userDTO.getLinkedAccounts().getVkAccounts().get(0);
        assertEquals(vkAccount.getId(), vkAccountResponse.getId());
        assertEquals(vkAccount.getEmail(), vkAccountResponse.getEmail());
    }

    @Test
    void testUserCreation() throws Exception {
        //given(userRepository.findOne(Example.of(new UserEntity()))).willReturn(new UserEntity());
        UserDTO userDTO = UserFixtures.createUserDTO(1);
        ResultActions resultActions = createUsers(Collections.singletonList(userDTO));
        resultActions.andExpect(status().isOk());

        resultActions = requestUserByEmail(userDTO.getEmail());
        resultActions.andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();

        UserDTO userDTOResponse = getUserDTOFromMVCResult(mvcResult);
        assertEquals(userDTO.getName(), userDTOResponse.getName());
        assertEquals(userDTO.getEmail(), userDTOResponse.getEmail());
        assertEquals(userDTO.getPhone(), userDTOResponse.getPhone());
    }

    @Test
    void testMultipleUserCreation() throws Exception {
        //given(userRepository.findOne(Example.of(new UserEntity()))).willReturn(new UserEntity());
        UserDTO userDTO1 = UserFixtures.createUserDTO(1);
        UserDTO userDTO2 = UserFixtures.createUserDTO(2);
        ResultActions resultActions = createUsers(Arrays.asList(userDTO1, userDTO2));
        resultActions.andExpect(status().isOk());

        resultActions = requestAllUsers();
        resultActions.andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();

        List<UserDTO> userDTOs = getMultipleUserDTOsFromMVCResult(mvcResult);
        assertEquals(2, userDTOs.size(), "Expected to have 2 users in response");
        int count = 0;
        for (UserDTO userDTO : userDTOs) {
            if (userDTO.getName().equals(userDTO1.getName())) {
                assertEquals(userDTO.getName(), userDTO1.getName());
                assertEquals(userDTO.getEmail(), userDTO1.getEmail());
                assertEquals(userDTO.getPhone(), userDTO1.getPhone());
                ++count;
            }
            else if (userDTO.getName().equals(userDTO2.getName())) {
                assertEquals(userDTO.getName(), userDTO2.getName());
                assertEquals(userDTO.getEmail(), userDTO2.getEmail());
                assertEquals(userDTO.getPhone(), userDTO2.getPhone());
                ++count;
            }
        }
        assertEquals(2, count, "Got 2 users from response as expected but not expected users");
    }

    @Test
    void testDeleteUserById() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("user1");
        mongoTemplate.insert(userEntity);

        ResultActions resultActions = requestUserById(userEntity.getName());
        UserDTO userDTO = getUserDTOFromMVCResult(resultActions.andReturn());
        assertEquals(userEntity.getName(), userDTO.getName());

        deleteUserById(userEntity.getName());

        List<UserDTO> userDTOs = getMultipleUserDTOsFromMVCResult(requestAllUsers().andReturn());
        assertEquals(0, userDTOs.size());
    }

}