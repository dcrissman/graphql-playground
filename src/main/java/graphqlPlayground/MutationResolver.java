package graphqlPlayground;

import java.util.List;
import java.util.UUID;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

public class MutationResolver implements GraphQLMutationResolver {

    private final List<Post> posts;
    private final List<Author> authors;

    public MutationResolver(List<Post> posts, List<Author> authors) {
        this.posts = posts;
        this.authors = authors;
    }

    public Post writePost(String title, String text, String category) {
        Post post = new Post(UUID.randomUUID().toString(), title, category, null);
        post.setText(text);
        posts.add(post);
        return post;
    }
}
