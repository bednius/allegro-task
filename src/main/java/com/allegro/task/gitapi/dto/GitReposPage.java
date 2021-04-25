package com.allegro.task.gitapi.dto;

import com.allegro.task.gitapi.response.GitApiRepoResponse;
import com.allegro.task.gitrepo.dto.GitRepo;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class GitReposPage {
    public final Collection<GitRepo> repos;
    public final String nextGitUrl;

    public GitReposPage(GitApiRepoResponse[] repos, String nextGitUrl) {
        this.repos = Arrays.stream(repos)
                .map(GitRepo::new)
                .collect(Collectors.toUnmodifiableList());
        this.nextGitUrl = nextGitUrl;
    }

    public boolean hasNext() {
        return !nextGitUrl.isEmpty();
    }
}
