package example.operate

import com.github.javafaker.Faker
import example.webapp.Booter
import example.webapp.model.User
import org.apache.commons.lang3.tuple.ImmutablePair
import org.apache.commons.lang3.tuple.Pair
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import java.util.concurrent.*

object WebappOperator {
    private val restTemplate = RestTemplate()
    private val faker = Faker.instance()
    private val genNames: MutableList<Pair<String?, String?>> = CopyOnWriteArrayList()
    private var minUserId = Int.MAX_VALUE
    private var maxUserId = Int.MIN_VALUE
    @JvmStatic
    fun main(args: Array<String>) {
        //start webapp
        Booter.main(args)
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
            val firstName = faker.name().firstName()
            val lastName = faker.name().lastName()
            genNames.add(ImmutablePair.of(firstName, lastName))
            try {
                val uri = UriComponentsBuilder
                    .fromHttpUrl("http://localhost:9999/users")
                    .queryParam("firstName", firstName)
                    .queryParam("lastName", lastName)
                    .build()
                    .toUri()
                restTemplate.exchange(uri, HttpMethod.POST, null, User::class.java)
            } catch (ignore: Exception) {
            }
        }, 0, ThreadLocalRandom.current().nextInt(2500 * 5).toLong(), TimeUnit.MILLISECONDS)
        genNames.add(ImmutablePair.of(faker.name().firstName(), faker.name().lastName()))
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
            try {
                val genName: Pair<String?, String?>
                if (ThreadLocalRandom.current().nextInt(10) > 8) {
                    genName = genNames.removeAt(ThreadLocalRandom.current().nextInt(genNames.size))
                } else {
                    genName = ImmutablePair.of(faker.name().firstName(), faker.name().lastName())
                }
                val uri = UriComponentsBuilder
                    .fromHttpUrl("http://localhost:9999/users")
                    .queryParam("firstName", genName.left)
                    .queryParam("lastName", genName.right)
                    .build()
                    .toUri()
                restTemplate.exchange(uri, HttpMethod.DELETE, null, User::class.java)
            } catch (ignore: Exception) {
            }
        }, 0, ThreadLocalRandom.current().nextInt(1500 * 5).toLong(), TimeUnit.MILLISECONDS)
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
            val userEntities = restTemplate.getForEntity("http://localhost:9999/users", Array<User>::class.java)
            Arrays.stream(userEntities.body).forEach { user: User ->
                if (user.id < minUserId) {
                    minUserId = user.id.toInt()
                }
                if (user.id > maxUserId) {
                    maxUserId = user.id.toInt()
                }
            }
        }, 0, ThreadLocalRandom.current().nextInt(3500 * 5).toLong(), TimeUnit.MILLISECONDS)
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
            if (minUserId < maxUserId && maxUserId > 0) {
                var userId = ThreadLocalRandom.current().nextInt(minUserId, maxUserId)
                if (ThreadLocalRandom.current().nextBoolean()) {
                    userId *= 2
                }
                try {
                    restTemplate.getForEntity("http://localhost:9999/users/$userId", User::class.java)
                } catch (ignore: RestClientException) {
                }
            }
        }, 0, ThreadLocalRandom.current().nextInt(3500 * 5).toLong(), TimeUnit.MILLISECONDS)
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
            try {
                restTemplate.getForEntity("http://localhost:9999/throws-exception", User::class.java)
            } catch (ignore: RestClientException) {
            }
        }, 0, ThreadLocalRandom.current().nextInt(6500 * 5).toLong(), TimeUnit.MILLISECONDS)
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
            try {
                restTemplate.getForEntity("http://localhost:9999/primitive-local-vars", User::class.java)
            } catch (ignore: RestClientException) {
            }
        }, 0, 1, TimeUnit.SECONDS)
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
            try {
                restTemplate.getForEntity("http://localhost:9999/changing-primitive-local-vars", User::class.java)
            } catch (ignore: RestClientException) {
            }
        }, 0, 2, TimeUnit.SECONDS)
    }
}