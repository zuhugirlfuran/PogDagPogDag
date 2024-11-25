package com.ssafy.snuggle.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.ssafy.snuggle.message.FcmMessageWithData;
import com.ssafy.snuggle.util.Constants;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * FCM 알림 메시지 생성
 * background 대응을 위해서 data로 전송한다.
 *   
 * @author taeshik.heo
 *
 */
@Component
public class FirebaseCloudMessageServiceWithData {
	private static final Logger logger = LoggerFactory.getLogger(FirebaseCloudMessageServiceWithData.class);

    public final ObjectMapper objectMapper;

    /**
     * FCM에 push 요청을 보낼 때 인증을 위해 Header에 포함시킬 AccessToken 생성
     * @return
     * @throws IOException
     */
    private String getAccessToken() throws IOException {

        // GoogleApi를 사용하기 위해 oAuth2를 이용해 인증한 대상을 나타내는객체
        GoogleCredentials googleCredentials = GoogleCredentials
                // 서버로부터 받은 service key 파일 활용
                .fromStream(new ClassPathResource(Constants.FIREBASE_KEY_FILE).getInputStream())
                // 인증하는 서버에서 필요로 하는 권한 지정
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
        
        googleCredentials.refreshIfExpired();
        String token = googleCredentials.getAccessToken().getTokenValue();
        
        return token;
    }
    
    /**
     * FCM 알림 메시지 생성
     * background 대응을 위해서 data로 전송한다.  
     * @param targetToken
     * @param title
     * @param body
     * @return
     * @throws JsonProcessingException
     */
    private String makeDataMessage(String targetToken, String title, String body, String channel) throws JsonProcessingException {
        Map<String, String> data = new HashMap<>();
        data.put("myTitle", title);
        data.put("myBody", body);
        data.put("channel", channel); // 채널 정보를 추가

        FcmMessageWithData.Message message = new FcmMessageWithData.Message();
        message.setToken(targetToken);
        message.setData(data);

        FcmMessageWithData fcmMessage = new FcmMessageWithData(false, message);

        return objectMapper.writeValueAsString(fcmMessage);
    }

    

    /**
     * targetToken에 해당하는 device로 FCM 푸시 알림 전송
     * background 대응을 위해서 data로 전송한다.  
     * @param targetToken
     * @param title
     * @param body
     * @throws IOException
     */
    public void sendDataMessageTo(String targetToken, String title, String body, String channel) throws IOException {
        String message = makeDataMessage(targetToken, title, body, channel); // 채널 추가
        logger.info("message : {}", message);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(Constants.API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }



    
    public FirebaseCloudMessageServiceWithData(ObjectMapper objectMapper){
    	this.objectMapper = objectMapper;
    }

    
    // 클라이언트 토큰 관리
    public void addToken(String token) {
        Constants.clientTokens.add(token);
    }
    
    // 등록된 모든 토큰을 이용해서 broadcasting
    public int broadCastDataMessage(String title, String body, String channel) throws IOException {
        for (String token : Constants.clientTokens) {
            logger.debug("broadcastmessage : {}, {}, {}, {}", token, title, body, channel);
            sendDataMessageTo(token, title, body, channel); // 채널 추가
        }
        return Constants.clientTokens.size();
    }



}
