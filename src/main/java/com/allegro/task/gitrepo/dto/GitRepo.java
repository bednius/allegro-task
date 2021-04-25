package com.allegro.task.gitrepo.dto;

import com.allegro.task.gitapi.response.GitApiRepoResponse;

public class GitRepo {
    public final String name;
    public final int stargazersCount;

    public GitRepo(GitApiRepoResponse repo) {
        this.name = repo.name;
        this.stargazersCount = repo.stargazersCount;
    }
}
