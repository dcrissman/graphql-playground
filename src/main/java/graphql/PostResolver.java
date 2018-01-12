package graphql;

import java.util.List;
import java.util.Optional;

import com.coxautodev.graphql.tools.GraphQLResolver;

public class PostResolver implements GraphQLResolver<Post> {

    private final List<Author> authors;

    public PostResolver(List<Author> authors) {
        this.authors = authors;
    }

    public Optional<Author> getAuthor(Post post) {
        return authors.stream()
                .filter(author -> author.getId().equals(post.getAuthorId()))
                .findFirst();
    }

}
