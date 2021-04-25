package com.allegro.task.gitapi.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;


@JsonNaming(SnakeCaseStrategy.class)
@NoArgsConstructor
public class GitApiRepoResponse {
    public String name;
    public int stargazersCount;

    public GitApiRepoResponse(String name, int stargazersCount) {
        this.name = name;
        this.stargazersCount = stargazersCount;
    }
}
