package com.allegro.task.gitrepo.request;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class GitReposRequestParams {
    public final String username;
    public final int page;
    public final int limit;

    public GitReposRequestParams(String username, int page, int limit) {
        this.username = username;
        this.page = page;
        this.limit = limit;
    }

    public GitReposRequestParams(String username) {
        this.username = username;
        this.page = 1;
        this.limit = 100;
    }
}
