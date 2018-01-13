package graphqlPlayground;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

public class QueryResolver implements GraphQLQueryResolver {

    private final List<Post> posts;
    private final List<Author> authors;

    public QueryResolver(List<Post> posts, List<Author> authors) {
        this.posts = posts;
        this.authors = authors;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<Post> getRecentPosts(int count, int offset) {
        return posts.subList(offset, offset + count);
    }

    public List<Author> getAuthors() {
        return authors;
    }
}
