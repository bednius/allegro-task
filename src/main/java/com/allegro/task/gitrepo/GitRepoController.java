package com.allegro.task.gitrepo;

import com.allegro.task.gitrepo.request.GitReposRequestParams;
import com.allegro.task.gitrepo.response.GitReposResponsePage;
import com.allegro.task.gitrepo.response.GitStargazersSumResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Controller
@Validated
@Log4j2
public class GitRepoController {

    private final GitRepoService service;

    @Autowired
    public GitRepoController(GitRepoService service) {
        this.service = service;
    }

    @GetMapping("/{username}/repos")
    public ResponseEntity<GitReposResponsePage> getRepos(
            @PathVariable String username,
            @RequestParam(defaultValue = "1") @Min(1) @Max(100) int page,
            @RequestParam(defaultValue = "100") @Min(1) @Max(100) int limit) {

        GitReposRequestParams params = new GitReposRequestParams(username, page, limit);

        log.info("Received getRepos request with {}", params.toString());

        GitReposResponsePage response = new GitReposResponsePage(
                service.getReposPage(params), params);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}/stargazers/sum")
    ResponseEntity<GitStargazersSumResponse> getStargazersSum(
            @PathVariable String username) {

        log.info("Received getRepos request for username={}", username);

        GitStargazersSumResponse response = new GitStargazersSumResponse(
                service.getStargazersSum(username));

        return ResponseEntity.ok(response);
    }
}
