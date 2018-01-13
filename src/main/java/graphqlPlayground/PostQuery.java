package graphqlPlayground;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

public class PostQuery implements GraphQLQueryResolver {

    private final List<Post> posts;

    public PostQuery(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<Post> getRecentPosts(int count, int offset) {
        return posts.subList(offset, offset + count);
    }
}
