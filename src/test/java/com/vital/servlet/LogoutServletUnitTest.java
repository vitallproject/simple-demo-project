package com.vital.servlet;

import com.vital.util.UrlPath;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogoutServletUnitTest {

    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession httpSession;

    @Test
    void shouldInvalidateSessionAndSendRedirectToLoginPage() throws Exception {
        doReturn(httpSession).when(request).getSession();

        new LogoutServlet().doPost(request, response);

        verify(httpSession).invalidate();
        verify(response).sendRedirect(UrlPath.LOGIN);
    }
}