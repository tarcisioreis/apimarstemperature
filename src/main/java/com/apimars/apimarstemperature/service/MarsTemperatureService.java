package com.apimars.apimarstemperature.service;

import com.apimars.apimarstemperature.config.ConfigProperties;
import com.apimars.apimarstemperature.dto.DataDTO;
import com.apimars.apimarstemperature.exceptions.BusinessException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.security.cert.CertificateException;
import java.util.*;

import static javax.servlet.SessionTrackingMode.SSL;

@SuppressWarnings({"squid:S1186", "squid:S4424", "squid:S2259", "squid:S3776"})
@Service
public class MarsTemperatureService {

    private final ConfigProperties configProperties;

    public MarsTemperatureService(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    protected OkHttpClient getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                                                       String authType) throws
                            CertificateException {
                        }
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                                                       String authType) throws
                            CertificateException {
                        }
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance(String.valueOf(SSL));
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);

            builder.hostnameVerifier((hostname, session) -> hostname.equalsIgnoreCase(session.getPeerHost()));

            return builder.build();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public List<DataDTO> getTemperature(Integer sol) {
        OkHttpClient httpClient = getUnsafeOkHttpClient();
        Response response = null;
        String retorno = null;

        List<DataDTO> lista = null;

        Request request = new Request.Builder()
                .url(configProperties.getURL_BASE() + configProperties.getAPI_KEY() +
                     configProperties.getFEEDTYPE() + configProperties.getVERSION_API())
                .get()
                .build();

        try {
            response = httpClient.newCall(request).execute();

            retorno = response.body().string();

            if (retorno.isEmpty()) {
                return Collections.emptyList();
            }

            DataDTO dto = null;

            HashMap<String, Object> map = null;
            TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};

            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(retorno, typeRef);

            for(Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();

                if (!key.equals("sol_keys") && !key.equals("validity_checks")) {

                    if (sol != null && !sol.equals(Integer.parseInt(key))) {
                            continue;
                    }

                    if (lista == null) {
                        lista = new ArrayList<>();
                    }

                    dto = new DataDTO();

                    dto.setSol(Integer.parseInt(key));
                }

                if (entry.getValue() instanceof LinkedHashMap) {
                    LinkedHashMap<String, Object> value = (LinkedHashMap<String, Object>) entry.getValue();

                    for(Map.Entry<String, Object> inner : value.entrySet()) {
                        String innerKey = inner.getKey();
                        Object values = inner.getValue();

                        if (innerKey.equals("AT")) {
                            LinkedHashMap<String, Object> innervalues = (LinkedHashMap<String, Object>) values;
                            for(Map.Entry<String, Object> inValues : innervalues.entrySet()) {
                                String inKey = inValues.getKey();
                                Object inKValue = inValues.getValue();

                                if (inKey.equals("av")) {
                                    dto.setAverage(Double.valueOf(inKValue.toString()));
                                    lista.add(dto);
                                }
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            return Collections.emptyList();
        }

        return lista;
    }

}
