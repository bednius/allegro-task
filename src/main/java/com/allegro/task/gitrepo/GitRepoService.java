package com.allegro.task.gitrepo;

import com.allegro.task.gitapi.GitWebClient;
import com.allegro.task.gitapi.dto.GitReposPage;
import com.allegro.task.gitrepo.dto.GitRepo;
import com.allegro.task.gitrepo.request.GitReposRequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.util.Collection;

@Service
public class GitRepoService {
    private final GitWebClient gitClient;

    private final ServletContext servletContext;

    @Autowired
    public GitRepoService(GitWebClient gitClient, ServletContext servletContext) {
        this.gitClient = gitClient;
        this.servletContext = servletContext;
    }

    public GitReposPage getReposPage(GitReposRequestParams params) {
        return gitClient.getReposPage(params);
    }

    public long getStargazersSum(String username) {
        Collection<GitRepo> repos = gitClient.getAllRepos(username);
        return repos.stream()
                .mapToLong(repo -> repo.stargazersCount)
                .sum();
    }
}
