package graphql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
        return generateRequest(query, null);
    }

    private String generateRequest(String query, Map<String, String> variables) throws Exception {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("query", query);

        if (variables != null) {
            JSONObject vars = new JSONObject();
            for (Entry<String, String> entry : variables.entrySet()) {
                vars.put(entry.getKey(), entry.getValue());
            }

            jsonObject.put("variables", vars);
        }

        return jsonObject.toString();
    }

    @Test
    public void testAllPosts() throws Exception {
        String query = "{Post {title}}";

        ResponseEntity<Post[]> postsResponse = template.postForEntity(
                base.toString(),
                generateRequest(query),
                Post[].class);

        assertNotNull(postsResponse.getBody());
        assertEquals(1, postsResponse.getBody().length);
    }

    @Test
    public void test() throws Exception {
        String query = "{recentPosts {title}}";
        Map<String, String> variables = new HashMap<>();
        variables.put("count", "1");
        variables.put("offset", "1");


        ResponseEntity<Post[]> postsResponse = template.postForEntity(
                base.toString(),
                generateRequest(query, variables),
                Post[].class);

        assertNotNull(postsResponse.getBody());
        assertEquals(1, postsResponse.getBody().length);
    }

}
