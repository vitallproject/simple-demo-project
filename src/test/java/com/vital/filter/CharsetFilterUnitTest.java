package com.vital.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CharsetFilterUnitTest {

    @Mock
    private ServletRequest servletRequest;
    @Mock
    private ServletResponse servletResponse;
    @Mock
    private FilterChain filterChain;

    @Test
    void shouldSetCharacterEncodingForRequestAndResponse() throws IOException, ServletException {
        new CharsetFilter().doFilter(servletRequest, servletResponse, filterChain);

        verify(servletRequest).setCharacterEncoding(StandardCharsets.UTF_8.name());
        verify(servletResponse).setCharacterEncoding(StandardCharsets.UTF_8.name());
        verify(filterChain).doFilter(servletRequest, servletResponse);
    }
}
