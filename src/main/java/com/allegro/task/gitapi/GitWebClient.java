package com.allegro.task.gitapi;

import com.allegro.task.gitapi.dto.GitReposPage;
import com.allegro.task.gitapi.response.GitApiRepoResponse;
import com.allegro.task.gitrepo.dto.GitRepo;
import com.allegro.task.gitrepo.request.GitReposRequestParams;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class GitWebClient {

    private final WebClient webClient;

    @Autowired
    public GitWebClient(WebClient webClient) {
        this.webClient = webClient;
    }


    public Collection<GitRepo> getAllRepos(String username) {
        String url = String.format("/users/%s/repos?per_page=100", username);

        return Objects.requireNonNull(fetchPage(url)
                .expand(this::tryFetchNextPage)
                .flatMap(response -> response.bodyToMono(GitApiRepoResponse[].class))
                .collectList()
                .block())
                .stream()
                .flatMap(Arrays::stream)
                .map(GitRepo::new)
                .collect(Collectors.toUnmodifiableList());
    }


    public GitReposPage getReposPage(GitReposRequestParams params) {
        String url = String.format("/users/%s/repos?per_page=%s&page=%s", params.username, params.limit, params.page);

        ClientResponse clientResponse = fetchPage(url).block();
        GitApiRepoResponse[] repos = clientResponse
                .bodyToMono(GitApiRepoResponse[].class)
                .block(Duration.ofSeconds(5));
        return new GitReposPage(repos, getGitNextUrl(clientResponse));
    }


    private Mono<ClientResponse> fetchPage(String url) {
        log.info("Requesting git api with url=[{}]", url);

        return webClient.get()
                .uri(url)
                .exchange();
    }

    private Mono<ClientResponse> tryFetchNextPage(ClientResponse previousResponse) {
        String nextUrl = getGitNextUrl(previousResponse);

        if (!nextUrl.isEmpty()) {
            return fetchPage(nextUrl);
        }
        return Mono.empty();
    }


    private String getGitNextUrl(ClientResponse previousResponse) {
        Optional<String> nextLink = previousResponse.headers().asHttpHeaders().getValuesAsList("link")
                .stream()
                .filter(link -> link.contains("rel=\"next\""))
                .findFirst();

        return nextLink.map(s ->
                s.split(">")[0].substring(1))
                .orElse("");
    }
}
