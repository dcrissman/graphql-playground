package graphqlPlayground;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @see https://spring.io/guides/gs/spring-boot/
 * @see https://github.com/merapar/graphql-spring-boot-starter/blob/master/graphql-core/src/test/java/com/merapar/graphql/controller/GraphQlControllerTest.java
 * @see http://graphql-java.readthedocs.io/en/stable/schema.html
 * @author dcrissman
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QueryResolverTest {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void before() throws Exception {
        base = new URL("http://localhost:" + port + "/graphql");
    }

    private String generateRequest(String query) throws Exception {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("query", query);

        return jsonObject.toString();
    }

    @Test
    public void testAllPosts() throws Exception {
        String query = "{posts {category}}";

        ResponseEntity<ExecutionResultImpl> postsResponse = template.postForEntity(
                base.toString(),
                generateRequest(query),
                ExecutionResultImpl.class);

        //{posts=[{category=cat1}, {category=cat1}, {category=cat1}, {category=cat2}, {category=cat2}, {category=cat2}]}
        assertNotNull(postsResponse.getBody());
        assertNotNull(postsResponse.getBody().getData());
        Map<String, List<Map<String, String>>> data = (Map<String, List<Map<String, String>>>) postsResponse.getBody().getData();
        assertTrue(data.containsKey("posts"));
        assertEquals(6, data.get("posts").size());
        assertEquals("cat1", data.get("posts").get(0).get("category"));
        assertEquals("cat1", data.get("posts").get(1).get("category"));
        assertEquals("cat1", data.get("posts").get(2).get("category"));
        assertEquals("cat2", data.get("posts").get(3).get("category"));
        assertEquals("cat2", data.get("posts").get(4).get("category"));
        assertEquals("cat2", data.get("posts").get(5).get("category"));
    }

    @Test
    public void testRecentPosts() throws Exception {
        String query = "{recentPosts(count: 2, offset: 1) {title}}";

        ResponseEntity<ExecutionResultImpl> postsResponse = template.postForEntity(
                base.toString(),
                generateRequest(query),
                ExecutionResultImpl.class);

        //{recentPosts=[{title=fake2}, {title=fake3}]}
        assertNotNull(postsResponse.getBody());
        assertNotNull(postsResponse.getBody().getData());
        Map<String, List<Map<String, String>>> data = (Map<String, List<Map<String, String>>>) postsResponse.getBody().getData();
        assertTrue(data.containsKey("recentPosts"));
        assertEquals(2, data.get("recentPosts").size());
        assertEquals("fake2", data.get("recentPosts").get(0).get("title"));
        assertEquals("fake3", data.get("recentPosts").get(1).get("title"));
    }

    @Test
    public void testAuthors() throws Exception {
        String query = "{authors {name}}";

        ResponseEntity<ExecutionResultImpl> postsResponse = template.postForEntity(
                base.toString(),
                generateRequest(query),
                ExecutionResultImpl.class);

        //{recentPosts=[{title=fake2}, {title=fake3}]}
        assertNotNull(postsResponse.getBody());
        assertNotNull(postsResponse.getBody().getData());
        Map<String, List<Map<String, String>>> data = (Map<String, List<Map<String, String>>>) postsResponse.getBody().getData();
        assertTrue(data.containsKey("authors"));
        assertEquals(1, data.get("authors").size());
        assertEquals("fake author", data.get("authors").get(0).get("name"));
    }

    @Test
    public void testWritePost() throws Exception {
        String mutation = "mutation {writePost(title: \"anewbook\", text: \"blah\", category: \"newcat\") {title}}";

        ResponseEntity<ExecutionResultImpl> postsResponse = template.postForEntity(
                base.toString(),
                generateRequest(mutation),
                ExecutionResultImpl.class);

        //{writePost={title=anewbook}}
        assertNotNull(postsResponse.getBody());
        assertNotNull(postsResponse.getBody().getData());
    }

}
