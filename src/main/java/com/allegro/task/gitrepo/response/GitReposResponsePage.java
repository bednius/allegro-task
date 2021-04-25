package com.allegro.task.gitrepo.response;

import com.allegro.task.gitapi.dto.GitReposPage;
import com.allegro.task.gitrepo.dto.GitRepo;
import com.allegro.task.gitrepo.request.GitReposRequestParams;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.stream.Collectors;

public class GitReposResponsePage {
    public final Collection<GitRepoResponse> repos;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public final String nextUrl;

    public GitReposResponsePage(GitReposPage reposPage, GitReposRequestParams params) {
        this.repos = reposPage.repos.stream()
                .map(GitRepoResponse::new)
                .collect(Collectors.toList());
        this.nextUrl = buildNextUrl(reposPage.hasNext(), params);
    }

    private String buildNextUrl(boolean hasNext, GitReposRequestParams params) {
        if (hasNext) {
            String url = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
            return url + String.format("?page=%s&limit=%s", params.page + 1, params.limit);
        }
        return "";
    }

    static class GitRepoResponse {
        public final String name;
        public final int stargazersCount;

        public GitRepoResponse(GitRepo repo) {
            this.name = repo.name;
            this.stargazersCount = repo.stargazersCount;
        }
    }
}
