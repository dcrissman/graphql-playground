package graphql;

import java.util.List;
import java.util.stream.Collectors;

import com.coxautodev.graphql.tools.GraphQLResolver;

public class AuthorResolver implements GraphQLResolver<Author> {
    private final List<Post> posts;

    public AuthorResolver(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts(Author author) {
        return posts.stream()
                .filter(post -> post.getAuthorId().equals(author.getId()))
                .collect(Collectors.toList());
    }
}
