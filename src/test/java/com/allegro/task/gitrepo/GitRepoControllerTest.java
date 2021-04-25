package com.allegro.task.gitrepo;


import com.allegro.task.gitapi.dto.GitReposPage;
import com.allegro.task.gitapi.response.GitApiRepoResponse;
import com.allegro.task.gitrepo.request.GitReposRequestParams;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GitRepoController.class)
public class GitRepoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GitRepoService gitRepoService;

    @Test
    public void shouldReturnListOfGitRepos() throws Exception {
        String username = "allegroTestUser";

        Mockito.when(gitRepoService.getReposPage(new GitReposRequestParams(username)))
                .thenReturn(new GitReposPage(
                        new GitApiRepoResponse[]{
                                new GitApiRepoResponse("repo1", 1),
                                new GitApiRepoResponse("repo2", 2),
                                new GitApiRepoResponse("repo3", 3)},
                        ""));

        mockMvc.perform(
                get("/{username}/repos", username))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"repos\": [" +
                        "{\"name\": \"repo1\", \"stargazersCount\": 1}," +
                        "{\"name\": \"repo2\", \"stargazersCount\": 2}," +
                        "{\"name\": \"repo3\", \"stargazersCount\": 3}" +
                        "]}"));

    }
}
