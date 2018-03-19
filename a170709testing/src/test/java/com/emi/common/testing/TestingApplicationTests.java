package com.emi.common.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class TestingApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private MockMvc mvc;

	@Test
	public void testUriBuild() throws URISyntaxException {

		URI uri = new URI("http://www.emi.com?name=miao&age=10");
		String fromUri = UriComponentsBuilder.fromUri(uri).build().getQueryParams().get("age").get(0);
		String fromUriStr = UriComponentsBuilder.fromUriString(uri.toString()).build().getQueryParams().get("name").get(0);
		System.out.println("fromUri: " + fromUri);
		System.out.println("fromUriStr: " + fromUriStr);

		MockHttpServletRequest mocRequest = new MockHttpServletRequest();
		mocRequest.setScheme("http");
		mocRequest.setServerName("localhost");
		mocRequest.setServerPort(-1);
		mocRequest.setRequestURI("/path");
		mocRequest.setQueryString("a=1");
		System.out.println(mocRequest.getRequestURI());

		HttpRequest httpRequest = new ServletServerHttpRequest(mocRequest);
		UriComponents result = UriComponentsBuilder.fromHttpRequest(httpRequest).build();
		System.out.println(result.toString());

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://www.emi.com/topic/{tid}/?name=miao")
				.queryParam("nickname", "nick");
		String urlStr = builder.buildAndExpand("22").encode().toString();
		System.out.println(urlStr);
	}
}
