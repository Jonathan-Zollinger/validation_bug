package com.jonathan_zollinger

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.validation.ValidationException
import net.datafaker.Faker
import spock.lang.Shared
import spock.lang.Specification
import jakarta.inject.Inject

import java.util.stream.Collectors
import java.util.stream.Stream

@MicronautTest
class PlaceSpec extends Specification {

    @Shared
    Faker faker = new Faker()

    @Shared
    List<String> zipCodePlus4 = Stream.generate(faker.address()::zipCodePlus4)
            .distinct()
            .limit(50)
            .collect(Collectors.toList())

    @Shared
    List<String> zipCode = Stream.generate(faker.address()::zipCode)
            .distinct()
            .limit(50)
            .collect(Collectors.toList())

    @Shared
    List<String> city = Stream.generate(faker.address()::cityName)
            .limit(50)
            .collect(Collectors.toList())

    @Shared
    List<String> state = Stream.generate(faker.address()::state)
            .limit(50)
            .collect(Collectors.toList())

    @Shared
    List<String> emptyStrings = Collections.nCopies(26, "").toList() + (Collections.nCopies(26, " "))

    void 'test creating a Place object with "#badZipCode" throws an error'() {
        when:
        new Place(badZipCode)

        then:
        thrown(ValidationException)

        where:
        badZipCode << city + List.of("", " ")
    }

    void 'test creating a Place object with "#badCity" and "#badState" throws an error'() {
        when:
        new Place(badCity, badState)

        then:
        thrown(ValidationException)

        where:
        badCity << city + emptyStrings
        badState << emptyStrings + state
    }

    void 'test creating a Place object with "#goodZip" throws no error'() {
        when:
        new Place(goodZip)

        then:
        noExceptionThrown()

        where:
        goodZip << zipCodePlus4 + zipCode
    }

    void 'test creating a Place object with "#goodCity" and "#goodState" throws no error'() {
        when:
        new Place(goodCity, goodState)

        then:
        noExceptionThrown()

        where:
        goodCity << city
        goodState << state
    }

}
