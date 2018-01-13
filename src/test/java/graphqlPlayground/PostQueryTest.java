package graphqlPlayground;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
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
 * @author dcrissman
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostQueryTest {

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
    @Ignore
    public void testAllPosts() throws Exception {
        String query = "{posts {title}}";

        ResponseEntity<ExecutionResultImpl> postsResponse = template.postForEntity(
                base.toString(),
                generateRequest(query),
                ExecutionResultImpl.class);

        assertNotNull(postsResponse.getBody());
        assertNotNull(postsResponse.getBody().getData());
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

}
