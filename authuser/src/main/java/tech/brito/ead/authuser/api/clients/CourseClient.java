package tech.brito.ead.authuser.api.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tech.brito.ead.authuser.api.models.CourseDTO;
import tech.brito.ead.authuser.api.models.ResponsePageDTO;

import java.util.UUID;

@Log4j2
@Component
public class CourseClient {
    @Value("${ead.api.url.course}")
    String REQUEST_URI_COURSE;
    private final RestTemplate restTemplate;

    public CourseClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "circuitbreakerInstance")
    public Page<CourseDTO> getAllCoursesByUser(UUID userId, Pageable pageable, String token) {

        var url = generateUrlCoursesByUser(userId, pageable, token);
        log.info("Url -> {}", url);
        var responseType = new ParameterizedTypeReference<ResponsePageDTO<CourseDTO>>() {};
        ResponseEntity<ResponsePageDTO<CourseDTO>> result = restTemplate.exchange(url,
                                                                                  HttpMethod.GET,
                                                                                  generateRequestEntity(token),
                                                                                  responseType);
        return result.getBody();
    }

    private String generateUrlCoursesByUser(UUID userId, Pageable pageable, String token) {
        var url = new StringBuilder();
        url.append(REQUEST_URI_COURSE);
        url.append("/courses?userId=");
        url.append(userId);
        url.append("&page=");
        url.append(pageable.getPageNumber());
        url.append("&size=");
        url.append(pageable.getPageSize());
        url.append("&sort=");
        url.append(pageable.getSort().toString().replaceAll(": ", ","));
        return url.toString();
    }

    private HttpEntity<String> generateRequestEntity(String token) {
        var headers = new HttpHeaders();
        headers.set("Authorization", token);
        return new HttpEntity<String>("parameters", headers);
    }
}
