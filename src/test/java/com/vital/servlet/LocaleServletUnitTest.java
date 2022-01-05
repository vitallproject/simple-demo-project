package com.vital.servlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocaleServletUnitTest {

    private static final String LANGUAGE = "lang";

    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession httpSession;

    @Test
    void shouldGetParameterSetAttributeToSessionAndSendRedirect() throws Exception {
        doReturn("en_US").when(request).getParameter(LANGUAGE);
        doReturn(httpSession).when(request).getSession();
        doReturn("prevPage").when(request).getHeader("referer");

        new LocaleServlet().doPost(request, response);

        verify(httpSession).setAttribute(LANGUAGE, "en_US");
        verify(response).sendRedirect("prevPage");
    }
}