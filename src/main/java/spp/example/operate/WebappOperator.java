package spp.example.operate;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import spp.example.webapp.Booter;
import spp.example.webapp.model.User;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class WebappOperator {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final Faker faker = Faker.instance();

    private static List<Pair<String, String>> genNames = new CopyOnWriteArrayList<>();
    private static int minUserId = Integer.MAX_VALUE;
    private static int maxUserId = Integer.MIN_VALUE;

    public static void main(String[] args) {
        //start webapp
        Booter.main(args);

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            genNames.add(new ImmutablePair(firstName, lastName));
            try {
                URI uri = UriComponentsBuilder
                        .fromHttpUrl("http://localhost:9999/users")
                        .queryParam("firstName", firstName)
                        .queryParam("lastName", lastName)
                        .build()
                        .toUri();
                restTemplate.exchange(uri, HttpMethod.POST, null, User.class);
            } catch (Exception ignore) {
            }
        }, 0, ThreadLocalRandom.current().nextInt(2500 * 5), TimeUnit.MILLISECONDS);

        genNames.add(new ImmutablePair(faker.name().firstName(), faker.name().lastName()));
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                Pair<String, String> genName;
                if (ThreadLocalRandom.current().nextInt(10) > 8) {
                    genName = genNames.remove(ThreadLocalRandom.current().nextInt(genNames.size()));
                } else {
                    genName = new ImmutablePair(faker.name().firstName(), faker.name().lastName());
                }
                URI uri = UriComponentsBuilder
                        .fromHttpUrl("http://localhost:9999/users")
                        .queryParam("firstName", genName.getLeft())
                        .queryParam("lastName", genName.getRight())
                        .build()
                        .toUri();
                restTemplate.exchange(uri, HttpMethod.DELETE, null, User.class);
            } catch (Exception ignore) {
            }
        }, 0, ThreadLocalRandom.current().nextInt(1500 * 5), TimeUnit.MILLISECONDS);

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            ResponseEntity<User[]> userEntities = restTemplate.getForEntity("http://localhost:9999/users", User[].class);
            Arrays.stream(userEntities.getBody()).forEach(user -> {
                if (user.getId() < minUserId) {
                    minUserId = (int) user.getId();
                }
                if (user.getId() > maxUserId) {
                    maxUserId = (int) user.getId();
                }
            });
        }, 0, ThreadLocalRandom.current().nextInt(3500 * 5), TimeUnit.MILLISECONDS);

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            if (minUserId < maxUserId && maxUserId > 0) {
                int userId = ThreadLocalRandom.current().nextInt(minUserId, maxUserId);
                if (ThreadLocalRandom.current().nextBoolean()) {
                    userId *= 2;
                }
                try {
                    restTemplate.getForEntity("http://localhost:9999/users/" + userId, User.class);
                } catch (RestClientException ignore) {
                }
            }
        }, 0, ThreadLocalRandom.current().nextInt(3500 * 5), TimeUnit.MILLISECONDS);

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                restTemplate.getForEntity("http://localhost:9999/throws-exception", User.class);
            } catch (RestClientException ignore) {
            }
        }, 0, ThreadLocalRandom.current().nextInt(6500 * 5), TimeUnit.MILLISECONDS);

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                restTemplate.getForEntity("http://localhost:9999/primitive-local-vars", Void.class);
            } catch (RestClientException ignore) {
            }
        }, 0, 1, TimeUnit.SECONDS);

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                restTemplate.getForEntity("http://localhost:9999/changing-primitive-local-vars", Void.class);
            } catch (RestClientException ignore) {
            }
        }, 0, 2, TimeUnit.SECONDS);

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                restTemplate.getForEntity("http://localhost:9999/slow-endpoint", Void.class);
            } catch (RestClientException ignore) {
            }
        }, 0, 3, TimeUnit.SECONDS);
    }
}
