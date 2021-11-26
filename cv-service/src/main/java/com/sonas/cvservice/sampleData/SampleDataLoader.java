package com.sonas.cvservice.sampleData;

import com.github.javafaker.Faker;
import com.sonas.cvservice.dao.Cv;
import com.sonas.cvservice.enums.CvType;
import com.sonas.cvservice.repository.CvRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SampleDataLoader implements CommandLineRunner {

    private final CvRepository cvRepository;

    private final Faker faker;

    public SampleDataLoader(CvRepository cvRepository, Faker faker) {
        this.cvRepository = cvRepository;
        this.faker = faker;
    }

    @Override
    public void run(String... args) {
        List<Cv> sampleCvs = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> new Cv(
                        faker.number().numberBetween(1, 10),
                        CvType.SIMPLE,
                        faker.number().numberBetween(1, 10),
                        faker.number().numberBetween(1, 10),
                        faker.beer().hop(),
                        faker.job().title(),
                        faker.job().seniority(),
                        faker.hobbit().quote()
                )).collect(Collectors.toList());
        cvRepository.saveAll(sampleCvs);
    }
}

