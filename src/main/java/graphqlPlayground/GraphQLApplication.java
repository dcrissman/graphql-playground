package graphqlPlayground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @see https://spring.io/guides/gs/spring-boot/
 * @see http://www.baeldung.com/spring-graphql
 * @see https://github.com/eugenp/tutorials/tree/master/spring-boot/src/main/java/com/baeldung/graphql
 * @author dcrissman
 */
@SpringBootApplication
public class GraphQLApplication {

    private final List<Post> posts = new ArrayList<>(Arrays.asList(
            new Post("1", "fake", "cat1", "a1"),
            new Post("2", "fake2", "cat1", "a1"),
            new Post("3", "fake3", "cat1", "a1"),
            new Post("4", "fake4", "cat2", "a1"),
            new Post("5", "fake5", "cat2", "a1"),
            new Post("6", "fake6", "cat2", "a1")));

    private final List<Author> authors = new ArrayList<>(Arrays.asList(
            new Author("a1", "fake author", "path/to/image")));

    public static void main(String[] args) {
        SpringApplication.run(GraphQLApplication.class, args);
    }

    @Bean
    public QueryResolver queryResolver() {
        return new QueryResolver(posts, authors);
    }

    @Bean
    public PostResolver postResolver() {
        return new PostResolver(authors);
    }

    @Bean
    public AuthorResolver authorResolver() {
        return new AuthorResolver(posts);
    }

    @Bean
    public MutationResolver mutationResolver() {
        return new MutationResolver(posts, authors);
    }

}
