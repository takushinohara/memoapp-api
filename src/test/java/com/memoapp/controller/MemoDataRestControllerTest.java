package com.memoapp.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.memoapp.App;
import com.memoapp.domain.MemoData;
import com.memoapp.repository.MemoDataRepository;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
@IntegrationTest({ "server.port:0",
                   "spring.datasource.url:jdbc:h2:mem:momodata;DB_CLOSE_ON_EXIT=FALSE" })
public class MemoDataRestControllerTest {
    @Autowired
    MemoDataRepository memoDataRepository;
    @Value("${local.server.port}")
    int port;
    String apiEndpoint;
    RestTemplate restTemplate = new TestRestTemplate();
    MemoData testData1;
    MemoData testData2;

    @Before
    public void setUp() {
        // データクリア
        memoDataRepository.deleteAll();
        // 初期データ設定
        testData1 = new MemoData();
        testData1.setTitle("テストタイトル１");
        testData1.setContent("テスト本文１");
        testData2 = new MemoData();
        testData2.setTitle("テストタイトル２");
        testData2.setContent("テスト本文２");

        memoDataRepository.save(Arrays.asList(testData1, testData2));
        apiEndpoint = "http://localhost:" + port + "/api/memoapp";
    }

    @Test
    public void メモ一覧が取得できる() throws Exception {
        ResponseEntity<List<MemoData>> response = restTemplate.exchange(
                apiEndpoint,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MemoData>>(){}
                );
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().size(), is(2));

        MemoData memoData1 = response.getBody().get(1);
        assertThat(memoData1.getId(), is(testData1.getId()));
        assertThat(memoData1.getTitle(), is(testData1.getTitle()));
        assertThat(memoData1.getContent(), is(testData1.getContent()));

        MemoData memoData2 = response.getBody().get(0);
        assertThat(memoData2.getId(), is(testData2.getId()));
        assertThat(memoData2.getTitle(), is(testData2.getTitle()));
        assertThat(memoData2.getContent(), is(testData2.getContent()));
    }

    @Test
    public void メモが追加できる() throws Exception {
        MemoData testData = new MemoData();
        testData.setTitle("新規メモ");
        testData.setContent("新規本文");

        ResponseEntity<MemoData> response = restTemplate.exchange(
                apiEndpoint,
                HttpMethod.POST,
                new HttpEntity<>(testData),
                MemoData.class
                );
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        MemoData memoData = response.getBody();
        assertThat(memoData.getId(), is(notNullValue()));
        assertThat(memoData.getTitle(), is(testData.getTitle()));
        assertThat(memoData.getContent(), is(testData.getContent()));

        assertThat(restTemplate.exchange(
                                apiEndpoint,
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<List<MemoData>>(){}
                                ).getBody().size(), is(3));
    }

    @Test
    public void メモが１件取得できる() throws Exception {
        ResponseEntity<MemoData> response = restTemplate.exchange(
                apiEndpoint + "/{id}",
                HttpMethod.GET,
                null,
                MemoData.class,
                Collections.singletonMap("id", testData1.getId())
                );
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        MemoData memoData1 = response.getBody();
        assertThat(memoData1.getId(), is(testData1.getId()));
        assertThat(memoData1.getTitle(), is(testData1.getTitle()));
        assertThat(memoData1.getContent(), is(testData1.getContent()));
    }

    @Test
    public void メモが更新できる() throws Exception {
        testData1.setTitle("テストタイトル１更新");
        testData1.setContent("テスト本文１更新");

        ResponseEntity<Void> response = restTemplate.exchange(
                apiEndpoint + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(testData1),
                Void.class,
                Collections.singletonMap("id", testData1.getId())
                );
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
        MemoData memoData1 = restTemplate.exchange(
                                          apiEndpoint + "/{id}",
                                          HttpMethod.GET,
                                          null,
                                          new ParameterizedTypeReference<MemoData>() {},
                                          Collections.singletonMap("id", testData1.getId())
                                          ).getBody();
        assertThat(memoData1.getTitle(), is(testData1.getTitle()));
        assertThat(memoData1.getContent(), is(testData1.getContent()));
    }

    @Test
    public void メモが削除できる() throws Exception {
        ResponseEntity<Void> response = restTemplate.exchange(
                apiEndpoint + "/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                Collections.singletonMap("id", testData1.getId())
                );
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
        assertThat(restTemplate.exchange(
                                apiEndpoint,
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<List<MemoData>>(){}
                                ).getBody().size(), is(1));
    }
}
