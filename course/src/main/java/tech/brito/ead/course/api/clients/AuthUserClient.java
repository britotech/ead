package tech.brito.ead.course.api.clients;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tech.brito.ead.course.api.models.CourseUserDto;
import tech.brito.ead.course.api.models.ResponsePageDto;
import tech.brito.ead.course.api.models.UserDto;

import java.util.UUID;

@Log4j2
@Component
public class AuthUserClient {

    @Value("${ead.api.url.authuser}")
    String REQUEST_URI_AUTH_USER;
    private final RestTemplate restTemplate;

    public AuthUserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Page<UserDto> getAllUsersByCourse(UUID courseId, Pageable pageable) {

        var url = generateUrlGetUsersByCourse(courseId, pageable);
        log.info("Url -> {}", url);
        var responseType = new ParameterizedTypeReference<ResponsePageDto<UserDto>>() {
        };
        ResponseEntity<ResponsePageDto<UserDto>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        return result.getBody();
    }

    private String generateUrlGetUsersByCourse(UUID courseId, Pageable pageable) {
        var url = new StringBuilder();
        url.append(REQUEST_URI_AUTH_USER);
        url.append("/users?courseId=");
        url.append(courseId);
        url.append("&page=");
        url.append(pageable.getPageNumber());
        url.append("&size=");
        url.append(pageable.getPageSize());
        url.append("&sort=");
        url.append(pageable.getSort().toString().replaceAll(": ", ","));
        return url.toString();
    }

    public UserDto getUserById(UUID userId) {
        var url = String.format("%s/users/%s", REQUEST_URI_AUTH_USER, userId);
        log.info("Url -> {}", url);
        return restTemplate.exchange(url, HttpMethod.GET, null, UserDto.class).getBody();
    }
    public void postSubscriptionUserInCourse(CourseUserDto courseUserDto) {
        var url = String.format("%s/users/%s/courses/subscription", REQUEST_URI_AUTH_USER, courseUserDto.getUserId());
        restTemplate.postForObject(url, courseUserDto, String.class);
    }
}
